package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CommonExtension.isNotNull;
import static java.text.NumberFormat.getCurrencyInstance;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;
import java.text.ParseException;
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
		return getCurrencyInstance(locale);
	}

	/*
	 * NumericColumnDescription
	 */

	@Override
	protected Number parseNumber(String value) throws ParseException
	{
		String symbol = numberFormat.getCurrency().getSymbol(locale);

		return numberFormat.parse(value.endsWith(symbol) ? value : (value + " " + symbol));
	}

	/*
	 * ColumnDescription
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
