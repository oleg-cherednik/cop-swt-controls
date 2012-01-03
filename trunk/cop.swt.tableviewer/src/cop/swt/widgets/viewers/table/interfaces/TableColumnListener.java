package cop.swt.widgets.viewers.table.interfaces;

import java.util.EventListener;

import cop.swt.widgets.viewers.table.PTableViewer;
import cop.swt.widgets.viewers.table.descriptions.ColumnDescription;

public interface TableColumnListener<T> extends EventListener
{
	void columnResized(PTableViewer<T> viewer, ColumnDescription<T> column);

	void columnMoved(PTableViewer<T> viewer, ColumnDescription<T> column);
}
