/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table;

import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.BitExtension.clearBits;
import static cop.common.extensions.BitExtension.isBitSet;
import static cop.common.extensions.CollectionExtension.EMPTY_STR_ARR_LIST;
import static cop.common.extensions.StringExtension.getText;
import static cop.swt.widgets.annotations.services.ColumnService.getColumnsSettings;
import static cop.swt.widgets.enums.SortDirectionEnum.SORT_OFF;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import cop.common.extensions.CollectionExtension;
import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;
import cop.swt.widgets.annotations.exceptions.AnnotationMissingException;
import cop.swt.widgets.menu.MenuBuilder;
import cop.swt.widgets.menu.interfaces.PropertyProvider;
import cop.swt.widgets.menu.items.PushMenuItem;
import cop.swt.widgets.menu.items.RadioKeyMenuItem;
import cop.swt.widgets.menu.items.SeparatorMenuItem;
import cop.swt.widgets.viewers.PViewer;
import cop.swt.widgets.viewers.interfaces.ItemModifyListener;
import cop.swt.widgets.viewers.interfaces.PModifyProvider;
import cop.swt.widgets.viewers.interfaces.Packable;
import cop.swt.widgets.viewers.table.columns.PTableColumn;
import cop.swt.widgets.viewers.table.columns.PTableColumnSet;
import cop.swt.widgets.viewers.table.columns.settings.AbstractColumnSettings;
import cop.swt.widgets.viewers.table.columns.settings.ColumnSettings;
import cop.swt.widgets.viewers.table.interfaces.TableColumnListener;

/*
 * ITableColorProvider
 * MarathonLia≈bilitiesControl
 */
public final class PTableViewer<T> extends PViewer<T> implements Packable
{
	private final PTableColumnSet<T> columns = new PTableColumnSet<T>();

	private boolean autoColumnWidth = true;
	private boolean mouseEnter;

	private TableFilter<T> filter;
	private PTableLabelProvider<T> labelProvider;

	// private TableConfig<T> config;
	// private AddListener<T> addListener;
	// private Set<String> controlNotifiers = new HashSet<String>();

	public PTableViewer(Class<T> cls, Composite parent, int style) throws Exception
	{
		this(cls, parent, style, null);
	}

	public PTableViewer(Class<T> cls, Composite parent, int style, TableViewerConfig config) throws Exception
	{
		super(cls, new TableViewer(parent, clearBits(style, SWT.READ_ONLY) | SWT.FULL_SELECTION), config);

		createColumns();
		setEditable(!isBitSet(style, SWT.READ_ONLY));
		addListeners();
		createLabelProvider();
		createFilter();
		postConstruct();
	}

	public void setSearchText(String text)
	{
		filter.setSearchText(text);
		labelProvider.setSearchText(text);
		widget.refresh();
	}

	public void setAutoColumnWidth(boolean autoColumnWidth)
	{
		this.autoColumnWidth = autoColumnWidth;
	}

	public TableViewer getWidget()
	{
		return (TableViewer)super.getWidget();
	}

	// public void setAddListener(AddListener<T> listener)
	// {
	// this.addListener = listener;
	// }

	public void editOnDoubleClick(boolean enabled)
	{
		((TableViewer)widget).cancelEditing();
		columns.setEditorEnabled(enabled);
	}

	public void setModifyProvider(PModifyProvider<T> provider)
	{
		columns.setModifyProvider(provider);
	}

	private void createColumns() throws AnnotationDeclarationException, AnnotationMissingException
	{
		List<ColumnSettings<T>> columnsSettings = getColumnsSettings(cls, getImageProvider());

		if(CollectionExtension.isEmpty(columnsSettings))
			throw new AnnotationMissingException("No column found. Use @Column annotation.");

		for(ColumnSettings<T> settings : columnsSettings)
			columns.add(new PTableColumn<T>(cls, this, settings));

		columns.setTableColumnListener(notifyTableColumnListener);
		columns.addPackableListener(this);

		pack();
	}

