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
import static cop.swt.widgets.enums.SortDirectionEnum.SORT_OFF;
import static cop.swt.widgets.keys.HotKey.keyCtrlDown;
import static cop.swt.widgets.keys.HotKey.keyCtrlUp;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_CTRL;
import static cop.swt.widgets.menu.enums.MenuItemEnum.MENU_ITEM_ENUM;
import static cop.swt.widgets.menu.enums.MenuItemEnum.MI_COPY;
import static cop.swt.widgets.menu.enums.MenuItemEnum.MI_DELETE;
import static cop.swt.widgets.menu.enums.MenuItemEnum.MI_DESELECT_ALL;
import static cop.swt.widgets.menu.enums.MenuItemEnum.MI_PROPERTIES;
import static cop.swt.widgets.menu.enums.MenuItemEnum.MI_SELECT_ALL;
import static cop.swt.widgets.menu.enums.MenuItemEnum.MI_SORT;
import static cop.swt.widgets.viewers.model.enums.ModificationTypeEnum.REMOVE;
import static org.eclipse.swt.SWT.Dispose;
import static org.eclipse.swt.SWT.KeyDown;
import static org.eclipse.swt.SWT.KeyUp;
import static org.eclipse.swt.SWT.MenuDetect;
import static org.eclipse.swt.SWT.MouseExit;
import static org.eclipse.swt.SWT.MouseWheel;

import java.util.Collection;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

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
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import cop.common.extensions.BitExtension;
import cop.managers.ClipboardManager;
import cop.swt.images.ImageProvider;
import cop.swt.widgets.interfaces.Clearable;
import cop.swt.widgets.interfaces.Editable;
import cop.swt.widgets.interfaces.Enablable;
import cop.swt.widgets.interfaces.Refreshable;
import cop.swt.widgets.keys.HotKey;
import cop.swt.widgets.keys.HotKeyManager;
import cop.swt.widgets.localization.interfaces.LocaleSupport;
import cop.swt.widgets.menu.MenuBuilder;
import cop.swt.widgets.menu.MenuManager;
import cop.swt.widgets.menu.enums.MenuItemEnum;
import cop.swt.widgets.menu.interfaces.IMenuBuilder;
import cop.swt.widgets.menu.interfaces.MenuItemKey;
import cop.swt.widgets.menu.interfaces.PropertyProvider;
import cop.swt.widgets.menu.items.CascadeMenuItem;
import cop.swt.widgets.menu.items.PushMenuItem;
import cop.swt.widgets.menu.items.SeparatorMenuItem;
import cop.swt.widgets.model.interfaces.ModelChangedListener;
import cop.swt.widgets.viewers.interfaces.ItemModifyListener;
import cop.swt.widgets.viewers.interfaces.ModifyListenerSupport;
import cop.swt.widgets.viewers.interfaces.SelectionListenerSupport;
import cop.swt.widgets.viewers.interfaces.ViewerConfig;
import cop.swt.widgets.viewers.model.ListModel;
import cop.swt.widgets.viewers.model.enums.ModificationTypeEnum;
import cop.swt.widgets.viewers.model.interfaces.ModelSupport;
import cop.swt.widgets.viewers.model.interfaces.ViewerModel;

