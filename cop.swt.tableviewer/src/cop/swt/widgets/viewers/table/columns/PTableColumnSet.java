package cop.swt.widgets.viewers.table.columns;

import static cop.swt.widgets.enums.SortDirectionEnum.SORT_OFF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;

import cop.swt.widgets.interfaces.Editable;
import cop.swt.widgets.viewers.interfaces.PModifyProvider;
import cop.swt.widgets.viewers.interfaces.ItemModifyListener;
import cop.swt.widgets.viewers.interfaces.Packable;
import cop.swt.widgets.viewers.table.interfaces.TableColumnListener;

public final class PTableColumnSet<T> implements Editable
{
	private final List<PTableColumn<T>> columns = new ArrayList<PTableColumn<T>>();

	public void add(PTableColumn<T> column)
	{
		columns.add(column);
	}

	public List<PTableColumn<T>> getColumns()
	{
		return Collections.unmodifiableList(columns);
	}

	private String[] getVisibleColumnNames()
	{
		List<String> names = new ArrayList<String>();

		for(PTableColumn<T> viewerColumn : columns)
			if(!viewerColumn.isHidden())
				names.add(viewerColumn.getName());

		return names.toArray(new String[names.size()]);
	}

	public void setLocale(Locale locale)
	{
		for(PTableColumn<T> column : columns)
			column.setLocale(locale);
	}

	public void addModifyListener(ItemModifyListener<T> listener)
	{
		// TODO Auto-generated method stub

	}

	public void removeModifyListener(ItemModifyListener<T> listener)
	{
		// TODO Auto-generated method stub

	}

	public void refresh()
	{
		// TODO Auto-generated method stub

	}

	public void handleEvent(Event event)
	{
		// TODO Auto-generated method stub

	}

	public void setTableColumnListener(TableColumnListener<T> listener)
	{
		// TODO Auto-generated method stub

	}

	public void addPackableListener(Packable listener)
	{
		// TODO Auto-generated method stub

	}

	public void removePackableListener(Packable listener)
	{
		// TODO Auto-generated method stub

	}

	public void setModifyProvider(PModifyProvider<T> provider)
	{
		for(PTableColumn<T> column : columns)
			column.setModifyProvider(provider);
	}

	public void setEditorEnabled(boolean enabled)
	{
		for(PTableColumn<T> column : columns)
			column.setEditorEnabled(!enabled);
	}

	public Menu addMenuItem(Menu parent)
	{
		try
		{
			for(PTableColumn<T> viewerColumn : columns)
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
		for(PTableColumn<T> column : columns)
			column.pack();
	}

	public boolean isHidden()
	{
		for(PTableColumn<T> column : columns)
			if(!column.isHidden())
				return false;

		return true;
	}

	public boolean isSortable()
	{
		for(PTableColumn<T> column : columns)
			if(column.isSortable())
				return true;

		return false;
	}

	public void setSorterOff()
	{
		for(PTableColumn<T> column : columns)
			column.setSorterDirection(SORT_OFF);
	}

	/*
	 * Editable
	 */

	@Override
	public void setEditable(boolean editable)
	{
		for(PTableColumn<T> column : columns)
			column.setEditable(editable);
	}

	@Override
	public boolean isEditable()
	{
		for(PTableColumn<T> column : columns)
			if(column.isEditable())
				return true;

		return false;
	}
}
