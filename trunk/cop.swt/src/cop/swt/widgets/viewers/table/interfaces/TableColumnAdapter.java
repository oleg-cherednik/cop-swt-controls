package cop.swt.widgets.viewers.table.interfaces;

import cop.swt.widgets.viewers.table.TableColumnProperty;

public abstract class TableColumnAdapter implements TableColumnListener
{
	@Override
	public void columnMoved(TableColumnProperty resizedColumn, TableColumnProperty[] columns)
	{}

	@Override
	public void columnResized(TableColumnProperty movedColumn, TableColumnProperty[] columns)
	{}
}
