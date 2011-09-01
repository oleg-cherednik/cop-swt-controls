package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CommonExtension.isNotNull;
import static java.text.NumberFormat.getCurrencyInstance;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyColumnDescription<T> extends NumericColumnDescription<T>
{
	private NumberFormat currencyFormat;

	/**
	 * Closed constructor
	 * 
	 * @param obj
	 * @param locale
	 */
	protected CurrencyColumnDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);
		this.currencyFormat = getCurrencyInstance(locale);
	}

	/*
	 * ColumnDescription
	 */

	@Override
	protected String getCellText(Object obj)
	{
		if(obj instanceof Number)
			return currencyFormat.format(obj);

		return isEmptyable() ? "" : currencyFormat.format(0);

	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		if(isNotNull(locale))
			currencyFormat = getCurrencyInstance(locale);
	}
}
