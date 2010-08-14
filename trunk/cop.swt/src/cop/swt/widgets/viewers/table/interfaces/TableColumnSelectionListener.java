package cop.swt.widgets.viewers.table.interfaces;

import org.eclipse.swt.widgets.Table;

import cop.swt.widgets.viewers.table.TableColumnProperty;

public interface TableColumnSelectionListener
{
	void columnSelected(Table table, TableColumnProperty column);
}
