package cop.swt.widgets.viewers.table.interfaces;

import java.util.List;

import cop.swt.widgets.viewers.table.descriptions.ColumnDescription;

public interface TableColumnListener<T>
{
	void columnResized(ColumnDescription<T> resizedColumn, List<ColumnDescription<T>> columns);

	void columnMoved(ColumnDescription<T> movedColumn, List<ColumnDescription<T>> columns);
}
