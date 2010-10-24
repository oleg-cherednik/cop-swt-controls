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
