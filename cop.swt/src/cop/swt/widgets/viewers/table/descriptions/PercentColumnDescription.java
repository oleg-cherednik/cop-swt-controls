package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.ReflectionExtension.getNumberValue;
import static java.text.NumberFormat.getPercentInstance;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;

public class PercentColumnDescription<T> extends NumericColumnDescription<T>
{
	/**
	 * Closed constructor
	 * 
	 * @param obj
	 * @param locale
	 */
	protected PercentColumnDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);
	}

	@Override
	protected NumberFormat getNumberFormat(Locale locale)
	{
		NumberFormat numberFormat = getPercentInstance(locale);

		numberFormat.setMinimumFractionDigits(2);

		return numberFormat;
	}

	/*
	 * NumericColumnDescription
	 */

	@Override
	protected Object parseNumber(String value) throws Exception
	{
		Assert.isNotNull(value);

		try
		{
			return getNumberValue(type, numberFormat.parse(value));
		}
		catch(ParseException e)
		{}

		Number obj = (Number)super.parseNumber(value);

		return obj.doubleValue() / 100;
	}

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
