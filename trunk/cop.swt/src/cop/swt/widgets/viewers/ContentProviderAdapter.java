/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import cop.swt.widgets.viewers.ibm._IStructuredContentProvider;

/**
 * Adapter class between {@link IStructuredContentProvider} that is used by SWT and {@link _IStructuredContentProvider}
 * that is template version of the the {@link IStructuredContentProvider}.
 * 
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 16.08.2010
 */
public final class ContentProviderAdapter<T> implements IStructuredContentProvider
{
	private final _IStructuredContentProvider<T> contentProvider;

	/**
	 * Creates new adapter instance. If
	 * 
	 * @param contentProvider content provider with template
	 */
	public ContentProviderAdapter(_IStructuredContentProvider<T> contentProvider)
	{
		if(contentProvider == null)
			throw new NullPointerException("Content provider can't be null");

		this.contentProvider = contentProvider;
	}

	/**
	 * @see IStructuredContentProvider#getElements(Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement)
	{
		return contentProvider.getElements((Collection<T>)inputElement).toArray();
	}

	/**
	 * @see IStructuredContentProvider#dispose()
	 */
	@Override
	public void dispose()
	{
		contentProvider.dispose();
	}

	/**
	 * @see IStructuredContentProvider#inputChanged(Viewer, Object, Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{
		contentProvider.inputChanged(viewer, (Collection<T>)oldInput, (Collection<T>)newInput);
	}
}
