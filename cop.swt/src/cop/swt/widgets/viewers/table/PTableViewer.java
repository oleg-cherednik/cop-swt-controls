/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table;

import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.ArrayExtension.isNotEmpty;
import static cop.common.extensions.BitExtension.clearBits;
import static cop.common.extensions.BitExtension.isBitSet;
import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isNotEmpty;
import static cop.swt.widgets.enums.SortDirectionEnum.SORT_OFF;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MI_OFF;
import static cop.swt.widgets.viewers.table.AbstractViewerSorter.DEFAULT_SORT_DIRECTION;
import static org.eclipse.swt.SWT.FULL_SELECTION;
import static org.eclipse.swt.SWT.READ_ONLY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import cop.swt.preferences.EmployeeListPreferencePage;
import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;
import cop.swt.widgets.annotations.exceptions.AnnotationMissingException;
import cop.swt.widgets.annotations.services.ColumnService;
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
import cop.swt.widgets.viewers.table.descriptions.IColumnDescription;
import cop.swt.widgets.viewers.table.interfaces.TableColumnListener;

/*
 * ITableColorProvider
 * MarathonLiabilitiesControl
 */
public final class PTableViewer<T> extends PViewer<T> implements Packable
{
	private PTableColumnInfo<T>[] columns;
	private boolean autoColumnWidth = true;
	private boolean mouseEnter;
	private Set<TableColumnListener> tableColumnListeners = new HashSet<TableColumnListener>();
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
		createLabelProvider();
		createFilter();

		addListeners(obj);
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
		TableViewer viewer = (TableViewer)widget;

		viewer.cancelEditing();

		for(PTableColumnInfo<T> column : columns)
			column.setEditorEnabled(!enabled);

