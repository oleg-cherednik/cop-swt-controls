package cop.swt.widgets.viewers.list.descriptions;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

public class BooleanLabelDescription<T> extends AbstractLabelDescription<T>
{
	protected BooleanLabelDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);
	}

	/*
	 * AbstractColumnDescription
	 */

	@Override
	protected int _compare(Object obj1, Object obj2)
	{
		return ((Boolean)obj1).compareTo((Boolean)obj2);
	}
}
