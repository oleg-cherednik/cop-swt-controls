/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.annotations.services;

import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.ReflectionExtension.getType;
import static cop.common.extensions.ReflectionExtension.isInteger;

import java.lang.reflect.AccessibleObject;

import cop.swt.widgets.annotations.IntegerRange;
import cop.swt.widgets.annotations.contents.RangeContent;

public final class RangeService
{
	public static final int DEF_MINIMUM = Integer.MIN_VALUE;
	public static final int DEF_MAXIMUM = Integer.MAX_VALUE;
	public static final int DEF_INCREMENT = 1;

	private RangeService()
	{}

	/*
	 * static
	 */

	public static RangeContent getContent(AccessibleObject obj)
	{
		int minimum = DEF_MINIMUM;
		int maximum = DEF_MAXIMUM;
		int increment = DEF_INCREMENT;

		if(!isNull(obj))
		{
			checkObj(obj);
			IntegerRange annotation = obj.getAnnotation(IntegerRange.class);

			minimum = annotation.min();
			maximum = annotation.max();
			increment = annotation.inc();
		}

		return new RangeContent(minimum, maximum, increment);
	}

	private static void checkObj(AccessibleObject obj)
	{
		if(!isInteger(getType(obj, null)))
			throw new IllegalArgumentException("@IntegerRange annotation can be used only with Integer or int type");
	}
}
