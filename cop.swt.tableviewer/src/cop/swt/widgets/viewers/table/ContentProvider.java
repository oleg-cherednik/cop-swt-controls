package cop.swt.widgets.viewers.table;

import static cop.extensions.CollectionExt.isNotEmpty;

import java.util.Collection;

import org.eclipse.jface.viewers.Viewer;

import cop.swt.widgets.viewers.ibm._IStructuredContentProvider;

public class ContentProvider<T> implements _IStructuredContentProvider<T>
{
	@Override
	public Collection<T> getElements(Collection<T> inputElement)
	{
		int num = isNotEmpty(inputElement) ? inputElement.size() : -1;

		System.out.println("ContentProvider.getElements(" + num + ")");

		return inputElement;
	}

	@Override
	public void dispose()
	{}

	@Override
	public void inputChanged(Viewer viewer, Collection<T> oldItems, Collection<T> newItems)
	{
		int oldNum = isNotEmpty(oldItems) ? oldItems.size() : -1;
		int newNum = isNotEmpty(newItems) ? newItems.size() : -1;

		System.out.println("ContentProvider.inputChanged(old: " + oldNum + ", new: " + newNum + ")");
	}
}
