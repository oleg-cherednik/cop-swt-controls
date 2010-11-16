/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers;

import static cop.algorithms.search.LinearSearch.linearSearch;
import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.ArrayExtension.removeDublicatesAndSort;
import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isEmpty;
import static cop.common.extensions.StringExtension.isNotEmpty;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MENU_ITEM_KEY;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MI_COPY;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MI_DELETE;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MI_DESELECT_ALL;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MI_PROPERTIES;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MI_SELECT_ALL;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MI_SORT;
import static cop.swt.widgets.viewers.model.enums.ModificationTypeEnum.REMOVE;
import static org.eclipse.swt.SWT.Dispose;
import static org.eclipse.swt.SWT.KeyDown;
import static org.eclipse.swt.SWT.KeyUp;
import static org.eclipse.swt.SWT.MenuDetect;
import static org.eclipse.swt.SWT.MouseExit;
import static org.eclipse.swt.SWT.MouseWheel;
import static org.eclipse.swt.SWT.Selection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.dialogs.PreferencesUtil;

import cop.common.extensions.BitExtension;
import cop.managers.ClipboardManager;
import cop.swt.images.ImageProvider;
import cop.swt.widgets.interfaces.Clearable;
import cop.swt.widgets.interfaces.Refreshable;
import cop.swt.widgets.keys.HotKeyManager;
import cop.swt.widgets.keys.enums.KeyEnum;
import cop.swt.widgets.localization.interfaces.LocaleSupport;
import cop.swt.widgets.menus.MenuBuilder;
import cop.swt.widgets.menus.MenuManager;
import cop.swt.widgets.menus.enums.MenuItemEnum;
import cop.swt.widgets.menus.interfaces.IMenuBuilder;
import cop.swt.widgets.menus.interfaces.PropertyProvider;
import cop.swt.widgets.menus.items.CascadeMenuItem;
import cop.swt.widgets.menus.items.PushMenuItem;
import cop.swt.widgets.menus.items.SeparatorMenuItem;
import cop.swt.widgets.model.interfaces.IModelChange;
import cop.swt.widgets.model.interfaces.Model;
import cop.swt.widgets.viewers.interfaces.IModifyListener;
import cop.swt.widgets.viewers.interfaces.ISelectionListener;
import cop.swt.widgets.viewers.interfaces.ModifyListenerSupport;
import cop.swt.widgets.viewers.interfaces.SelectionListenerSupport;
import cop.swt.widgets.viewers.model.ListModel;
import cop.swt.widgets.viewers.model.enums.ModificationTypeEnum;
import cop.swt.widgets.viewers.model.interfaces.ModelSupport;
import cop.swt.widgets.viewers.model.interfaces.ViewerModel;
import cop.swt.widgets.viewers.table.interfaces.ViewerConfig;

