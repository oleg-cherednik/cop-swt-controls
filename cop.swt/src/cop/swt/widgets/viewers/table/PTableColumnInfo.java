package cop.swt.widgets.viewers.table;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isNotEmpty;
import static cop.swt.widgets.annotations.services.i18nService.getTranslation;
import static cop.swt.widgets.enums.SortDirectionEnum.SORT_OFF;
import static cop.swt.widgets.enums.SortDirectionEnum.parseSwtDirection;
import static cop.swt.widgets.viewers.table.AbstractViewerSorter.DEFAULT_SORT_DIRECTION;
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

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;
import cop.swt.widgets.enums.SortDirectionEnum;
import cop.swt.widgets.interfaces.Refreshable;
import cop.swt.widgets.localization.interfaces.LocaleSupport;
import cop.swt.widgets.tmp.ActionTO;
import cop.swt.widgets.viewers.interfaces.IModifyListener;
import cop.swt.widgets.viewers.interfaces.IModifyProvider;
import cop.swt.widgets.viewers.interfaces.ModifyListenerSupport;
import cop.swt.widgets.viewers.interfaces.Packable;
import cop.swt.widgets.viewers.table.descriptions.ColumnDescription;
import cop.swt.widgets.viewers.table.interfaces.TableColumnListener;
import cop.swt.widgets.viewers.table.interfaces.TableColumnSelectionListener;

public class PTableColumnInfo<T> implements LocaleSupport, ModifyListenerSupport<T>, Refreshable, Listener
{
	private PTableSorter<T> sorter;
	private ColumnEditingSupport<T> editor;
	private ColumnDescription<T> description;
	private TableViewerColumn columnViewer;
	private final TableViewer tableViewer;
	private final TableColumnProperty property;

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
		Assert.isNotNull(description);

		this.obj = obj;
		this.tableViewer = tableViewer;
		this.description = description;
		this.columnViewer = description.createTableViewerColumn(tableViewer);
		this.editor = new ColumnEditingSupport<T>(tableViewer, description);
		this.sorter = new PTableSorter<T>(description);
		this.property = new TableColumnProperty(this);

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

	public void setSorterDirection(SortDirectionEnum direction)
	{
		Assert.isNotNull(direction);
		Assert.isNotNull(sorter);
		Assert.isNotNull(tableViewer);
		Assert.isNotNull(columnViewer);

		if(sorter.getDirection() == direction)
			return;

		if(direction == SORT_OFF)
		{
			tableViewer.setSorter(null);
			tableViewer.getTable().setSortColumn(null);
		}
		else
		{
			tableViewer.setSorter(sorter);
			tableViewer.getTable().setSortColumn(columnViewer.getColumn());
		}

		sorter.setDirection(direction);
		tableViewer.getTable().setSortDirection(direction.getSwtDirection());

		refresh();
	}

	public boolean isSorterOn()
	{
		Assert.isNotNull(tableViewer);
		Assert.isNotNull(columnViewer);

		TableColumn column = tableViewer.getTable().getSortColumn();

		return isNotNull(column) && column == columnViewer.getColumn();
	}

	public boolean isSortable()
	{
		Assert.isNotNull(description);

		return description.isSortable();
	}
	
	public TableColumnProperty getProperty()
	{
		return property;
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
		if(itemName != null && event.widget == itemName)
			handleMenuItemEvent(event);
		
//		if(event.item != null) {
			description.handleEvent(event, tableViewer, columnViewer);
//			ActionTO data = (ActionTO)(event.item.getData());
//			tableViewer.update(data, null);
//		}
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
		if(event.type == Selection)
			setHidden(!itemName.getSelection());
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
		Assert.isNotNull(tableViewer);

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
}
