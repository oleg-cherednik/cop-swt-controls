package cop.swt.widgets.viewers.list;

import static cop.common.extensions.ArrayExtension.EMPTY_STR_ARR;
import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isNotNull;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import cop.swt.widgets.viewers.descriptions.LabelDescription;

public class PListLabelProvider<T> implements ILabelProvider, IColorProvider, IFontProvider
{
	private LabelDescription<T> description;
	private Map<Integer, String> map = new HashMap<Integer, String>();

	public PListLabelProvider(LabelDescription<T> description)
	{
		Assert.isNotNull(description);

		setDescription(description);
	}

	public void setDescription(LabelDescription<T> description)
	{
		if(isNotNull(description))
			this.description = description;
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
	public final Image getImage(Object element)
	{
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getText(Object element)
	{
		String value = description.getTextValue((T)element);

		map.put(element.hashCode(), value);

		return value;
	}

	/*
	 * IBaseLabelProvider
	 */

	@Override
	public void addListener(ILabelProviderListener listener)
	{}

	@Override
	public void removeListener(ILabelProviderListener listener)
	{}

	@Override
	public void dispose()
	{}

	@Override
	public boolean isLabelProperty(Object element, String property)
	{
		String oldValue = map.get(element.hashCode());
		String newValue = description.getTextValue((T)element);

		return !oldValue.equals(newValue);
	}

	/*
	 * IColorProvider
	 */

	@Override
	public Color getForeground(Object element)
	{
		System.out.println("PListLabelProvider.getForeground()");
		return null;
	}

	/*
	 * IFontProvider
	 */

	@Override
	public Color getBackground(Object element)
	{
		System.out.println("PListLabelProvider.getBackground()");
		return null;
	}

	@Override
	public Font getFont(Object element)
	{
		System.out.println("PListLabelProvider.getFont()");
		return null;
	}
}
