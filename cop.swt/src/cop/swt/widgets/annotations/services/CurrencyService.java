/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.annotations.services;

import java.lang.reflect.AccessibleObject;

import cop.swt.widgets.annotations.Currency;

public final class CurrencyService
{
	private CurrencyService()
	{}

	/*
	 * static
	 */

	public static boolean isCurrency(AccessibleObject obj)
	{
		return (obj != null) ? (obj.getAnnotation(Currency.class) != null) : false;
	}
}
