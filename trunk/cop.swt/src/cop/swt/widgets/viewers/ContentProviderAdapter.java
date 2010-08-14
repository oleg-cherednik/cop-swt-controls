package cop.swt.widgets.viewers;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import cop.swt.widgets.viewers.ibm._IStructuredContentProvider;

public final class ContentProviderAdapter<T> implements IStructuredContentProvider
{
	private final _IStructuredContentProvider<T> contentProvider;

	public ContentProviderAdapter(_IStructuredContentProvider<T> contentProvider)
	{
		Assert.isNotNull(contentProvider);

		this.contentProvider = contentProvider;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement)
	{
		Assert.isNotNull(contentProvider);

		return contentProvider.getElements((Collection<T>)inputElement).toArray();
	}

	@Override
	public void dispose()
	{}

	@Override
	@SuppressWarnings("unchecked")
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{
		Assert.isNotNull(contentProvider);

		contentProvider.inputChanged(viewer, (Collection<T>)oldInput, (Collection<T>)newInput);
	}
}
