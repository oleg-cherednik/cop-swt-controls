/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers;

import static cop.algorithms.search.LinearSearch.linearSearch;
import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.ArrayExtension.isNotEmpty;
import static cop.common.extensions.ArrayExtension.removeDublicatesAndSort;
import static cop.common.extensions.CollectionExtension.EMPTY_LIST;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.StringExtension.isEmpty;
import static cop.common.extensions.StringExtension.isNotEmpty;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_CTRL;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_DOWN;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_UP;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MENU_ITEM_ENUM;
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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.StructuredSelection;
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
import cop.swt.widgets.localization.interfaces.LocaleSupport;
import cop.swt.widgets.menus.MenuBuilder;
import cop.swt.widgets.menus.MenuManager;
import cop.swt.widgets.menus.enums.MenuItemEnum;
import cop.swt.widgets.menus.interfaces.IMenuBuilder;
import cop.swt.widgets.menus.interfaces.IMenuItem;
import cop.swt.widgets.menus.interfaces.PropertyProvider;
import cop.swt.widgets.menus.items.CascadeMenuItem;
import cop.swt.widgets.menus.items.PushMenuItem;
import cop.swt.widgets.menus.items.SeparatorMenuItem;
import cop.swt.widgets.menus.items.basics.AbstractMenuItem;
import cop.swt.widgets.model.interfaces.ModelChanged;
import cop.swt.widgets.viewers.interfaces.IModifyListener;
import cop.swt.widgets.viewers.interfaces.ModifyListenerSupport;
import cop.swt.widgets.viewers.interfaces.SelectionListenerSupport;
import cop.swt.widgets.viewers.model.ListModel;
import cop.swt.widgets.viewers.model.enums.ModificationTypeEnum;
import cop.swt.widgets.viewers.model.interfaces.ModelSupport;
import cop.swt.widgets.viewers.model.interfaces.ViewerModel;
import cop.swt.widgets.viewers.table.interfaces.ViewerConfig;

