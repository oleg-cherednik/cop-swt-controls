package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.NumericExtension.compareNumbers;
import static cop.common.extensions.ReflectionExtension.getNumberValue;
import static cop.common.extensions.StringExtension.isNotEmpty;
import static java.text.NumberFormat.getNumberInstance;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;

import cop.common.extensions.ReflectionExtension;
import cop.swt.images.ImageProvider;

public class NumericColumnDescription<T> extends StringColumnDescription<T>
{
	protected NumberFormat numberFormat;

	protected NumericColumnDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);

		numberFormat = getNumberFormat(locale);
	}

	protected NumberFormat getNumberFormat(Locale locale)
	{
		return getNumberInstance(locale);
	}

	protected Object parseNumber(String value) throws Exception
	{
		Assert.isNotNull(value);

		if(isNotEmpty(value))
			return getNumberValue(type, numberFormat.parse(value));

		return ReflectionExtension.getType(getObject()).isPrimitive() ? 0 : null;
	}

	/*
	 * IColumnDescription
	 */

	@Override
	public void setValue(T item, Object value) throws Exception
	{
		invoke(item, parseNumber((String)value));
	}

	/*
	 * AbstractColumnDescription
	 */

	@Override
	protected int _compare(Object obj1, Object obj2)
	{
		return compareNumbers(type, obj1, obj2);
	}

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