public abstract class PViewer<T> implements ModelSupport<T>, LocaleSupport, ModifyListenerSupport<T>,
                SelectionListenerSupport, Clearable, Refreshable, Listener, ModelChangedListener<T>, Editable, Enablable
{
	protected final Composite parent;
	protected final StructuredViewer widget;
	protected final Class<T> cls;
	protected final Multimap<Class<? extends EventListener>, EventListener> listeners = ArrayListMultimap.create();

	// protected final ImageProviderImpl imageProvider = new ImageProviderImpl();
	protected ViewerConfig config;

	// manager
	private HotKeyManager hotKeyManager;
	protected MenuManager menuManager;
	// property flags
	private boolean editable;
	private final String preferencePage = null;
	// model
	protected ViewerModel<T> model;
	private boolean standaloneMode;

	protected PViewer(Class<T> cls, StructuredViewer viewer, ViewerConfig config)
	{
		this.cls = cls;
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

	public boolean isSorterOn()
	{
		return widget.getSorter() != null;
	}

	public StructuredViewer getWidget()
	{
		return widget;
	}

	protected void swap(int index1, int index2)
	{
		if(standaloneMode)
			((ListModel<T>)model).swap(index1, index2);
	}

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
		menuBuilder.addMenuItem(new CascadeMenuItem(widget.getControl(), hotKeyManager, MI_SORT,
		                createSortMenuBuilder(), isSortable, null));
		menuBuilder.addMenuItem(new SeparatorMenuItem());
		menuBuilder.addMenuItem(new PushMenuItem(MI_PROPERTIES, isPropertiesVisible, null, this));

		return menuBuilder;
	}

	private void createMenuManager()
	{
		menuManager = new MenuManager(widget.getControl(), hotKeyManager, createMenuBuilder());
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
			return !listeners.isEmpty();
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
			return isEditable() && getSelectionSize() != 0;
		}
	};

	private void createHotKeys()
	{
		hotKeyManager = new HotKeyManager(widget.getControl());

		hotKeyManager.addHotKey(keyCtrlUp, this);
		hotKeyManager.addHotKey(keyCtrlDown, this);
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
		if(event.widget != widget.getControl())
			return getRootMenu((MenuItem)event.widget) == widget.getControl().getMenu();

		if(!(event.data instanceof Properties))
			return false;

		return ((Properties)event.data).get(MenuItemEnum.MENU_ITEM_ENUM) instanceof MenuItemEnum;
	}

	private boolean isControlEvent(Event event)
	{
		return event.widget == widget.getControl();
	}

	public abstract void setSorterOff();

	protected static Menu getRootMenu(MenuItem item)
	{
		if(item == null)
			return null;

		Menu menu = item.getParent();
		Menu menuNext;

		while((menuNext = menu.getParentMenu()) != null)
		{
			menu = menuNext;
		}

		return menu;
	}

	/*
	 * abstract
	 */

	public abstract int getItemCount();

	public abstract void selectAll();

	public abstract void deselectAll();

	protected abstract List<String[]> toStringArrayList(T[] items);

	protected abstract String[] getProperties();

	protected abstract int getTopIndex();

	protected abstract int[] getSelectionIndices();

	protected abstract void setTopIndex(int index);

	protected abstract void setSelected(int index, boolean selected);

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

		// if(isSorterOn() && isE)
		// refresh();
	}

	/*
	 * ModifyListenerSupport
	 */

	@Override
	public void addModifyListener(ItemModifyListener<T> listener)
	{
		if(listener != null)
			listeners.put(ItemModifyListener.class, listener);
	}

	@Override
	public void removeModifyListener(ItemModifyListener<T> listener)
	{
		if(listener != null)
			listeners.remove(ItemModifyListener.class, listener);
	}

	@SuppressWarnings("unchecked")
    private void notifyModifyListener(T item, ModificationTypeEnum type)
	{
		for(EventListener listener : listeners.get(ItemModifyListener.class))
			((ItemModifyListener<T>)listener).itemModified(this, item, type);
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
	 * Editable
	 */

	@Override
	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	@Override
	public boolean isEditable()
	{
		return editable;
	}
	
	/*
	 * Enablable
	 */
	
	@Override
	public void setEnabled(boolean enabled)
	{
		widget.getControl().setEnabled(enabled);
	}

	@Override
	public boolean isEnabled()
	{
		return widget.getControl().isEnabled();
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
		else if(event.type == MouseWheel)
			onMouseWheel(event);
		else if(event.type == MouseExit)
			onMouseExit(event);
		else if(event.type == MenuDetect)
			onMenuDetect(event);
	}

	protected void handleMenuEvent(Event event)
	{
		MenuItemKey menuItem = null;

		if(!(event.data instanceof Properties))
			menuItem = (MenuItemKey)event.widget.getData(MENU_ITEM_ENUM);
		else
			menuItem = ((MenuItemKey)(((Properties)event.data).get(MenuItemEnum.MENU_ITEM_ENUM)));

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
		else if(menuItem == SORT_OFF)
			setSorterOff();
	}

	private void onKeyDown(Event event)
	{
		if(!(event.data instanceof Properties))
			return;

		Properties prop = (Properties)event.data;
		HotKey hotKey = (HotKey)prop.get(HotKeyManager.HOT_KEY);

		if(keyCtrlUp.equals(hotKey))
			moveItemsUp(getSelectionIndices(), true);
		else if(keyCtrlDown.equals(hotKey))
			moveItemsDown(getSelectionIndices(), true);
	}

	private void onMouseWheel(Event event)
	{
		if(event.count == 0 || !hotKeyManager.isKeyPressed(KEY_CTRL))
			return;

		if(event.count > 0)
			moveItemsUp(getSelectionIndices(), true);
		else
			moveItemsDown(getSelectionIndices(), true);
	};

	private void onMouseExit(Event event)
	{
		if(hotKeyManager != null)
			hotKeyManager.clear();
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

		Object[] data = new Object[] { ClipboardManager.buildOneStringData(toStringArrayList(items)) };
		Transfer[] dataTypes = new Transfer[] { TextTransfer.getInstance() };

		Clipboard cb = new Clipboard(parent.getDisplay());
		cb.setContents(data, dataTypes);
		cb.dispose();
	}

	protected void onDeleteMenuItem(Event event)
	{
		if(editable && getSelectionSize() != 0)
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

	protected ItemModifyListener<T> modifyDefaultModel = new ItemModifyListener<T>()
	{
		@Override
		public void itemModified(PViewer<T> viewer, T item, ModificationTypeEnum type)
		{
			if(standaloneMode)
				((ListModel<T>)model).modify(item, type);
		}
	};
}
