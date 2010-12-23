package cop.swt.widgets.viewers.interfaces;

import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;

/**
 * A viewer filter is used by a structured viewer to extract a subset of elements provided by its content provider.
 * <p>
 * Subclasses must implement the <code>select</code> method and may implement the <code>isFilterProperty</code> method.
 * </p>
 * 
 * @see IStructuredContentProvider
 * @see StructuredViewer
 */
public interface IViewerFilter<T>
{
	/**
	 * Filters the given elements for the given viewer. The input array is not modified.
	 * <p>
	 * The default implementation of this method calls {@link #filter(Viewer, Object, Object[])} with the parent from
	 * the path. Subclasses may override
	 * </p>
	 * 
	 * @param viewer the viewer
	 * @param items item list
	 * @return the filtered elements
	 */
	public List<T> filter(Collection<T> items);

	/**
	 * Returns whether the given element makes it through this filter.
	 * 
	 * @param viewer the viewer
	 * @param parentElement the parent element
	 * @param element the element
	 * @return <code>true</code> if element is included in the filtered set, and <code>false</code> if excluded
	 */
	boolean select(T item);
}
