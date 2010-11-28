/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table;

import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.BitExtension.clearBits;
import static cop.common.extensions.BitExtension.isBitSet;
import static cop.common.extensions.CollectionExtension.EMPTY_STR_ARR_LIST;
import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.getText;
import static cop.swt.widgets.annotations.services.ColumnService.getDescriptions;
import static cop.swt.widgets.enums.SortDirectionEnum.SORT_OFF;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MI_OFF;
import static cop.swt.widgets.viewers.table.AbstractViewerSorter.DEFAULT_SORT_DIRECTION;
import static org.eclipse.swt.SWT.Dispose;
import static org.eclipse.swt.SWT.FULL_SELECTION;
import static org.eclipse.swt.SWT.MouseDoubleClick;
import static org.eclipse.swt.SWT.MouseExit;
import static org.eclipse.swt.SWT.MouseMove;
import static org.eclipse.swt.SWT.PaintItem;
import static org.eclipse.swt.SWT.READ_ONLY;
import static org.eclipse.swt.SWT.Resize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import cop.swt.preferences.EmployeeListPreferencePage;
import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;
import cop.swt.widgets.annotations.exceptions.AnnotationMissingException;
import cop.swt.widgets.menus.MenuBuilder;
import cop.swt.widgets.menus.interfaces.PropertyProvider;
import cop.swt.widgets.menus.items.PushMenuItem;
import cop.swt.widgets.menus.items.RadioDescriptionMenuItem;
import cop.swt.widgets.menus.items.SeparatorMenuItem;
import cop.swt.widgets.viewers.PViewer;
import cop.swt.widgets.viewers.ibm._IStructuredContentProvider;
import cop.swt.widgets.viewers.interfaces.IModifyListener;
import cop.swt.widgets.viewers.interfaces.IModifyProvider;
import cop.swt.widgets.viewers.interfaces.Packable;
import cop.swt.widgets.viewers.table.descriptions.ColumnDescription;
import cop.swt.widgets.viewers.table.interfaces.TableColumnListener;

/*
 * ITableColorProvider
 * MarathonLiabilitiesControl
 */
public final class PTableViewer<T> extends PViewer<T> implements Packable
{
	private Map<Integer, PTableColumnInfo<T>> columns = new HashMap<Integer, PTableColumnInfo<T>>();
	private boolean autoColumnWidth = true;
	private boolean mouseEnter;
	private Set<TableColumnListener<T>> tableColumnListeners = new HashSet<TableColumnListener<T>>();
	private TableFilter<T> filter;
	private PTableLabelProvider<T> labelProvider;
	// private TableConfig<T> config;
	// private AddListener<T> addListener;

	private static final String PREFERENCE_PAGE = EmployeeListPreferencePage.class.getName();

	// private Set<String> controlNotifiers = new HashSet<String>();

	public PTableViewer(T obj, Composite parent, int style) throws Exception
	{
		this(obj, parent, style, null);
	}