	private Menu createHeaderMenu()
	{
		return columns.addMenuItem(new Menu(parent));
	}

	private void addListeners()
	{
		Table table = (Table)widget.getControl();

		table.addListener(SWT.Resize, this);
		table.addListener(SWT.MouseExit, this);
		table.addListener(SWT.MouseMove, this);
		table.addListener(SWT.MouseDoubleClick, this);
		table.addListener(SWT.Dispose, this);
		table.addListener(SWT.PaintItem, this);
	}

	private final Runnable packTask = new Runnable()
	{
		@Override
		public void run()
		{
			if(!widget.getControl().isDisposed())
				columns.pack();
		}
	};

	private String[] getVisibleColumnNames()
	{
		List<String> names = new ArrayList<String>();

		for(PTableColumn<T> viewerColumn : columns.getColumns())
			if(!viewerColumn.isHidden())
				names.add(viewerColumn.getName());

		return names.toArray(new String[names.size()]);
	}

	private String[] getObjectVisibleFieldsString(T obj)
	{
		List<String> names = new ArrayList<String>();

		for(PTableColumn<T> column : columns.getColumns())
			if(!column.isHidden())
				names.add(getText(column.getColumnString(obj), ""));

		return names.toArray(new String[names.size()]);
	}

	public List<ColumnSettings<T>> getOrderTableColumns()
	{
		List<ColumnSettings<T>> res = new ArrayList<ColumnSettings<T>>();
		Table table = ((TableViewer)widget).getTable();
		PTableColumn<T> column;

		for(int pos : table.getColumnOrder())
		{
			column = columns.get(pos);

			if(column != null)
				res.add(column.getSettings());
		}

		return res;
	}

	// @Override
	// @SuppressWarnings("unchecked")
	// protected T[] getVisibleItems()
	// {
	// Table table = (Table)widget.getControl();
	// int itemCount = table.getItemCount();
	//
	// if(table.getItemCount() == 0)
	// return null;
	//
	// int topIndex = table.getTopIndex();
	// int itemHeight = table.getItemHeight();
	// int tableHeigth = table.getBounds().height;
	// int headerHight = 0;
	//
	// if(table.getHeaderVisible())
	// headerHight = table.getHeaderHeight();
	//
	// int visibleItemCount = min(itemCount - topIndex, (tableHeigth - headerHight) / itemHeight + 1);
	// TableItem[] items = table.getItems();
	// Object[] visibleItems = new Object[visibleItemCount]; // TODO need optimisation
	//
	// for(int i = topIndex; i < visibleItemCount - 1; i++)
	// visibleItems[i] = items[i].getData();
	//
	// return (T[])visibleItems;
	// }

	@Override
	public int getItemCount()
	{
		return ((Table)widget.getControl()).getItemCount();
	}

	/*
	 * tableColumnListener
	 */

	public void addTableColumnListener(TableColumnListener<T> listener)
	{
		if(listener != null)
			listeners.put(TableColumnListener.class, listener);
	}

	public void removeTableColumnListener(TableColumnListener<T> listener)
	{
		if(listener != null)
			listeners.remove(TableColumnListener.class, listener);
	}

	@SuppressWarnings("unchecked")
	private void notifyTableColumnResizedListeners(PTableViewer<T> viewer, ColumnSettings<T> column)
	{
		for(EventListener listener : listeners.get(TableColumnListener.class))
			((TableColumnListener<T>)listener).columnResized(viewer, column);
	}

	@SuppressWarnings("unchecked")
	private void notifyTableColumnMovedListeners(PTableViewer<T> viewer, ColumnSettings<T> column)
	{
		for(EventListener listener : listeners.get(TableColumnListener.class))
			((TableColumnListener<T>)listener).columnMoved(viewer, column);
	}

	/*
	 * TableColumnListener
	 */

	public void columnResized(TableColumnProperty resizedColumn, TableColumnProperty[] columns)
	{

	}

	public void columnMoved(TableColumnProperty movedColumn, TableColumnProperty[] columns)
	{

	}