		if(enabled)
			viewer.getTable().addMouseListener(setEditOnDoubleClick);
		else
			viewer.getTable().removeMouseListener(setEditOnDoubleClick);
	}

	public void setReadonlyProvider(IModifyProvider<T> provider)
	{
		if(isNotEmpty(columns))
			for(PTableColumnInfo<T> viewerColumn : columns)
				viewerColumn.setReadonlyProvider(provider);
	}

	@SuppressWarnings("unchecked")
	private void createColumns() throws AnnotationDeclarationException, AnnotationMissingException
	{
		Assert.isNotNull(obj);

		List<? extends IColumnDescription> descriptions = ColumnService.getDescriptions(obj.getClass(),
		                getImageProvider());

		if(isEmpty(descriptions))
			throw new AnnotationMissingException("No column found. Use @Column annotation.");

		columns = new PTableColumnInfo[descriptions.size()];

		PTableColumnInfo<T> column;
		TableViewer viewer = (TableViewer)widget;

		for(int i = 0, size = columns.length; i < size; i++)
		{
			column = new PTableColumnInfo(obj, viewer, descriptions.get(i));

			column.setTableColumnListener(notifyTableColumnListener);
			column.addPackableListener(doPack);

			columns[i] = column;
		}

		pack();
	}

	private Menu createHeaderMenu()
	{
		try
		{
			Menu menu = new Menu(parent);

			for(PTableColumnInfo<T> viewerColumn : columns)
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
		super.addListeners();

		if(isEmpty(columns) || isNull(obj))
			return;

		Table table = (Table)widget.getControl();

		table.addControlListener(autoColumnWidthListener);
		table.addMouseTrackListener(onMouseTrack);
		table.addMouseMoveListener(onMouseMove);
		table.addMouseListener(setEditOnDoubleClick);
		table.addDisposeListener(preDispose);
	}

	private void _pack()
	{
		Runnable task = new Runnable()
		{
			@Override
			public void run()
			{
				Runnable task = new Runnable()
				{
					@Override
					public void run()
					{
						pack();
					}
				};

				widget.getControl().getDisplay().syncExec(task);
			}
		};

		new Thread(task).start();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getSelectedItems()
	{
		Table table = (Table)widget.getControl();
		TableItem[] tableItems = table.getSelection();
		List<T> items = new ArrayList<T>();

		if(isEmpty(tableItems))
			return items;

		for(TableItem tableItem : tableItems)
			items.add((T)tableItem.getData());

		return items;
	}

	@Override
	public int getItemCount()
	{
		return ((Table)widget.getControl()).getItemCount();
	}

	/*
	 * tableColumnListener
	 */

	public void addTableColumnListener(TableColumnListener listener)
	{
		tableColumnListeners.add(listener);
	}

	public void removeTableColumnListener(TableColumnListener listener)
	{
		tableColumnListeners.remove(listener);
	}

	private void notifyTableColumnResizedListeners(TableColumnProperty resizedColumn, TableColumnProperty[] columns)
	{
		for(TableColumnListener listener : tableColumnListeners)
			listener.columnResized(resizedColumn, columns);
	}

	private void notifyTableColumnMovedListeners(TableColumnProperty movedColumn, TableColumnProperty[] columns)
	{
		for(TableColumnListener listener : tableColumnListeners)
			listener.columnMoved(movedColumn, columns);
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

		for(PTableColumnInfo<T> column : columns)
			column.addModifyListener(listener);
	}

	@Override
	public void removeModifyListener(IModifyListener<T> listener)
	{
		if(isNull(listener))
			return;

		super.removeModifyListener(listener);

		for(PTableColumnInfo<T> column : columns)
			column.removeModifyListener(listener);
	}

	/*
	 * IPack
	 */

	@Override
	public void pack()
	{
		if(widget.getControl().isDisposed())
			return;

		if(isNotEmpty(columns))
			for(PTableColumnInfo<T> column : columns)
				column.pack();
	}

	/*
	 * PViewer
	 */

	@Override
	protected boolean isSortable()
	{
		if(isEmpty(columns))
			return false;

		for(PTableColumnInfo<T> column : columns)
			if(column.isSortable())
				return true;

		return false;
	}

	@Override
	protected List<String[]> toStringArrayList(List<T> items)
	{
		if(isEmpty(items))
			return new ArrayList<String[]>(0);

		List<String[]> data = new ArrayList<String[]>(items.size() + 1);

		data.add(getVisibleColumnNames());

		for(T item : items)
			data.add(getObjectVisibleFieldsString(item));

		return data;
	}

	@Override
	public void selectAll()
	{
		if(getSelectedItems().size() == getItemCount())
			return;

		((Table)widget.getControl()).selectAll();
		super.selectAll();
	}

	@Override
	public void deselectAll()
	{
		if(getSelectedItems().size() == 0)
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

		for(PTableColumnInfo<T> column : columns)
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

		if(isEmpty(columns))
			return;

		for(PTableColumnInfo<T> viewerColumn : columns)
			viewerColumn.setReadonly(readonly);
	}

	@Override
	protected void postConstruct()
	{
		Table table = ((TableViewer)widget).getTable();

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		super.postConstruct();
	}

	@Override
	public void refresh()
	{
		super.refresh();
		_pack();
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
	 * Listeners
	 */

	private Packable doPack = new Packable()
	{
		@Override
		public void pack()
		{
			PTableViewer.this.pack();
		}
	};

	private MouseListener setEditOnDoubleClick = new MouseAdapter()
	{
		@Override
		public void mouseDoubleClick(MouseEvent e)
		{
			if(e.button != 1)
				return;

			TableViewer viewer = (TableViewer)widget;
			Table table = viewer.getTable();
			TableItem[] items = table.getSelection();

			if(isEmpty(items) || items.length != 1)
				return;

			int column = getSelectedColumn(items[0], e.x, e.y);

			if(column < 0)
				return;

			columns[column].setEditorEnabled(true);
			viewer.editElement(items[0].getData(), column);
			columns[column].setEditorEnabled(false);
		}

		private int getSelectedColumn(TableItem item, int x, int y)
		{
			Assert.isNotNull(item);

			for(int i = 0, size = columns.length; i < size; i++)
				if(item.getBounds(i).contains(x, y))
					return i;

			return -1;
		}
	};

	private DisposeListener preDispose = new DisposeListener()
	{
		@Override
		public void widgetDisposed(DisposeEvent e)
		{
			dispose();
		}
	};

	private ControlListener autoColumnWidthListener = new ControlAdapter()
	{
		@Override
		public void controlResized(ControlEvent e)
		{
			if(!autoColumnWidth)
				return;

			Table table = ((TableViewer)widget).getTable();
			int tableWidth = table.getBounds().width;

			if(tableWidth == 0)
				return;

			table.setRedraw(false);

			for(PTableColumnInfo<T> viewerColumn : columns)
				viewerColumn.setRelativeWidth();

			table.setRedraw(true);
		}
	};

	private MouseTrackListener onMouseTrack = new MouseTrackAdapter()
	{
		@Override
		public void mouseExit(MouseEvent e)
		{
			mouseEnter = false;
		}
	};

	private MouseMoveListener onMouseMove = new MouseMoveListener()
	{
		@Override
		public void mouseMove(MouseEvent e)
		{
			mouseEnter = true;
		}
	};

	private TableColumnListener notifyTableColumnListener = new TableColumnListener()
	{
		@Override
		public void columnMoved(TableColumnProperty movedColumn, TableColumnProperty[] columns)
		{
			if(!autoColumnWidth)
				notifyTableColumnMovedListeners(movedColumn, getOrderTableColumns());
		}

		@Override
		public void columnResized(TableColumnProperty resizedColumn, TableColumnProperty[] columns)
		{
			if(!autoColumnWidth)
				notifyTableColumnResizedListeners(resizedColumn, getOrderTableColumns());
		}

		private TableColumnProperty[] getOrderTableColumns()
		{
			List<TableColumnProperty> res = new ArrayList<TableColumnProperty>();
			Table table = ((TableViewer)widget).getTable();

			for(int pos : table.getColumnOrder())
				res.add(new TableColumnProperty(columns[pos]));

			return res.toArray(new TableColumnProperty[0]);
		}
	};

	@Override
	protected MenuBuilder createSortMenuBuilder()
	{
		MenuBuilder menuBuilder = new MenuBuilder(getImageProvider());
		IColumnDescription<T> description;

		menuBuilder.addMenuItem(new PushMenuItem(MI_OFF, null, isSorterOnProvider, setSorterOffListener));
		menuBuilder.addMenuItem(new SeparatorMenuItem());

		for(PTableColumnInfo<T> column : columns)
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

		for(PTableColumnInfo<T> column : columns)
			column.setLocale(locale);

		refresh();

		if(autoColumnWidth)
			pack();
	}

	/*
	 * Listeners
	 */

	private String[] getVisibleColumnNames()
	{
		List<String> names = new ArrayList<String>();

		for(PTableColumnInfo<T> viewerColumn : columns)
			if(!viewerColumn.isHidden())
				names.add(viewerColumn.getName());

		return names.toArray(new String[0]);
	}

	private String[] getObjectVisibleFieldsString(T obj)
	{
		List<String> names = new ArrayList<String>();

		for(PTableColumnInfo<T> viewerColumn : columns)
		{
			if(viewerColumn.isHidden())
				continue;

			String value = viewerColumn.getColumnString(obj);

			names.add(isNotEmpty(value) ? value : "");
		}

		return names.toArray(new String[0]);
	}

	@Override
	protected MenuDetectListener getContextMenu()
	{
		MenuDetectListener listener = new MenuDetectListener()
		{
			@Override
			public void menuDetected(MenuDetectEvent e)
			{
				if(isNull(menuManager))
					return;

				if(mouseEnter)
					setControlMenu(menuManager.createMenu(0));
				else
					// setControlMenu(menuManager.createMenu(1));
					setControlMenu(createHeaderMenu());
			}
		};

		return listener;
	};
}
