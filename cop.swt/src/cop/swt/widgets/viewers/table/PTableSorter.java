package cop.swt.widgets.viewers.table;

import cop.swt.widgets.viewers.table.descriptions.IColumnDescription;

public class PTableSorter<T> extends AbstractViewerSorter<T>
{
	public PTableSorter(IColumnDescription<T> accessibleObject)
	{
		super(accessibleObject);
	}
}
