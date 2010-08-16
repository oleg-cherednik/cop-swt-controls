package cop.swt.widgets.viewers;

import static cop.common.extensions.CollectionExtension.isEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cop.swt.widgets.viewers.interfaces.IViewerFilter;

public abstract class AbstractViewerFilter<T> implements IViewerFilter<T>
{
	@Override
	public List<T> filter(Collection<T> items)
	{
		if(isEmpty(items))
			return new ArrayList<T>(0);

		List<T> arr = new ArrayList<T>();

		for(T item : items)
			if(select(item))
				arr.add(item);

		return arr;
	}

}