	/*
	 * ModifyListenerSupport
	 */

	@Override
	public void addModifyListener(ItemModifyListener<T> listener)
	{
		super.addModifyListener(listener);
		columns.addModifyListener(listener);
	}

	@Override
	public void removeModifyListener(ItemModifyListener<T> listener)
	{
		super.removeModifyListener(listener);
		columns.removeModifyListener(listener);
	}

	/*
	 * IPack
	 */

	@Override
	public void pack()
	{
		// if(autoColumnWidth)
		Control control = widget.getControl();

		if(!control.isDisposed())
			control.getDisplay().syncExec(packTask);
	}

	/*
	 * PViewer
	 */

	@Override
	protected boolean isSortable()
	{
		return columns.isSortable();
	}

	@Override
	protected String[] getProperties()
	{
		return null;
	}

	@Override
	protected List<String[]> toStringArrayList(T[] items)
	{
		if(isEmpty(items))
			return EMPTY_STR_ARR_LIST;

		List<String[]> data = new ArrayList<String[]>(items.length + 1); // +1 for header

		data.add(getVisibleColumnNames());

		for(T item : items)
			data.add(getObjectVisibleFieldsString(item));

		return data;
	}

	@Override
	public void selectAll()
	{
		if(getSelectionSize() == getItemCount())
			return;

		TableViewer viewer = (TableViewer)widget;

		viewer.cancelEditing();
		viewer.getTable().selectAll();
	}

	@Override
	public void deselectAll()
	{
		if(getSelectionSize() == 0)
			return;

		TableViewer viewer = (TableViewer)widget;

		viewer.cancelEditing();
		viewer.getTable().deselectAll();
	}

	@Override
	public void setSorterOff()
	{
		if(!isSorterOn())
			return;

		columns.setSorterOff();
		refresh();
	}

	@Override
	protected int getTopIndex()
	{
		return ((Table)widget.getControl()).getTopIndex();
	}

	@Override
	protected int[] getSelectionIndices()
	{
		return ((Table)widget.getControl()).getSelectionIndices();
	}

	@Override
	protected void setTopIndex(int index)
	{
		((Table)widget.getControl()).setTopIndex(index);
	}

	@Override
	protected void setSelected(int index, boolean selected)
	{
		if(selected)
			getWidget().getTable().select(index);
		else
			getWidget().getTable().deselect(index);
	}

	@Override
	protected void postConstruct()
	{
		final Table table = ((TableViewer)widget).getTable();

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		super.postConstruct();
	}

	@Override
	public void refresh()
	{
		super.refresh();
		pack();
	}

	/*
	 * Editable
	 */

	@Override
	public void setEditable(boolean editable)
	{
		super.setEditable(editable);

		if(!editable)
			getWidget().cancelEditing();

		columns.setEditable(editable);
	}

	/*
	 * Control
	 */

	public void setLayoutData(Object layoutData)
	{
		widget.getControl().setLayoutData(layoutData);
	}

	/*
	 * Table
	 */

	public void setLinesVisible(boolean show)
	{
		((TableViewer)widget).getTable().setLinesVisible(show);
	}

	public void setHeaderVisible(boolean show)
	{
		((TableViewer)widget).getTable().setHeaderVisible(show);
	}

	private void createLabelProvider()
	{
		widget.setLabelProvider(labelProvider = new PTableLabelProvider<T>(columns));
	}

	/*
	 * StructuredViewer
	 */

	private void createFilter()
	{
		filter = new TableFilter<T>(columns);
		widget.addFilter(filter);
	}

	/*
	 * Listener
	 */

	@Override
	public void handleEvent(Event event)
	{
		super.handleEvent(event);

		if(event.widget == widget.getControl())
		{
			if(event.type == SWT.PaintItem)
				onPaintItem(event);
			else if(event.type == SWT.MouseDoubleClick)
				onMouseDoubleClick(event);
			else if(event.type == SWT.MouseMove)
				onMouseMove(event);
			else if(event.type == SWT.MouseExit)
				onMouseExit(event);
			else if(event.type == SWT.Resize)
				onResize(event);
		}
	}

