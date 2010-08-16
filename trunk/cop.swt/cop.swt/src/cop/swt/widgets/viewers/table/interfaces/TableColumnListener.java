package cop.swt.widgets.viewers.table.interfaces;

import cop.swt.widgets.viewers.table.TableColumnProperty;

public interface TableColumnListener
{
	void columnResized(TableColumnProperty resizedColumn, TableColumnProperty[] columns);

	void columnMoved(TableColumnProperty movedColumn, TableColumnProperty[] columns);
}
