package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.ReflectionExtension.getNumberValue;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.annotations.contents.RangeContent;
import cop.swt.widgets.annotations.services.RangeService;
import cop.swt.widgets.viewers.table.celleditors.SpinnerCellEditor;

public class IntegerNumberColumnDescription<T> extends NumericColumnDescription<T>
{
	private NumberFormat integerFormat;
	private final RangeContent range;

	/**
	 * Closed constructor
	 * 
	 * @param obj
	 * @param locale
	 */
	protected IntegerNumberColumnDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);
		
		this.integerFormat = NumberFormat.getIntegerInstance(locale);
		this.range = RangeService.getContent(obj);
	}

	/*
	 * ColumnDescription
	 */
	
	@Override
	public void setValue(T item, Object value) throws Exception
	{
		invoke(item, getNumberValue(type, (Number)value));
	}

	@Override
	protected String getCellText(Object obj)
	{
		if(obj instanceof Number)
			return integerFormat.format(obj);

		return isEmptyable() ? "" : integerFormat.format(0);

	}
	
	@Override
	public CellEditor getCellEditor(Composite parent)
	{
		return new SpinnerCellEditor(parent, integerFormat, range, SWT.NONE);
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		if(isNotNull(locale))
			integerFormat = NumberFormat.getIntegerInstance(locale);
	}
}
