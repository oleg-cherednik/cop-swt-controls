/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.annotations.services;

import static cop.extensions.ReflectionExt.getType;
import static cop.extensions.ReflectionExt.isInteger;

import java.lang.reflect.AccessibleObject;

import cop.common.RangeContent;
import cop.swt.widgets.annotations.IntegerRange;

public final class IntegerRangeService
{
	public static final int DEF_MINIMUM = Integer.MIN_VALUE;
	public static final int DEF_MAXIMUM = Integer.MAX_VALUE;
	public static final int DEF_INCREMENT = 1;

	private IntegerRangeService()
	{}

	/*
	 * static
	 */

	public static RangeContent getContent(AccessibleObject obj)
	{
		int minimum = DEF_MINIMUM;
		int maximum = DEF_MAXIMUM;
		int increment = DEF_INCREMENT;

		if(obj != null)
		{
			checkObj(obj);
			IntegerRange annotation = obj.getAnnotation(IntegerRange.class);

			if(annotation != null)
			{
				minimum = annotation.min();
				maximum = annotation.max();
				increment = annotation.inc();
			}
		}

		return new RangeContent(minimum, maximum, increment);
	}

	private static void checkObj(AccessibleObject obj)
	{
		if(!isInteger(getType(obj)))
			throw new IllegalArgumentException("@" + IntegerRange.class.getSimpleName()
			                + " annotation can be used only with <Integer> or <int> type");
	}
}
