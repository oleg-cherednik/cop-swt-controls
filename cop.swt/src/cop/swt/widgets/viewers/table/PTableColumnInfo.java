package cop.swt.widgets.viewers.table;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isNotEmpty;
import static cop.swt.widgets.annotations.services.i18nService.getTranslation;
import static cop.swt.widgets.enums.SortDirectionEnum.SORT_OFF;
import static cop.swt.widgets.enums.SortDirectionEnum.parseSwtDirection;
import static cop.swt.widgets.viewers.table.PViewerSorter.DEFAULT_SORT_DIRECTION;
import static org.eclipse.swt.SWT.CHECK;
import static org.eclipse.swt.SWT.DEFAULT;
import static org.eclipse.swt.SWT.DOWN;
import static org.eclipse.swt.SWT.Move;
import static org.eclipse.swt.SWT.Resize;
import static org.eclipse.swt.SWT.Selection;
import static org.eclipse.swt.SWT.UP;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import cop.common.extensions.StringExtension;
import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;
import cop.swt.widgets.enums.SortDirectionEnum;
import cop.swt.widgets.interfaces.Refreshable;
import cop.swt.widgets.localization.interfaces.LocaleSupport;
import cop.swt.widgets.menu.MenuManager;
import cop.swt.widgets.menu.enums.MenuItemEnum;
import cop.swt.widgets.viewers.interfaces.IModifyListener;
import cop.swt.widgets.viewers.interfaces.IModifyProvider;
import cop.swt.widgets.viewers.interfaces.ModifyListenerSupport;
import cop.swt.widgets.viewers.interfaces.Packable;
import cop.swt.widgets.viewers.table.descriptions.ColumnDescription;
import cop.swt.widgets.viewers.table.interfaces.TableColumnListener;
import cop.swt.widgets.viewers.table.interfaces.TableColumnSelectionListener;

public class PTableColumnInfo<T> implements LocaleSupport, ModifyListenerSupport<T>, Refreshable, Listener
{
	private PViewerSorter<T> sorter;
	private ColumnEditingSupport<T> editor;
	private ColumnDescription<T> description;
	private TableViewerColumn columnViewer;
	private final TableViewer tableViewer;

	private Set<Packable> packableListeners = new HashSet<Packable>();

	private float hiddenWidth;
	private boolean hiddenResizeable;
	private boolean hidden;
	// private boolean autoColumnWidth = true;

	private final T obj;

	private TableColumnListener<T> columnListener;
	private TableColumnSelectionListener selectionListener;

	private MenuItem itemName;

	public PTableColumnInfo(T obj, final TableViewer tableViewer, ColumnDescription<T> description)
	{
		if(description == null)
			throw new NullPointerException("description == null");

		this.obj = obj;
		this.tableViewer = tableViewer;
		this.description = description;
		this.columnViewer = description.createTableViewerColumn(tableViewer);
		this.editor = new ColumnEditingSupport<T>(tableViewer, description);
		this.sorter = new PViewerSorter<T>(tableViewer, description);

		this.columnViewer.setEditingSupport(editor);

		if(description.isHideable() && !description.isVisible())
			setHidden(true);

		addListeners();
	}

	private void addListeners()
	{
		columnViewer.getColumn().addListener(Resize, this);
		columnViewer.getColumn().addListener(Move, this);
		columnViewer.getColumn().addListener(Selection, this);
	}

	public void setTableColumnSelectionListener(TableColumnSelectionListener listener)
	{
		this.selectionListener = listener;
	}

	public void setTableColumnListener(TableColumnListener<T> listener)
	{
		this.columnListener = listener;
	}

	// Assert.isNotNull(widget);
	//
	// PTableSorter<T> sorter = (PTableSorter<T>)widget.getSorter();
	// column.setSorterDirection(isNotNull(sorter) ? sorter.getDirection() : DEFAULT_SORT_DIRECTION);

	public void setSorterDirection(SortDirectionEnum direction)
	{
		sorter.setDirection(direction);
		tableViewer.getTable().setSortDirection(direction.getSwtDirection());
		tableViewer.getTable().setSortColumn((direction == SORT_OFF) ? null : columnViewer.getColumn());

		if(direction != SORT_OFF)
			refresh();
	}

	public boolean isSorterOn()
	{
		TableColumn column = tableViewer.getTable().getSortColumn();

		return isNotNull(column) && column == columnViewer.getColumn();
	}

