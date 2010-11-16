package cop.swt.widgets.viewers.model;

import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.common.extensions.CollectionExtension.isNotEmpty;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.swt.widgets.viewers.model.enums.ModificationTypeEnum.ADD;
import static cop.swt.widgets.viewers.model.enums.ModificationTypeEnum.REMOVE;
import static cop.swt.widgets.viewers.model.enums.ModificationTypeEnum.UPDATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.Viewer;

import cop.common.extensions.CollectionExtension;
import cop.swt.widgets.viewers.model.enums.ModificationTypeEnum;

public class ListModel<T> extends AbstractViewerModel<T>
{
	private List<T> items = new ArrayList<T>();

	public ListModel(String name)
	{
		super(name);
	}

	public void add(T item)
	{
		this.items.add(item);
		modelChanged(item);
	}

	public void add(T item, int index)
	{
		items.add(index, item);
		modelChanged(item);
	}

	public void add(Collection<T> items)
	{
		this.items.addAll(items);
		modelChanged(items);
	}

	public T getItem(int index)
	{
		return items.get(index);
	}

	public void remove(int index)
	{
		items.remove(index);
		modelChanged();
	}

	public void remove(Collection<T> items)
	{
		if(isEmpty(items))
			return;

		items.remove(items);
		modelChanged();
	}

	public int getItemCount()
	{
		return items.size();
	}

	public void swap(int index1, int index2)
	{
		if(CollectionExtension.swap(items, index1, index2))
			modelChanged();
	}

	public void clear()
	{
		if(isEmpty(items))
			return;

		items.clear();
		modelChanged();
	}

	public void set(Collection<T> items)
	{
		this.items.clear();

		if(isNotEmpty(items))
			this.items.addAll(items);

		modelChanged();
	}

	public void updateItem(T item)
	{
		if(isNull(item))
			return;

		int index = items.indexOf(item);

		if(index < 0)
		{
			add(item);
			return;
		}

		items.set(index, item);
		modelChanged(item);
	}

	public boolean remove(T item)
	{
		boolean res = items.remove(item);
		modelChanged();

		return res;
	}

	public boolean removeAll(Collection<T> items)
	{
		boolean res = items.removeAll(items);
		modelChanged();

		return res;
	}

	/*
	 * _IStructuredContentProvider
	 */

	@Override
	public Collection<T> getElements(Collection<T> inputElement)
	{
		return items;
	}

	@Override
	public void dispose()
	{
		items.clear();
	}

	@Override
	public void inputChanged(Viewer viewer, Collection<T> oldItems, Collection<T> newItems)
	{}

	/*
	 * Model
	 */

	@Override
	public void modify(T item, ModificationTypeEnum type)
	{
		if(isNull(item) || isNull(type))
			return;

		if(type == ADD)
			add(item);
		else if(type == REMOVE)
			remove(item);
		else if(type == UPDATE)
			updateItem(item);
		else
			Assert.isTrue(false, "ModificationTypeEnum is not implemented");
	}
}