	/*
	 * listeners
	 */

	private void onPaintItem(Event event)
	{
		columns.handleEvent(event);
	};

	private void onMouseDoubleClick(Event event)
	{
		if(event.button != 1)
			return;

		TableViewer viewer = (TableViewer)widget;
		Table table = viewer.getTable();
		TableItem[] items = table.getSelection();

		if(isEmpty(items) || items.length != 1)
			return;

		int index = getSelectedColumnIndex(items[0], event.x, event.y);

		if(index < 0)
			return;

		PTableColumn<T> column = columns.get(index);

		column.setEditorEnabled(true);
		viewer.editElement(items[0].getData(), index);
		column.setEditorEnabled(false);
	}

	private void onMouseExit(Event event)
	{
		mouseEnter = false;
	};

	private void onMouseMove(Event event)
	{
		mouseEnter = true;
	};

	private void onResize(Event event)
	{
		if(!autoColumnWidth)
			return;

		Table table = ((TableViewer)widget).getTable();
		int tableWidth = table.getBounds().width;

		if(tableWidth == 0)
			return;

		table.setRedraw(false);
		columns.setRelativeWidth();
		table.setRedraw(true);
	};

	private int getSelectedColumnIndex(TableItem item, int x, int y)
	{
		for(int i = 0, size = columns.getSize(); i < size; i++)
			if(item.getBounds(i).contains(x, y))
				return i;

		return -1;
	}

	private TableColumnListener<T> notifyTableColumnListener = new TableColumnListener<T>()
	{
		@Override
		public void columnMoved(PTableViewer<T> viewer, ColumnSettings<T> column)
		{
			if(!autoColumnWidth)
				notifyTableColumnMovedListeners(viewer, column);
		}

		@Override
		public void columnResized(PTableViewer<T> viewer, ColumnSettings<T> column)
		{
			if(!autoColumnWidth)
				notifyTableColumnResizedListeners(viewer, column);
		}
	};

	@Override
	protected MenuBuilder createSortMenuBuilder()
	{
		MenuBuilder menuBuilder = new MenuBuilder(getImageProvider());
		ColumnSettings<T> settings;
		PropertyProvider<Boolean> visibleProvider;
		PropertyProvider<Boolean> enabledProvider;
		PropertyProvider<Boolean> selectionProvider;
		Listener listener;

		menuBuilder.addMenuItem(new PushMenuItem(SORT_OFF, null, isSorterOnProvider, this));
		menuBuilder.addMenuItem(new SeparatorMenuItem());

		for(PTableColumn<T> column : columns.getColumns())
		{
			settings = column.getSettings();

			if(!settings.isSortable())
				continue;

			visibleProvider = null;
			enabledProvider = null;
			selectionProvider = getColumnStateSelectionProvider(column);
			listener = column;

			menuBuilder.addMenuItem(new RadioKeyMenuItem<T>(cls, settings.getKey(), visibleProvider, enabledProvider,
			                selectionProvider, listener));
		}

		return menuBuilder;
	}

	private PropertyProvider<Boolean> isSorterOnProvider = new PropertyProvider<Boolean>()
	{
		@Override
		public Boolean getProperty()
		{
			return isSorterOn();
		}
	};

	private PropertyProvider<Boolean> getColumnStateSelectionProvider(final PTableColumn<T> column)
	{
		PropertyProvider<Boolean> provider = new PropertyProvider<Boolean>()
		{
			@Override
			public Boolean getProperty()
			{
				return column.isSorterOn();
			}
		};

		return provider;
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		((TableViewer)widget).cancelEditing();
		columns.setLocale(locale);
		refresh();

		if(autoColumnWidth)
			pack();
	}

	/*
	 * Listeners
	 */

	@Override
	protected void onMenuDetect(Event event)
	{
		if(menuManager == null)
			return;

		if(mouseEnter)
			setControlMenu(menuManager.createMenu());
		else
			setControlMenu(createHeaderMenu());
	}
}