	public boolean isSortable()
	{
		return description.isSortable();
	}

	TableViewerColumn getViewer()
	{
		return columnViewer;
	}

	ColumnEditingSupport<T> getEditor()
	{
		return editor;
	}

	public ColumnDescription<T> getDescription()
	{
		return description;
	}

	public boolean isHidden()
	{
		return hidden;
	}

	public void setHidden(boolean hidden)
	{
		if(this.hidden == hidden)
			return;

		int tableWidth = tableViewer.getTable().getBounds().width;

		this.hidden = hidden;

		if(hidden)
		{
			hiddenWidth = (tableWidth != 0) ? getRelativeWidth(columnViewer.getColumn().getWidth(), tableWidth) : 0;
			hiddenResizeable = columnViewer.getColumn().getResizable();

			columnViewer.getColumn().setWidth(0);
			columnViewer.getColumn().setResizable(false);
		}
		else
		{
			if(hiddenWidth == 0)
				pack();
			else
			{
				columnViewer.getColumn().setWidth(getWidthByRelative(hiddenWidth, tableWidth));
				columnViewer.getColumn().setResizable(hiddenResizeable);
			}
		}
	}

	public void setMenu(Menu parent)
	{
		if(!description.isHideable())
			return;

		if(itemName != null)
			itemName.dispose();

		itemName = new MenuItem(parent, CHECK);

		itemName.setData(MenuManager.MENU_ITEM_PATH, MenuItemEnum.MI_HIDE.name() + MenuManager.MENU_ITEM_PATH_SEPARATOR
		                + description.getKey() + MenuManager.MENU_ITEM_PATH_SEPARATOR);
		itemName.setText(columnViewer.getColumn().getText());
		itemName.setSelection(columnViewer.getColumn().getResizable());
		itemName.addListener(Selection, this);
	}

	public float getRelativeWidth()
	{
		int width = description.getWidth();

		if(width > 0)
			return width;

		return getRelativeWidth(getWidth(), tableViewer.getTable().computeSize(DEFAULT, DEFAULT).x);
	}

	public void setRelativeWidth()
	{
		if(hidden)
			return;

		int width = description.getWidth();

		if(width < 1)
			return;

		Table table = tableViewer.getTable();
		int tableWidth = table.getBounds().width;

		if(tableWidth == 0)
			return;

		tableWidth -= table.getBorderWidth() + 5;
		setWidth(getWidthByRelative(getRelativeWidth(), tableWidth));
	}

	public int getWidth()
	{
		return columnViewer.getColumn().getWidth();
	}

	public void setWidth(int width)
	{
		if(!hidden)
			columnViewer.getColumn().setWidth(width);
	}

	public String getName()
	{
		return description.getName();
	}

	public void pack()
	{
		if(hidden)
			return;

		int descWidth = description.getWidth();

		tableViewer.getTable().setRedraw(false);

		if(descWidth <= 0)
			columnViewer.getColumn().pack();
		else
		{
			Table table = tableViewer.getTable();
			int tableWidth = table.getBounds().width;

			columnViewer.getColumn().setWidth(getWidthByRelative(descWidth, tableWidth));
		}

		tableViewer.getTable().setRedraw(true);
	}

	public void setReadonly(boolean readonly)
	{
		editor.setReadonly(readonly);
	}

	public void setReadonlyProvider(IModifyProvider<T> provider)
	{
		editor.setReadonlyProvider(provider);
	}

	public static float getRelativeWidth(int columnWidth, int totalWidth)
	{
		return ((float)columnWidth * 100) / totalWidth;
	}

	public static int getWidthByRelative(float relativeWidth, int totalWidth)
	{
		return (int)((totalWidth * relativeWidth) / 100);
	}

	public String getColumnString(T obj)
	{
		if(hidden || isNull(obj))
			return "";

		try
		{
			Object value = description.getTextValue(obj);

			return isNotNull(value) ? value.toString() : "";
		}
		catch(Exception e)
		{
			return "";
		}
	}

	/*
	 * packableListeners
	 */

	public void addPackableListener(Packable listener)
	{
		if(isNotNull(listener))
			packableListeners.add(listener);
	}

	public void removePackableListener(Packable listener)
	{
		if(isNotNull(listener))
			packableListeners.remove(listener);
	}

