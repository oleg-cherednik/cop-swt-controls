package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CompareExtension.compareNumbers;
import static cop.common.extensions.ReflectionExtension.getNumberValue;
import static cop.common.extensions.StringExtension.isNotEmpty;
import static java.text.NumberFormat.getNumberInstance;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.viewers.table.celleditors.SpinnerCellEditor;

public class NumericColumnDescription<T> extends StringColumnDescription<T>
{
	private NumberFormat numberFormat;

	protected NumericColumnDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);
		this.numberFormat = getNumberFormat(locale);
	}

	protected NumberFormat getNumberFormat(Locale locale)
	{
		return getNumberInstance(locale);
	}

	protected Number parseNumber(String value) throws ParseException
	{
		return numberFormat.parse(value);
	}

	/*
	 * ColumnDescription
	 */

	@Override
	public void setValue(T item, Object value) throws Exception
	{
		if(isNotEmpty((String)value))
			invoke(item, getNumberValue(type, parseNumber((String)value)));
		else if(!type.isPrimitive() && isEmptyable())
			invoke(item, (Object)null);
	}

	@Override
	protected int _compare(Object obj1, Object obj2)
	{
		return compareNumbers(type, obj1, obj2);
	}

	@Override
	protected String getText(Object obj)
	{
		if(obj instanceof Number)
			return numberFormat.format(obj);

		return isEmptyable() ? "" : numberFormat.format(0);
	}
	
//	@Override
//	public CellEditor getCellEditor(Composite parent)
//	{
//		return new SpinnerCellEditor(parent, numberFormat, SWT.NONE);
//	}

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
