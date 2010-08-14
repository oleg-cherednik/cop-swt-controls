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
	public List<T> filter(Collection<T> items);

	// /**
	// * Creates a new viewer filter.
	// */
	// protected ViewerFilter()
	// {}

	// /**
	// * Filters the given elements for the given viewer. The input array is not modified.
	// * <p>
	// * The default implementation of this method calls <code>select</code> on each element in the array, and returns
	// * only those elements for which <code>select</code> returns <code>true</code>.
	// * </p>
	// *
	// * @param viewer the viewer
	// * @param parent the parent element
	// * @param elements the elements to filter
	// * @return the filtered elements
	// */
	// public Object[] filter(Viewer viewer, T parent, T[] elements)
	// {
	// int size = elements.length;
	// List<T> out = new ArrayList<T>(size);
	// for(int i = 0; i < size; ++i)
	// {
	// T element = elements[i];
	// if(select(viewer, parent, element))
	// {
	// out.add(element);
	// }
	// }
	// return out.toArray();
	// }
	//
	// /**
	// * Filters the given elements for the given viewer. The input array is not modified.
	// * <p>
	// * The default implementation of this method calls {@link #filter(Viewer, Object, Object[])} with the parent from
	// * the path. Subclasses may override
	// * </p>
	// *
	// * @param viewer the viewer
	// * @param parentPath the path of the parent element
	// * @param elements the elements to filter
	// * @return the filtered elements
	// * @since 3.2
	// */
	// @SuppressWarnings("unchecked")
	// public Object[] filter(Viewer viewer, TreePath parentPath, T[] elements)
	// {
	// return filter(viewer, (T)parentPath.getLastSegment(), elements);
	// }
	//
	// /**
	// * Returns whether this viewer filter would be affected by a change to the given property of the given element.
	// * <p>
	// * The default implementation of this method returns <code>false</code>. Subclasses should reimplement.
	// * </p>
	// *
	// * @param element the element
	// * @param property the property
	// * @return <code>true</code> if the filtering would be affected, and <code>false</code> if it would be unaffected
	// */
	// public boolean isFilterProperty(Object element, String property)
	// {
	// return false;
	// }

	// /**
	// * Returns whether the given element makes it through this filter.
	// *
	// * @param viewer the viewer
	// * @param parentElement the parent element
	// * @param element the element
	// * @return <code>true</code> if element is included in the filtered set, and <code>false</code> if excluded
	// */
	// public abstract boolean select(Viewer viewer, T parentElement, T element);

	boolean select(T item);
}
