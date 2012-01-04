package cop.swt.widgets.viewers.table.interfaces;

import java.util.EventListener;

import cop.swt.widgets.viewers.table.PTableViewer;
import cop.swt.widgets.viewers.table.columns.settings.ColumnSettings;

public interface TableColumnListener<T> extends EventListener
{
	void columnResized(PTableViewer<T> viewer, ColumnSettings<T> column);

	void columnMoved(PTableViewer<T> viewer, ColumnSettings<T> column);
}