	private void notifyPackableListener()
	{
		for(Packable listener : packableListeners)
			listener.pack();
	}

	public void setEditorEnabled(boolean enabled)
	{
		editor.setEnabled(enabled);
	}

	/*
	 * ControlListener
	 */

	public void addControlListener(ControlListener listener)
	{
		columnViewer.getColumn().addControlListener(listener);
	}

	public void removeControlListener(ControlListener listener)
	{
		columnViewer.getColumn().removeControlListener(listener);
	}

	/*
	 * ModifyListenerSupport
	 */

	@Override
	public void addModifyListener(IModifyListener<T> listener)
	{
		editor.addModifyListener(listener);
	}

	@Override
	public void removeModifyListener(IModifyListener<T> listener)
	{
		editor.removeModifyListener(listener);
	}

	/*
	 * Listener
	 */

	@Override
	public void handleEvent(Event event)
	{
		if(event.widget == columnViewer.getColumn())
			handleColumnEvent(event);
		else if(isMenuItemEvent(event))
			handleMenuItemEvent(event);

		// if(event.item != null) {
		description.handleEvent(event, tableViewer, columnViewer);
		// ActionTO data = (ActionTO)(event.item.getData());
		// tableViewer.update(data, null);
		// }
	}

	private boolean isMenuItemEvent(Event event)
	{
		if(!(event.widget instanceof MenuItem))
			return false;

		String path = (String)event.widget.getData(MenuManager.MENU_ITEM_PATH);

		if(StringExtension.isEmpty(path))
			return false;

		String[] parts = (path != null) ? path.split(MenuManager.MENU_ITEM_PATH_SEPARATOR) : null;
		int i = parts.length - 1;
		String key = description.getKey();

		return key.equals(parts[i]);
	}

	private void handleColumnEvent(Event event)
	{
		if(event.type == Resize)
			onResized(event);
		else if(event.type == Move)
			onMove(event);
		else if(event.type == Selection)
			onColumnSelection(event);
	}

	private void handleMenuItemEvent(Event event)
	{
		String path = (String)event.widget.getData(MenuManager.MENU_ITEM_PATH);
		String[] parts = path.split(MenuManager.MENU_ITEM_PATH_SEPARATOR);

		if(parts.length < 2)
			return;

		int i = parts.length - 1;

		if(MenuItemEnum.MI_SORT.name().equals(parts[i - 1]))
		{
			if(description.isSortable())
				onColumnSorter(event);
		}
		else if(MenuItemEnum.MI_HIDE.name().equals(parts[i - 1]))
			setHidden(!itemName.getSelection());
		int a = 0;
		a++;
	}

	/*
	 * listeners
	 */

	private void onResized(Event event)
	{
		if(columnListener != null)
			columnListener.columnResized(description, null);
	}

	private void onMove(Event event)
	{
		if(columnListener != null)
			columnListener.columnMoved(description, null);
	}

	private void onColumnSelection(Event event)
	{
		if(selectionListener != null)
			onColumnSelect(event);
		if(description.isSortable())
			onColumnSorter(event);
	}

	private void onColumnSelect(Event event)
	{
		TableColumnProperty columnProperty = new TableColumnProperty(description.getKey(), columnViewer.getColumn());
		selectionListener.columnSelected(tableViewer.getTable(), columnProperty);
	}

	private void onColumnSorter(Event event)
	{
		try
		{
			TableColumn column = columnViewer.getColumn();
			Table table = tableViewer.getTable();
			int dir = table.getSortDirection();
			int swt;

			if(table.getSortColumn() == column)
				swt = (dir == UP) ? DOWN : UP;
			else
				swt = DEFAULT_SORT_DIRECTION.getSwtDirection();

			setSorterDirection(parseSwtDirection(swt));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/*
	 * Refreshable
	 */

	@Override
	public void refresh()
	{
		tableViewer.refresh();
		notifyPackableListener();
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		description.setLocale(locale);
		editor.setLocale(locale);

		try
		{
			String str = getTranslation(obj, description.getKey(), locale);

			columnViewer.getColumn().setText(isNotEmpty(str) ? str : description.getName());
		}
		catch(AnnotationDeclarationException e)
		{
			e.printStackTrace();
			columnViewer.getColumn().setText(description.getName());
		}
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		return description.getKey();
	}
}
