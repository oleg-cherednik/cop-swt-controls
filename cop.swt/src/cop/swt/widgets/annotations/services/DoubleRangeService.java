/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.annotations.services;

import static cop.common.extensions.ReflectionExtension.getType;
import static cop.common.extensions.ReflectionExtension.*;

import java.lang.reflect.AccessibleObject;

import cop.common.RangeContent;
import cop.swt.widgets.annotations.DoubleRange;

public final class DoubleRangeService
{
	public static final double DEF_MINIMUM = -Integer.MIN_VALUE;
	public static final double DEF_MAXIMUM = Integer.MAX_VALUE;
	public static final double DEF_INCREMENT = 1;

	private DoubleRangeService()
	{}

	/*
	 * static
	 */

	public static RangeContent getContent(AccessibleObject obj, int fractionDigits)
	{
		int multiplier = (int)Math.pow(10, fractionDigits);
		double minimum = DEF_MINIMUM;
		double maximum = DEF_MAXIMUM;
		double increment = DEF_INCREMENT;

		if(obj != null)
		{
			DoubleRange annotation = obj.getAnnotation(DoubleRange.class);

			if(annotation != null)
			{
				checkObj(obj);

				minimum = annotation.min();
				maximum = annotation.max();
				increment = annotation.inc();
			}
		}

		return new RangeContent(minimum / multiplier, maximum / multiplier, increment, multiplier);
	}

	private static void checkObj(AccessibleObject obj)
	{
		if(!isDouble(getType(obj)))
			throw new IllegalArgumentException("@" + DoubleRange.class.getSimpleName()
			                + " annotation can be used only with <Double> or <double> type");
	}
}
