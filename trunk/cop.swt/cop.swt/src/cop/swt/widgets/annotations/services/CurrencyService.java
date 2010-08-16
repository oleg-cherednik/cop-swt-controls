package cop.swt.widgets.annotations.services;

import static cop.common.extensions.CommonExtension.isNotNull;

import java.lang.reflect.AccessibleObject;

import cop.swt.widgets.annotations.Currency;

public final class CurrencyService
{
	/**
	 * Closed constructor
	 */
	private CurrencyService()
	{}

	public static boolean isCurrency(AccessibleObject obj)
	{
		return isNotNull(obj) ? isNotNull(obj.getAnnotation(Currency.class)) : false;
	}
}
