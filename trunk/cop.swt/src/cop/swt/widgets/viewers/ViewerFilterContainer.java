package cop.swt.widgets.viewers;

import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;

import java.util.LinkedHashSet;
import java.util.Set;

import cop.swt.widgets.viewers.interfaces.IViewerFilter;

public class ViewerFilterContainer<T> extends AbstractViewerFilter<T>
{
	private Set<IViewerFilter<T>> filters = new LinkedHashSet<IViewerFilter<T>>();

	public void add(IViewerFilter<T> filter)
	{
		if(isNotNull(filter))
			this.filters.add(filter);
	}

	public void remove(IViewerFilter<T> filter)
	{
		if(isNotNull(filter))
			this.filters.remove(filter);
	}

	/**
	 * Returns the result of running the given elements through the filters.
	 * 
	 * @param elements the elements to filter
	 * @return only the elements which all filters accept
	 */
	// @SuppressWarnings("unchecked")
	// protected T[] filter(T[] elements)
	// {
	// if(isEmpty(filters) || isEmpty(elements))
	// return elements;
	//
	// List<T> filtered = new ArrayList<T>(elements.length);
	// T root = null;// getRoot();
	//
	// all: for(T element : elements)
	// {
	// for(ViewerFilter<T> filter : filters)
	// if(!filter.select(null, root, element))
	// break all;
	//
	// filtered.add(element);
	// }
	//
	// return (T[])filtered.toArray();
	// }

	/**
	 * Returns the filtered array of children of the given element. The resulting array must not be modified, as it may
	 * come directly from the model's internal state.
	 * 
	 * @param parent the parent element
	 * @return a filtered array of child elements
	 */
	// protected T[] getFilteredChildren(T parent)
	// {
	// T[] result = getRawChildren(parent);
	// if(filters != null)
	// {
	// for(Iterator iter = filters.iterator(); iter.hasNext();)
	// {
	// ViewerFilter f = (ViewerFilter)iter.next();
	// result = f.filter(this, parent, result);
	// }
	// }
	// return result;
	// }

	@Override
	public boolean select(T item)
	{
		if(isEmpty(filters) || isNull(item))
			return true;

		for(IViewerFilter<T> filter : filters)
			if(!filter.select(item))
				return false;

		return true;
	}
}
