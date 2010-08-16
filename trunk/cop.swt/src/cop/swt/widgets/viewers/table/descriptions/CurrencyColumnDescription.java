package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CommonExtension.isNotNull;
import static java.text.NumberFormat.getNumberInstance;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyColumnDescription<T> extends NumericColumnDescription<T>
{
	/**
	 * Closed constructor
	 * 
	 * @param obj
	 * @param locale
	 */
	protected CurrencyColumnDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);
	}

	@Override
	protected NumberFormat getNumberFormat(Locale locale)
	{
		// this.numberFormat.

		// return getCurrencyInstance(locale);
		return getNumberInstance(locale);
	}

	// /*
	// * NumericColumnDescription
	// */
	//
	// @Override
	// protected Object parseNumber(String value) throws Exception
	// {
	// // Assert.isNotNull(value);
	// //
	// // try
	// // {
	// // return getNumberValue(type, numberFormat.parse(value));
	// // }
	// // catch(ParseException e)
	// // {
	// // e.printStackTrace();
	// // }
	//
	// return super.parseNumber(value);
	// }

	/*
	 * AbstractColumnDescription
	 */

	@Override
	protected String getText(Object obj)
	{
		return isNotNull(obj) ? numberFormat.format(obj) : "";
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		if(isNotNull(locale))
			numberFormat = getNumberFormat(locale);
	}
}
