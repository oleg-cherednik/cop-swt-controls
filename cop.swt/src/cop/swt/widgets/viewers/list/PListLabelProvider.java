package cop.swt.widgets.viewers.list;

import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;

import cop.swt.widgets.viewers.list.descriptions.ILabelDescription;

public class PListLabelProvider<T> extends TextLabelProvider
{
	private ILabelDescription<T> description;
	private Map<String, T> map = new HashMap<String, T>();

	public PListLabelProvider(ILabelDescription<T> description)
	{
		Assert.isNotNull(description);

		setDescription(description);
	}

	public void setDescription(ILabelDescription<T> description)
	{
		if(isNotNull(description))
			this.description = description;
	}

	public int getItemCount()
	{
		return map.size();
	}

	public List<T> getItems(String[] labels)
	{
		if(isEmpty(labels))
			return new ArrayList<T>(0);

		List<T> items = new ArrayList<T>(labels.length);

		for(String label : labels)
			items.add(map.get(label));

		return items;
	}

	public List<String> getLabels(Collection<T> items)
	{
		System.out.println("PListLabelProvider.getLabels(" + items.size() + ")");

		if(isEmpty(items))
			return new ArrayList<String>(0);

		List<String> data = new ArrayList<String>(items.size());

		for(T item : items)
		{
			try
			{
				data.add(getText(item));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		return data;

	}

	public void updateItem(T item)
	{
		System.out.println("PListLabelProvider.updateItem()");

		try
		{
			map.put(getText(item), item);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void updateItems(Collection<T> items)
	{
		System.out.println("PListLabelProvider.updateItems(" + items.size() + ")");

		map.clear();

		for(T item : items)
			updateItem(item);
	}

	/*
	 * ILabelProvider
	 */

	@Override
	@SuppressWarnings("unchecked")
	public String getText(Object element)
	{
		try
		{
			return description.getTextValue((T)element);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return "";
	}
}
