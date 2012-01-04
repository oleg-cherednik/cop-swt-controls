/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.annotations.services;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;

import java.lang.reflect.AccessibleObject;

import cop.swt.widgets.annotations.Currency;
import cop.swt.widgets.annotations.contents.RangeContent;

public final class CurrencyService
{
	public static final double DEF_MINIMUM = -Integer.MIN_VALUE;
	public static final double DEF_MAXIMUM = Integer.MAX_VALUE;
	public static final double DEF_INCREMENT = 1;

	private CurrencyService()
	{}

	/*
	 * static
	 */

	public static boolean isCurrency(AccessibleObject obj)
	{
		return isNotNull(obj) ? isNotNull(obj.getAnnotation(Currency.class)) : false;
	}

	public static RangeContent getContent(AccessibleObject obj, int fractionDigits)
	{
		int multiplier = (int)Math.pow(10, fractionDigits);
		double minimum = DEF_MINIMUM;
		double maximum = DEF_MAXIMUM;
		double increment = DEF_INCREMENT;

		if(!isNull(obj))
		{
			Currency annotation = obj.getAnnotation(Currency.class);

			minimum = annotation.min();
			maximum = annotation.max();
			increment = annotation.inc();
		}

		return new RangeContent(minimum / multiplier, maximum / multiplier, increment, multiplier);
	}
}
