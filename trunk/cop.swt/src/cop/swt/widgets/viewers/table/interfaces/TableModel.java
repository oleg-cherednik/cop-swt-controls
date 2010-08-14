package cop.swt.widgets.viewers.table.interfaces;

import java.util.Collection;

import cop.swt.widgets.viewers.ibm._IStructuredContentProvider;

public interface TableModel<T> extends _IStructuredContentProvider<T>
{
	boolean remove(T item);

	boolean removeAll(Collection<T> items);
}