	public PTableViewer(T obj, Composite parent, int style, TableViewerConfig config) throws Exception
	{
		super(obj, new TableViewer(parent, clearBits(style, READ_ONLY) | FULL_SELECTION), PREFERENCE_PAGE, config);

		createColumns();
		setReadonly(isBitSet(style, READ_ONLY));
		addListeners(obj);
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

	// public void setAddListener(AddListener<T> listener)
	// {
	// this.addListener = listener;
	// }

	public void editOnDoubleClick(boolean enabled)
	{
		System.out.println("PtableViewer.editOnDoubleClick() - cancelEditing()");
		TableViewer viewer = (TableViewer)widget;

		viewer.cancelEditing();

		for(PTableColumnInfo<T> column : columns.values())
			column.setEditorEnabled(!enabled);
	}

	public void setReadonlyProvider(IModifyProvider<T> provider)
	{
		for(PTableColumnInfo<T> viewerColumn : columns.values())
			viewerColumn.setReadonlyProvider(provider);
	}

	private void createColumns() throws AnnotationDeclarationException, AnnotationMissingException
	{
		Assert.isNotNull(obj);

		List<? extends ColumnDescription<T>> descriptions = getDescriptions(obj.getClass(), getImageProvider());

		if(isEmpty(descriptions))
			throw new AnnotationMissingException("No column found. Use @Column annotation.");

		PTableColumnInfo<T> column;
		TableViewer viewer = (TableViewer)widget;
		int index = 0;

		// new PTableColumnInfo<T>(obj, viewer, descriptions.get(0)).setHidden(true);

		for(ColumnDescription<T> description : descriptions)
		{
			columns.put(index++, column = new PTableColumnInfo<T>(obj, viewer, description));
			column.setTableColumnListener(notifyTableColumnListener);
			column.addPackableListener(this);
		}

		pack();
	}

	private Menu createHeaderMenu()
	{
		try
		{
			Menu menu = new Menu(parent);

			for(PTableColumnInfo<T> viewerColumn : columns.values())
				viewerColumn.setMenu(menu);

			return menu;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private void addListeners(T obj)
	{
		if(isEmpty(columns) || isNull(obj))
			return;

		Table table = (Table)widget.getControl();

		table.addListener(Resize, this);
		table.addListener(MouseExit, this);
		table.addListener(MouseMove, this);
		table.addListener(MouseDoubleClick, this);
		table.addListener(Dispose, this);
		table.addListener(PaintItem, this);
	}

	private final Runnable packTask = new Runnable()
	{
		@Override
		public void run()
		{
			if(!widget.getControl().isDisposed())
				for(PTableColumnInfo<T> column : columns.values())
					column.pack();
		}
	};

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
		tableColumnListeners.add(listener);
	}

	public void removeTableColumnListener(TableColumnListener<T> listener)
	{
		tableColumnListeners.remove(listener);
	}

	private void notifyTableColumnResizedListeners(ColumnDescription<T> resizedColumn,
	                List<ColumnDescription<T>> columns)
	{
		for(TableColumnListener<T> listener : tableColumnListeners)
			listener.columnResized(resizedColumn, columns);
	}

	private void notifyTableColumnMovedListeners(ColumnDescription<T> movedColumn, List<ColumnDescription<T>> columns)
	{
		for(TableColumnListener<T> listener : tableColumnListeners)
			listener.columnMoved(movedColumn, columns);
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
	public void addModifyListener(IModifyListener<T> listener)
	{
		if(isNull(listener))
			return;

		super.addModifyListener(listener);

		for(PTableColumnInfo<T> column : columns.values())
			column.addModifyListener(listener);
	}

	@Override
	public void removeModifyListener(IModifyListener<T> listener)
	{
		if(isNull(listener))
			return;

		super.removeModifyListener(listener);

		for(PTableColumnInfo<T> column : columns.values())
			column.removeModifyListener(listener);
	}

	/*
	 * IPack
	 */

	@Override
	public void pack()
	{
		if(!widget.getControl().isDisposed())
			widget.getControl().getDisplay().syncExec(packTask);
	}

	/*
	 * PViewer
	 */

	@Override
	protected boolean isSortable()
	{
		for(PTableColumnInfo<T> column : columns.values())
			if(column.isSortable())
				return true;

		return false;
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

		List<String[]> data = new ArrayList<String[]>(items.length + 1);

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

		((Table)widget.getControl()).selectAll();
		super.selectAll();
	}

	@Override
	public void deselectAll()
	{
		if(getSelectionSize() == 0)
			return;

		TableViewer viewer = (TableViewer)widget;

		viewer.cancelEditing();
		viewer.getTable().deselectAll();

		super.deselectAll();
	}

	@Override
	public void setSorterOff()
	{
		if(!isSorterOn())
			return;

		for(PTableColumnInfo<T> column : columns.values())
			column.setSorterDirection(SORT_OFF);

		refresh();
	}

	@Override
	protected int getTopIndex()
	{
		Assert.isNotNull(widget);

		return ((Table)widget.getControl()).getTopIndex();
	}

	@Override
	protected int[] getSelectionIndices()
	{
		Assert.isNotNull(widget);

		return ((Table)widget.getControl()).getSelectionIndices();
	}

	@Override
	protected void setTopIndex(int index)
	{
		Assert.isNotNull(widget);

		((Table)widget.getControl()).setTopIndex(index);
	}

	@Override
	protected void setSelected(int index, boolean selected)
	{
		if(selected)
			((TableViewer)widget).getTable().select(index);
		else
			((TableViewer)widget).getTable().deselect(index);
	}

	@Override
	public void setReadonly(boolean readonly)
	{
		super.setReadonly(readonly);

		((TableViewer)widget).cancelEditing();

		for(PTableColumnInfo<T> viewerColumn : columns.values())
			viewerColumn.setReadonly(readonly);
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

	/*
	 * StructuredViewer
	 */

	public void setContentProvider(_IStructuredContentProvider<T> provider)
	{
		// if(isNull(provider))
		// {
		// if(isNull(model))
		// model = new LocalContentProvider();
		//
		// return;
		// }
		//
		// if(isNotNull(model))
		// model.dispose();
		//
		// //model = provider;
		//
		// viewer.setContentProvider(new ContentProviderAdapter<T>(provider));
	}

	/*
	 * ColumnViewer
	 */

	private void createLabelProvider()
	{
		labelProvider = new PTableLabelProvider<T>(columns);
		widget.setLabelProvider(labelProvider);
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
			if(event.type == PaintItem)
				onPaintItem(event);
			else if(event.type == MouseDoubleClick)
				onMouseDoubleClick(event);
			else if(event.type == MouseMove)
				onMouseMove(event);
			else if(event.type == MouseExit)
				onMouseExit(event);
			else if(event.type == Resize)
				onResize(event);
		}
	}

	/*
	 * Listeners
	 */

	private void onPaintItem(Event event)
	{
		PTableColumnInfo<T> column = columns.get(event.index);

		if(column != null)
			column.handleEvent(event);
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

		// TODO why true is here?
		columns.get(index).setEditorEnabled(true);
		viewer.editElement(items[0].getData(), index);
		columns.get(index).setEditorEnabled(false);
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

		for(PTableColumnInfo<T> viewerColumn : columns.values())
			viewerColumn.setRelativeWidth();

		table.setRedraw(true);
	};

	private int getSelectedColumnIndex(TableItem item, int x, int y)
	{
		Assert.isNotNull(item);

		for(int i = 0, size = columns.size(); i < size; i++)
			if(item.getBounds(i).contains(x, y))
				return i;

		return -1;
	}

	private TableColumnListener<T> notifyTableColumnListener = new TableColumnListener<T>()
	{
		@Override
		public void columnMoved(ColumnDescription<T> movedColumn, List<ColumnDescription<T>> columns)
		{
			if(!autoColumnWidth)
				notifyTableColumnMovedListeners(movedColumn, getOrderTableColumns());
		}

		@Override
		public void columnResized(ColumnDescription<T> resizedColumn, List<ColumnDescription<T>> columns)
		{
			if(!autoColumnWidth)
				notifyTableColumnResizedListeners(resizedColumn, getOrderTableColumns());
		}

		private List<ColumnDescription<T>> getOrderTableColumns()
		{
			List<ColumnDescription<T>> res = new ArrayList<ColumnDescription<T>>();
			Table table = ((TableViewer)widget).getTable();
			PTableColumnInfo<T> info;

			for(int pos : table.getColumnOrder())
			{
				info = columns.get(pos);

				if(info != null)
					res.add(info.getDescription());
			}

			return res;
		}
	};

	@Override
	protected MenuBuilder createSortMenuBuilder()
	{
		MenuBuilder menuBuilder = new MenuBuilder(getImageProvider());
		ColumnDescription<T> description;

		menuBuilder.addMenuItem(new PushMenuItem(MI_OFF, null, isSorterOnProvider, setSorterOffListener));
		menuBuilder.addMenuItem(new SeparatorMenuItem());

		for(PTableColumnInfo<T> column : columns.values())
		{
			description = column.getDescription();

			if(!description.isSortable())
				continue;

			menuBuilder.addMenuItem(new RadioDescriptionMenuItem<T>(obj, column.getDescription(), null, null,
			                getColumnStateSelectionProvider(column), getSortColumnMenuListener(column)));
		}

		return menuBuilder;
	}

	private Listener setSorterOffListener = new Listener()
	{
		@Override
		public void handleEvent(Event event)
		{
			setSorterOff();
		}
	};

	private PropertyProvider<Boolean> isSorterOnProvider = new PropertyProvider<Boolean>()
	{
		@Override
		public Boolean getProperty()
		{
			return isSorterOn();
		}
	};

	private PropertyProvider<Boolean> getColumnStateSelectionProvider(final PTableColumnInfo<T> column)
	{
		Assert.isNotNull(column);

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

	private Listener getSortColumnMenuListener(final PTableColumnInfo<T> column)
	{
		Assert.isNotNull(column);

		Listener listener = new Listener()
		{
			@Override
			@SuppressWarnings("unchecked")
			public void handleEvent(Event event)
			{
				Assert.isNotNull(widget);

				PTableSorter<T> sorter = (PTableSorter<T>)widget.getSorter();
				column.setSorterDirection(isNotNull(sorter) ? sorter.getDirection() : DEFAULT_SORT_DIRECTION);
			}
		};

		return listener;
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		((TableViewer)widget).cancelEditing();

		for(PTableColumnInfo<T> column : columns.values())
			column.setLocale(locale);

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
			// setControlMenu(menuManager.createMenu(1));
			setControlMenu(createHeaderMenu());
	}

	private String[] getVisibleColumnNames()
	{
		List<String> names = new ArrayList<String>();

		for(PTableColumnInfo<T> viewerColumn : columns.values())
			if(!viewerColumn.isHidden())
				names.add(viewerColumn.getName());

		return names.toArray(new String[names.size()]);
	}

	private String[] getObjectVisibleFieldsString(T obj)
	{
		List<String> names = new ArrayList<String>();

		for(PTableColumnInfo<T> viewerColumn : columns.values())
			if(!viewerColumn.isHidden())
				names.add(getText(viewerColumn.getColumnString(obj), ""));

		return names.toArray(new String[names.size()]);
	}

}