public abstract class PViewer<T> implements ModelSupport<T>, LocaleSupport, ModifyListenerSupport<T>,
                SelectionListenerSupport, Clearable, Refreshable, Listener, ModelChanged<T>
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
	private final String preferencePage = null;
	// model
	protected ViewerModel<T> model;
	private boolean standaloneMode;
	// listeners
	private Set<IModifyListener<T>> modifyListeners = new HashSet<IModifyListener<T>>();

	protected PViewer(T obj, StructuredViewer viewer, ViewerConfig config)
	{
		Assert.isNotNull(obj);
		Assert.isNotNull(viewer);

		this.obj = obj;
		this.widget = viewer;
		this.parent = widget.getControl().getParent();
		this.config = config;

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
		return widget.getSorter() != null;
	}

	protected void swap(int index1, int index2)
	{
		if(standaloneMode)
			((ListModel<T>)model).swap(index1, index2);
	}

	protected abstract int getTopIndex();

	protected abstract int[] getSelectionIndices();

	protected abstract void setTopIndex(int index);

	protected abstract void setSelected(int index, boolean selected);

	private void moveItemsUp(int[] indices, boolean keepTopIndex)
	{
		if(!standaloneMode || isSorterOn())
			return;

		int size = ((ListModel<T>)model).getItemCount();

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
		if(!standaloneMode || isSorterOn())
			return;

		int size = ((ListModel<T>)model).getItemCount();

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
			return getSelectedItems().length != 0;
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
			return isNotEmpty(preferencePage);
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
			return getItemCount() != getSelectionSize();
		}
	};

	private PropertyProvider<Boolean> isDeselectAllEnabled = new PropertyProvider<Boolean>()
	{
		@Override
		public Boolean getProperty()
		{
			return getSelectionSize() != 0;
		}
	};

	private PropertyProvider<Boolean> isDeleteEnabled = new PropertyProvider<Boolean>()
	{
		@Override
		public Boolean getProperty()
		{
			return !isReadonly() && getSelectionSize() != 0;
		}
	};

	private void createHotKeys()
	{
		keyManager = new HotKeyManager(widget.getControl());
	}

	public List<T> getItems()
	{
		return (List<T>)model.getElements(null);
	}

	public void setItems(Collection<T> items)
	{
		Assert.isTrue(standaloneMode, "setItems() must be used only in non-model mode");

		((ListModel<T>)model).set(items);
	}

	protected final void setControlMenu(Menu menu)
	{
		widget.getControl().setMenu((menu != null && menu.getItemCount() != 0) ? menu : null);
	}

	public T[] getSelectedItems()
	{
		StructuredSelection selection = (StructuredSelection)widget.getSelection();
		return (T[])selection.toArray();
	}

	public int getSelectionSize()
	{
		StructuredSelection selection = (StructuredSelection)widget.getSelection();
		return selection.size();
	}

	private void setStandaloneMode()
	{
		beginListenToModel(new ListModel<T>(getClass().getCanonicalName() + ".default"));
		standaloneMode = true;
	}

	private void deleteSelectedItems()
	{
		T[] items = getSelectedItems();

		if(isNotEmpty(items))
			for(T item : items)
				notifyModifyListener(item, REMOVE);
	}

	public void dispose()
	{
		model.removeListener(this);
		model = null;
	}

	public void setEnabled(boolean enabled)
	{
		widget.getControl().setEnabled(enabled);
	}

	public boolean isEnabled()
	{
		return widget.getControl().isEnabled();
	}

	private final Runnable refreshTask = new Runnable()
	{
		@Override
		public void run()
		{
			if(!Thread.currentThread().isInterrupted() && !widget.getControl().isDisposed())
				widget.refresh();
		}
	};

	private boolean isMenuHandleEvent(Event event)
	{
		if(event.widget == widget.getControl())
			return event.data instanceof IMenuItem;

		return ((MenuItem)event.widget).getParent() == widget.getControl().getMenu();
	}

	private boolean isControlEvent(Event event)
	{
		return event.widget == widget.getControl();
	}

	/*
	 * abstract
	 */

	protected abstract List<String[]> toStringArrayList(T[] items);

	protected abstract String[] getProperties();

	public abstract void setSorterOff();

	public abstract int getItemCount();

	public abstract void selectAll();

	public abstract void deselectAll();

	/*
	 * IModelChange
	 */

	@Override
	@SuppressWarnings("unchecked")
	public void modelChanged(T... items) // TODO maybe it's better to give an update type
	{
		if(isEmpty(items))
			widget.setInput(model.getElements(null));
		else
			widget.update(items, getProperties());
	}

	/*
	 * ModifyListenerSupport
	 */

	@Override
	public void addModifyListener(IModifyListener<T> listener)
	{
		if(listener != null)
			modifyListeners.add(listener);
	}

	@Override
	public void removeModifyListener(IModifyListener<T> listener)
	{
		if(listener != null)
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
		if(model == null || this.model == model)
			return;

		if(this.model != null)
			this.model.removeListener(this);

		model.addListener(this);

		this.model = model;
		this.widget.setContentProvider(new ContentProviderAdapter<T>(model));
		this.widget.setInput(EMPTY_LIST);

		standaloneMode = false;
	}

	@Override
	public void stopListenToModel(ViewerModel<T> model)
	{
		if(model == this.model)
			setStandaloneMode();
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		if(menuManager != null)
			menuManager.setLocale(locale);
	}

	/*
	 * SelectionListenerSupport
	 */

	@Override
	public void addSelectionListener(ISelectionChangedListener listener)
	{
		widget.addSelectionChangedListener(listener);
	}

	@Override
	public void removeSelectionListener(ISelectionChangedListener listener)
	{
		widget.removeSelectionChangedListener(listener);
	}

	/*
	 * Refreshable
	 */

	@Override
	public void refresh()
	{
		if(!widget.getControl().isDisposed())
			widget.getControl().getDisplay().syncExec(refreshTask);
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
		if(isMenuHandleEvent(event))
			handleMenuEvent(event);
		else if(isControlEvent(event))
			handleControlEvent(event);
	}

	/*
	 * listeners
	 */

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
		MenuItemEnum menuItem = null;

		if(event.data instanceof AbstractMenuItem)
			menuItem = ((AbstractMenuItem)event.data).getMenuItemKey();
		else
			menuItem = (MenuItemEnum)event.widget.getData(MENU_ITEM_ENUM);

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

	/*
	 * listeners
	 */

	private void onKeyDown(Event event)
	{
		if(event.keyCode == KEY_CTRL.getKeyCode())
			ctrlPressed = true;
		else if(ctrlPressed)
		{
			if(event.keyCode == KEY_UP.getKeyCode())
				moveItemsUp(getSelectionIndices(), true);
			else if(event.keyCode == KEY_DOWN.getKeyCode())
				moveItemsDown(getSelectionIndices(), true);
		}
	}

	private void onKeyUp(Event event)
	{
		if(event.keyCode == KEY_CTRL.getKeyCode())
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
			setControlMenu(menuManager.createMenu());
	}

	protected void onCopyMenuItem(Event event)
	{
		T[] items = getSelectedItems();

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
		if(!readonly && getSelectionSize() != 0)
			deleteSelectedItems();
	}

	protected void onPropertiesMenuItem(Event event)
	{
		if(isEmpty(preferencePage))
			return;

		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, preferencePage, null, null);

		if(dialog.open() == Window.OK)
		{}
	}

	protected IModifyListener<T> modifyDefaultModel = new IModifyListener<T>()
	{
		@Override
		public void itemModified(Widget widget, T item, ModificationTypeEnum type)
		{
			if(standaloneMode)
				((ListModel<T>)model).modify(item, type);
		}
	};
}