public abstract class PViewer<T> implements ModelSupport<T>, LocaleSupport, ModifyListenerSupport<T>,
                SelectionListenerSupport<T>, Clearable, Refreshable, Listener
{
	protected final Composite parent;
	public StructuredViewer widget;
	protected final T obj;

	// protected final ImageProviderImpl imageProvider = new ImageProviderImpl();
	protected ViewerConfig config;

	// manager
	private HotKeyManager keyManager;
	protected MenuManager menuManager;
	// property flags
	private boolean readonly;
	private boolean ctrlPressed;
	private final String PREFERENCE_PAGE;
	// model
	protected ViewerModel<T> model;
	protected ListModel<T> defaultModel;
	// listeners
	private Set<ISelectionListener<T>> selectionListeners = new CopyOnWriteArraySet<ISelectionListener<T>>();
	private Set<IModifyListener<T>> modifyListeners = new HashSet<IModifyListener<T>>();

	protected PViewer(T obj, StructuredViewer viewer, String preferencePage, ViewerConfig config)
	{
		Assert.isNotNull(obj);
		Assert.isNotNull(viewer);

		this.obj = obj;
		this.widget = viewer;
		this.parent = widget.getControl().getParent();
		this.config = config;
		PREFERENCE_PAGE = preferencePage;

		addListeners();
	}

	protected void postConstruct()
	{
		createHotKeys();
		createMenuManager();
		setStandaloneMode();
	}

	private void addListeners()
	{
		widget.addSelectionChangedListener(notifySelectionChangedListener);

		Scrollable obj = (Scrollable)widget.getControl();

		obj.addListener(KeyDown, this);
		obj.addListener(KeyUp, this);
		obj.addListener(MouseWheel, this);
		obj.addListener(MouseExit, this);
		obj.addListener(MenuDetect, this);
	}

	public boolean isReadonly()
	{
		return readonly;
	}

	public void setReadonly(boolean readonly)
	{
		this.readonly = readonly;
	}

	public boolean isSorterOn()
	{
		Assert.isNotNull(widget);

		return isNotNull(widget.getSorter());
	}

	protected void swap(int index1, int index2)
	{
		if(isStandaloneMode())
			defaultModel.swap(index1, index2);
	}

	protected abstract int getTopIndex();

	protected abstract int[] getSelectionIndices();

	protected abstract void setTopIndex(int index);

	protected abstract void setSelected(int index, boolean selected);

	private void moveItemsUp(int[] indices, boolean keepTopIndex)
	{
		if(!isStandaloneMode() || isSorterOn())
			return;

		int size = defaultModel.getItemCount();

		if(isEmpty(indices) || size == 0)
			return;

		int topIndex = getTopIndex();
		int[] unicueIndices = (indices.length > 1) ? removeDublicatesAndSort(indices, 0, size - 1) : indices;

		if(unicueIndices[0] == 0)
			return;

		for(int index : unicueIndices)
		{
			int[] selectedItems = getSelectionIndices();
			boolean sel = linearSearch(selectedItems, index) >= 0;
			boolean sel1 = linearSearch(selectedItems, index - 1) >= 0;

			swap(index, index - 1);

			setSelected(index, sel1);
			setSelected(index - 1, sel);
		}

		if(keepTopIndex)
			setTopIndex(topIndex - 1);
	}

	private void moveItemsDown(int[] indices, boolean keepTopIndex)
	{
		if(!isStandaloneMode() || isSorterOn())
			return;

		int size = defaultModel.getItemCount();

		if(isEmpty(indices) || size == 0)
			return;

		int topIndex = getTopIndex();
		int[] unicueIndices = (indices.length > 1) ? removeDublicatesAndSort(indices, 0, size - 1) : indices;

		if(unicueIndices[unicueIndices.length - 1] == size - 1)
			return;

		for(int i = unicueIndices.length - 1; i >= 0; i--)
		{
			int index = unicueIndices[i];
			int[] selectedItems = getSelectionIndices();
			boolean sel = linearSearch(selectedItems, index) >= 0;
			boolean sel1 = linearSearch(selectedItems, index + 1) >= 0;

			swap(index, index + 1);

			setSelected(index, sel1);
			setSelected(index + 1, sel);
		}

		if(keepTopIndex)
			setTopIndex(topIndex + 1);
	}

	protected final ImageProvider getImageProvider()
	{
		return isNotNull(config) ? config.getImageProvider() : null;
	}

	protected IMenuBuilder createMenuBuilder()
	{
		MenuBuilder menuBuilder = new MenuBuilder(getImageProvider());

		menuBuilder.addMenuItem(new PushMenuItem(MI_COPY, null, isCopyEnabled, this));
		menuBuilder.addMenuItem(new PushMenuItem(MI_DELETE, isDeleteVisible, isDeleteEnabled, this));
		menuBuilder.addMenuItem(new SeparatorMenuItem());
		menuBuilder.addMenuItem(new PushMenuItem(MI_SELECT_ALL, isSelectAllVisible, isSelectAllEnabled, this));
		menuBuilder.addMenuItem(new PushMenuItem(MI_DESELECT_ALL, null, isDeselectAllEnabled, this));
		menuBuilder.addMenuItem(new SeparatorMenuItem());
		menuBuilder.addMenuItem(new CascadeMenuItem(widget.getControl(), keyManager, MI_SORT, createSortMenuBuilder(),
		                isSortable, null));
		menuBuilder.addMenuItem(new SeparatorMenuItem());
		menuBuilder.addMenuItem(new PushMenuItem(MI_PROPERTIES, isPropertiesVisible, null, this));

		return menuBuilder;
	}

	private void createMenuManager()
	{
		menuManager = new MenuManager(widget.getControl(), keyManager, createMenuBuilder());
	}

	protected MenuBuilder createSortMenuBuilder()
	{
		MenuBuilder menuBuilder = new MenuBuilder(getImageProvider());

		// menuBuilder.addMenuItem(new CheckMenuItem(MenuItemEnum.MI_ON_OFF, null, null, isSortOn, setSortEnabled));
		// menuBuilder.addMenuItem(new SeparatorMenuItem());

		return menuBuilder;
	}

	protected boolean isSortable()
	{
		return true;
	}

	// TODO - not protected
	protected PropertyProvider<Boolean> isSortable = new PropertyProvider<Boolean>()
	{
		@Override
		public Boolean getProperty()
		{
			return isSortable();
		}
	};

	private PropertyProvider<Boolean> isCopyEnabled = new PropertyProvider<Boolean>()
	{
		@Override
		public Boolean getProperty()
		{
			return !getSelectedItems().isEmpty();
		}
	};

	private PropertyProvider<Boolean> isDeleteVisible = new PropertyProvider<Boolean>()
	{
		@Override
		public Boolean getProperty()
		{
			return !modifyListeners.isEmpty();
		}
	};

	private PropertyProvider<Boolean> isPropertiesVisible = new PropertyProvider<Boolean>()
	{
		@Override
		public Boolean getProperty()
		{
			return isNotEmpty(PREFERENCE_PAGE);
		}
	};

	private PropertyProvider<Boolean> isSelectAllVisible = new PropertyProvider<Boolean>()
	{
		@Override
		public Boolean getProperty()
		{
			return BitExtension.isBitSet(widget.getControl().getStyle(), SWT.MULTI);
		}
	};

	private PropertyProvider<Boolean> isSelectAllEnabled = new PropertyProvider<Boolean>()
	{
		@Override
		public Boolean getProperty()
		{
			return getItemCount() != getSelectedItems().size();
		}
	};

	private PropertyProvider<Boolean> isDeselectAllEnabled = new PropertyProvider<Boolean>()
	{
		@Override
		public Boolean getProperty()
		{
			return !getSelectedItems().isEmpty();
		}
	};

	private PropertyProvider<Boolean> isDeleteEnabled = new PropertyProvider<Boolean>()
	{
		@Override
		public Boolean getProperty()
		{
			return !isReadonly() && !getSelectedItems().isEmpty();
		}
	};

	private void createHotKeys()
	{
		Assert.isNotNull(widget);

		keyManager = new HotKeyManager(widget.getControl());
	}

	public List<T> getItems()
	{
		Assert.isNotNull(model);

		return (List<T>)model.getElements(null);
	}

	public void setItems(Collection<T> items)
	{
		Assert.isNotNull(defaultModel, "setItems() must be used only in non-model mode");

		defaultModel.set(items);
	}

	protected void setControlMenu(Menu menu)
	{
		Assert.isNotNull(widget);

		if(menu != null && menu.getItemCount() != 0)
			widget.getControl().setMenu(menu);
		else
			widget.getControl().setMenu(null);
	}

	// return empty array if no selection
	public abstract List<T> getSelectedItems();

	public abstract int getItemCount();

	public void selectAll()
	{
		notifySelectionListeners(getSelectedItems());
	}

	public void deselectAll()
	{
		notifySelectionListeners(getSelectedItems());
	}

	public boolean isStandaloneMode()
	{
		return isNotNull(defaultModel);
	}

	private void setStandaloneMode()
	{
		beginListenToModel(new ListModel<T>(getClass().getCanonicalName() + ".default"));
		defaultModel = (ListModel<T>)model;
	}

	public abstract void setSorterOff();

	private void removeDefaultModel()
	{
		if(isNull(defaultModel))
			return;

		defaultModel.removeListener(onModelChanged);
		defaultModel.dispose();
		defaultModel = null;
	}

	protected void modelChanged()
	{
		refresh();
	}

	private void deleteSelectedItems()
	{
		List<T> items = getSelectedItems();

		if(isEmpty(items))
			return;

		for(T item : items)
			notifyModifyListener(item, REMOVE);
	}

	public void dispose()
	{
		model.removeListener(onModelChanged);
		removeDefaultModel();
	}

	public void setEnabled(boolean enabled)
	{
		widget.getControl().setEnabled(false);
	}

	public boolean isEnabled()
	{
		return widget.getControl().isEnabled();
	}

	protected abstract List<String[]> toStringArrayList(List<T> items);

	/*
	 * ModifyListenerSupport
	 */

	@Override
	public void addModifyListener(IModifyListener<T> listener)
	{
		if(isNotNull(listener))
			modifyListeners.add(listener);
	}

	@Override
	public void removeModifyListener(IModifyListener<T> listener)
	{
		if(isNotNull(listener))
			modifyListeners.remove(listener);
	}

	private void notifyModifyListener(T item, ModificationTypeEnum type)
	{
		for(IModifyListener<T> listener : modifyListeners)
			listener.itemModified(widget.getControl(), item, type);
	}

	/*
	 * ModelSupport
	 */

	@Override
	public void beginListenToModel(ViewerModel<T> model)
	{
		if(isNull(model) || this.model == model)
			return;

		if(isStandaloneMode())
			removeDefaultModel();
		else if(isNotNull(this.model))
			this.model.removeListener(onModelChanged);

		model.addListener(onModelChanged);
		this.model = model;

		widget.setContentProvider(new ContentProviderAdapter<T>(model));
		widget.setInput(new ArrayList<T>());

		modelChanged();
	}

	@Override
	public void stopListenToModel(ViewerModel<T> model)
	{
		if(isNull(model))
			return;

		model.removeListener(onModelChanged);

		if(this.model == defaultModel)
			return;

		setStandaloneMode();
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		Assert.isNotNull(locale);

		if(isNotNull(menuManager))
			menuManager.setLocale(locale);
	}

	/*
	 * SelectionListenerSupport
	 */

	@Override
	public void addSelectionListener(ISelectionListener<T> listener)
	{
		selectionListeners.add(listener);
	}

	@Override
	public void removeSelectionListener(ISelectionListener<T> listener)
	{
		selectionListeners.remove(listener);
	}

	protected void notifySelectionListeners(List<T> items)
	{
		for(ISelectionListener<T> listener : selectionListeners)
			listener.itemSelected(widget.getControl(), items);
	}

	/*
	 * Refreshable
	 */

	@Override
	public void refresh()
	{
		widget.refresh();
	}

	/*
	 * Clearable
	 */

	@Override
	public void clear()
	{

	}

	/*
	 * Listener
	 */

	@Override
	public void handleEvent(Event event)
	{
		if(event.widget == widget.getControl())
			handleControlEvent(event);
		else if(((MenuItem)event.widget).getParent() == widget.getControl().getMenu())
			handleMenuEvent(event);
	}

	protected void handleControlEvent(Event event)
	{
		if(event.type == Dispose)
			dispose();
		else if(event.type == KeyDown)
			onKeyDown(event);
		else if(event.type == KeyUp)
			onKeyUp(event);
		else if(event.type == MouseWheel)
			onMouseWheel(event);
		else if(event.type == MouseExit)
			onMouseExit(event);
		else if(event.type == MenuDetect)
			onMenuDetect(event);
	}

	protected void handleMenuEvent(Event event)
	{
		if(event.type == Selection)
			onSelection(event);
	}

	/*
	 * Listeners
	 */

	private void onKeyDown(Event event)
	{
		if(event.keyCode == KeyEnum.KEY_CTRL.getKeyCode())
			ctrlPressed = true;
		else if(!ctrlPressed)
		{
			if(event.keyCode == KeyEnum.KEY_UP.getKeyCode())
				moveItemsUp(getSelectionIndices(), true);
			else if(event.keyCode == KeyEnum.KEY_DOWN.getKeyCode())
				moveItemsDown(getSelectionIndices(), true);
		}
	}

	private void onKeyUp(Event event)
	{
		if(event.keyCode == KeyEnum.KEY_CTRL.getKeyCode())
			ctrlPressed = false;
	}

	private void onMouseWheel(Event event)
	{
		if(!ctrlPressed || event.count == 0)
			return;

		if(event.count > 0)
			moveItemsUp(getSelectionIndices(), true);
		else
			moveItemsDown(getSelectionIndices(), true);
	};

	private void onMouseExit(Event event)
	{
		if(keyManager != null)
			keyManager.clear();
	};

	protected void onMenuDetect(Event event)
	{
		if(menuManager != null)
			setControlMenu(menuManager.createMenu(0));
	}

	protected void onSelection(Event event)
	{
		MenuItemEnum menuItem = (MenuItemEnum)event.widget.getData(MENU_ITEM_KEY);

		if(menuItem == null)
			return;

		if(menuItem == MI_COPY)
			onCopyMenuItem(event);
		else if(menuItem == MI_DELETE)
			onDeleteMenuItem(event);
		else if(menuItem == MI_SELECT_ALL)
			selectAll();
		else if(menuItem == MI_DESELECT_ALL)
			deselectAll();
		else if(menuItem == MI_DELETE)
			onDeleteMenuItem(event);
		else if(menuItem == MI_PROPERTIES)
			onPropertiesMenuItem(event);
	}

	protected void onCopyMenuItem(Event event)
	{
		List<T> items = getSelectedItems();

		if(isEmpty(items))
			return;

		Object[] data1 = new Object[] { ClipboardManager.buildOneStringData(toStringArrayList(items)) };
		Transfer[] dataTypes = new Transfer[] { TextTransfer.getInstance() };

		Clipboard cb = new Clipboard(parent.getDisplay());
		cb.setContents(data1, dataTypes);
		cb.dispose();
	}

	protected void onDeleteMenuItem(Event event)
	{
		if(!readonly && !getSelectedItems().isEmpty())
			deleteSelectedItems();
	}

	protected void onPropertiesMenuItem(Event event)
	{
		if(isEmpty(PREFERENCE_PAGE))
			return;

		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, PREFERENCE_PAGE, null, null);

		if(dialog.open() == Window.OK)
		{}
	}

	protected IModifyListener<T> modifyDefaultModel = new IModifyListener<T>()
	{
		@Override
		public void itemModified(Widget widget, T item, ModificationTypeEnum type)
		{
			defaultModel.modify(item, type);
		}
	};

	private IModelChange<T> onModelChanged = new IModelChange<T>()
	{
		@Override
		public void modelChanged(Model<T> model)
		{
			PViewer.this.modelChanged();
		}
	};

	private ISelectionChangedListener notifySelectionChangedListener = new ISelectionChangedListener()
	{
		@Override
		public void selectionChanged(SelectionChangedEvent event)
		{
			notifySelectionListeners(getSelectedItems());
		}
	};
}
