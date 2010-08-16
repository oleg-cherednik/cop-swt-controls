package cop.swt.widgets.viewers.table.interfaces;

import java.util.Collection;

import org.eclipse.jface.viewers.Viewer;

public interface DeleteListener<T>
{
	void itemsDeleted(Viewer viewer, Collection<T> items);
}
