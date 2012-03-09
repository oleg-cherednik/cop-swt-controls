/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.annotations.services;

import static cop.extensions.CommonExt.isNull;

import java.lang.reflect.AccessibleObject;

import cop.common.RangeContent;
import cop.swt.widgets.annotations.Percent;

public final class PercentService
{
	public static final double DEF_MINIMUM = 0;
	public static final double DEF_MAXIMUM = 100;
	public static final double DEF_INCREMENT = 1;

	private PercentService()
	{}

	/*
	 * static
	 */

	public static boolean isPercent(AccessibleObject obj)
	{
		return isNull(obj) ? false : !isNull(obj.getAnnotation(Percent.class));
	}

	public static RangeContent getContent(AccessibleObject obj, int fractionDigits)
	{
		double minimum = DEF_MINIMUM;
		double maximum = DEF_MAXIMUM;
		double increment = DEF_INCREMENT;
		int multiplier = (int)Math.pow(10, fractionDigits);

		if(!isNull(obj))
		{
			Percent annotation = obj.getAnnotation(Percent.class);

			minimum = annotation.min();
			maximum = annotation.max();
			increment = annotation.inc();
		}

		return new RangeContent(minimum, maximum, increment, multiplier);
	}
}
