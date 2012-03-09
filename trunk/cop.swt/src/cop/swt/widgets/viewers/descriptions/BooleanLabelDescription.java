package cop.swt.widgets.viewers.descriptions;

import static cop.extensions.CompareExt.compareNumbers;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

public class BooleanLabelDescription<T> extends LabelDescription<T>
{
	protected BooleanLabelDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);
	}

	/*
	 * Comparator
	 */

	@Override
	public int compare(T item1, T item2)
	{
		try
		{
			return compareNumbers(Boolean.TYPE, getValue(item1), getValue(item2));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
}
