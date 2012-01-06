package cop.swt.widgets.viewers.table.interfaces;

import java.lang.reflect.AccessibleObject;

import cop.swt.widgets.viewers.table.columns.ColumnContext;
import cop.swt.widgets.viewers.table.columns.settings.ColumnSettings;

public interface PTableColumnProvider
{
	<T> ColumnSettings<T> createColumn(AccessibleObject obj, ColumnContext context);
}
