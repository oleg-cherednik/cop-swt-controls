package cop.swt.widgets.viewers.list;

import static cop.common.extensions.ArrayExtension.EMPTY_STR_ARR;
import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;

import cop.swt.widgets.viewers.list.descriptions.ILabelDescription;

public class PListLabelProvider<T> extends TextLabelProvider
{
	private ILabelDescription<T> description;
	// private Map<String, T> map = new HashMap<String, T>();
	private Map<Integer, String> map = new HashMap<Integer, String>();

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
		// if(isEmpty(labels))
		// return new ArrayList<T>(0);
		//
		// List<T> items = new ArrayList<T>(labels.length);
		//
		// for(String label : labels)
		// items.add(map.get(label));
		//
		// return items;
		return new ArrayList<T>(0);
	}

	public String[] getLabels(T[] items)
	{
		if(isEmpty(items))
			return EMPTY_STR_ARR;

		String[] data = new String[items.length];

		for(int i = 0; i < data.length; i++)
			data[i] = getText(items[i]);

		return data;

	}

	/*
	 * ILabelProvider
	 */

	@Override
	@SuppressWarnings("unchecked")
	public String getText(Object element)
	{
		System.out.println("PListLabelProvider.getText()");
		String value = description.getTextValue((T)element);

		map.put(element.hashCode(), value);

		// if(!map.containsKey(value))
		// map.put(value, (T)element);

		return value;
	}

	@Override
	public boolean isLabelProperty(Object element, String property)
	{
		String oldValue = map.get(element.hashCode());
		String newValue = description.getTextValue((T)element);

		return !oldValue.equals(newValue);
	}
}
