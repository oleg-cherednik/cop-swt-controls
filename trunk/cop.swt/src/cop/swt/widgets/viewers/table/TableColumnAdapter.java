package cop.swt.widgets.viewers.table;

import java.util.List;

import cop.swt.widgets.viewers.table.descriptions.ColumnDescription;
import cop.swt.widgets.viewers.table.interfaces.TableColumnListener;

public abstract class TableColumnAdapter<T> implements TableColumnListener<T>
{
	@Override
	public void columnMoved(ColumnDescription<T> resizedColumn, List<ColumnDescription<T>> columns)
	{}

	@Override
	public void columnResized(ColumnDescription<T> movedColumn, List<ColumnDescription<T>> columns)
	{}
}
