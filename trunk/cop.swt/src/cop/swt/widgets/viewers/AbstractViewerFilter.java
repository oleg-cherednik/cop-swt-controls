package cop.swt.widgets.viewers;

import static cop.common.extensions.CollectionExtension.isEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cop.swt.widgets.viewers.interfaces.IViewerFilter;

public abstract class AbstractViewerFilter<T> implements IViewerFilter<T>
{
	/**
	 * Filters the given elements for the given viewer. The input array is not modified.
	 * <p>
	 * The default implementation of this method calls {@link #select(Object))} with the parent from the path.
	 * 
	 * @param viewer the viewer
	 * @param items item list
	 * @return the filtered elements
	 */
	@Override
	public List<T> filter(Collection<T> items)
	{
		if(isEmpty(items))
			return Collections.emptyList();

		List<T> arr = new ArrayList<T>();

		for(T item : items)
			if(select(item))
				arr.add(item);

		return arr;
	}
}
