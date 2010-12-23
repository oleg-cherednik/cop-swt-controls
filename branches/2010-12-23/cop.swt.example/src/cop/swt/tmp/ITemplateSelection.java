package cop.swt.tmp;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * A template implementation of the <b>IStructuredSelection</b> interface
 * 
 * @see org.eclipse.jface.viewers.IStructuredSelection
 */
public interface ITemplateSelection<T> extends ISelection
{
	/**
	 * Returns the first element in this selection, or <code>null</code> if the selection is empty.
	 * 
	 * @return an element, or <code>null</code> if none
	 */
	public T getFirstElement();

	/**
	 * Returns an iterator over the elements of this selection.
	 * 
	 * @return an iterator over the selected elements
	 */
	public Iterator<T> iterator();

	/**
	 * Returns the number of elements selected in this selection.
	 * 
	 * @return the number of elements selected
	 */
	public int size();

	/**
	 * Returns the elements in this selection as an array.
	 * 
	 * @return the selected elements as an array
	 */
	public T[] toArray();

	/**
	 * Returns the elements in this selection as a <code>List</code>. <strong>Note</strong> In the default
	 * implementation of {@link #toList()} in {@link StructuredSelection} the returned list is not a copy of the
	 * elements of the receiver and modifying it will modify the contents of the selection.
	 * 
	 * @return the selected elements as a list
	 */
	public List<T> toList();
}
