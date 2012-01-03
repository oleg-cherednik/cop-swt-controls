package cop.swt.widgets.viewers.table.columns;

import static cop.swt.widgets.enums.SortDirectionEnum.SORT_OFF;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;

import cop.swt.widgets.interfaces.Editable;
import cop.swt.widgets.viewers.interfaces.ItemModifyListener;
import cop.swt.widgets.viewers.interfaces.ModifyListenerSupport;
import cop.swt.widgets.viewers.interfaces.PModifyProvider;
import cop.swt.widgets.viewers.interfaces.Packable;
import cop.swt.widgets.viewers.table.interfaces.TableColumnListener;

public final class PTableColumnSet<T> implements Editable, ModifyListenerSupport<T>
{
	private int size = 0;
	private final Map<Integer, PTableColumn<T>> columns = new LinkedHashMap<Integer, PTableColumn<T>>();

	public void add(PTableColumn<T> column)
	{
		if(column != null && !columns.containsKey(column))
			columns.put(size++, column);
	}

	public PTableColumn<T> get(int index)
	{
		return columns.get(index);
	}

	public Collection<PTableColumn<T>> getColumns()
	{
		return Collections.unmodifiableCollection(columns.values());
	}

	public int getSize()
	{
		return size;
	}

	public void setLocale(Locale locale)
	{
		for(PTableColumn<T> column : columns.values())
			column.setLocale(locale);
	}

	public void refresh()
	{
		for(PTableColumn<T> column : columns.values())
			column.refresh();
	}

	public void setTableColumnListener(TableColumnListener<T> listener)
	{
		if(listener != null)
			for(PTableColumn<T> column : columns.values())
				column.setTableColumnListener(listener);
	}

	public void setRelativeWidth()
	{
		for(PTableColumn<T> column : columns.values())
			column.setRelativeWidth();
	}

	public void addPackableListener(Packable listener)
	{
		if(listener != null)
			for(PTableColumn<T> column : columns.values())
				column.addPackableListener(listener);
	}

	public void removePackableListener(Packable listener)
	{
		if(listener != null)
			for(PTableColumn<T> column : columns.values())
				column.removePackableListener(listener);
	}

	public void setModifyProvider(PModifyProvider<T> provider)
	{
		for(PTableColumn<T> column : columns.values())
			column.setModifyProvider(provider);
	}

	public void setEditorEnabled(boolean enabled)
	{
		for(PTableColumn<T> column : columns.values())
			column.setEditorEnabled(!enabled);
	}

	public Menu addMenuItem(Menu parent)
	{
		try
		{
			for(PTableColumn<T> viewerColumn : columns.values())
				viewerColumn.addMenuItem(parent);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public void pack()
	{
		for(PTableColumn<T> column : columns.values())
			column.pack();
	}

	public boolean isHidden()
	{
		for(PTableColumn<T> column : columns.values())
			if(!column.isHidden())
				return false;

		return true;
	}

	public boolean isSortable()
	{
		for(PTableColumn<T> column : columns.values())
			if(column.isSortable())
				return true;

		return false;
	}

	public void setSorterOff()
	{
		for(PTableColumn<T> column : columns.values())
			column.setSorterDirection(SORT_OFF);
	}

	public void handleEvent(Event event)
	{
		PTableColumn<T> column = columns.get(event.index);

		if(column != null)
			column.handleEvent(event);
	}

	/*
	 * Editable
	 */

	@Override
	public void setEditable(boolean editable)
	{
		for(PTableColumn<T> column : columns.values())
			column.setEditable(editable);
	}

	@Override
	public boolean isEditable()
	{
		for(PTableColumn<T> column : columns.values())
			if(column.isEditable())
				return true;

		return false;
	}

	/*
	 * ModifyListenerSupport
	 */

	@Override
	public void addModifyListener(ItemModifyListener<T> listener)
	{
		if(listener != null)
			for(PTableColumn<T> column : columns.values())
				column.addModifyListener(listener);
	}

	@Override
	public void removeModifyListener(ItemModifyListener<T> listener)
	{
		if(listener != null)
			for(PTableColumn<T> column : columns.values())
				column.removeModifyListener(listener);
	}
}
