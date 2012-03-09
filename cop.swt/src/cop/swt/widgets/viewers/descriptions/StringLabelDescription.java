package cop.swt.widgets.viewers.descriptions;

import static cop.extensions.CommonExt.isNull;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

public class StringLabelDescription<T> extends LabelDescription<T>
{
	/**
	 * Closed constructor
	 * 
	 * @param obj
	 * @param locale
	 */
	protected StringLabelDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);
	}

	protected int _compare(Object obj1, Object obj2)
	{
		return getCollator().compare(obj1, obj2);
	}

	/*
	 * Comparator
	 */

	@Override
	public int compare(T item1, T item2)
	{
		try
		{
			Object obj1 = getValue(item1);
			Object obj2 = getValue(item2);

			if(obj1 == obj2)
				return 0;
			if(isNull(obj1) ^ isNull(obj2))
				return isNull(obj2) ? 1 : -1;

			return _compare(obj1, obj2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return 0;
	}
}
