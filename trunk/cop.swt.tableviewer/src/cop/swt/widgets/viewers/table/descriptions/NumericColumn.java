/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CompareExtension.compareNumbers;
import static cop.common.extensions.ReflectionExtension.getNumberValue;
import static cop.common.extensions.StringExtension.isNotEmpty;
import static java.text.NumberFormat.getNumberInstance;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 20.12.2010
 */
public class NumericColumn<T> extends StringColumn<T>
{
	private NumberFormat numberFormat;

	protected NumericColumn(AccessibleObject obj, Locale locale)
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

	// @Override
	// public Number getCellEditorValue(T item) throws Exception
	// {
	// Object value = getValue(item);
	// return (Number)value;
	// }

	// @Override
	// public CellEditor getCellEditor(Composite parent)
	// {
	// if(editor == null)
	// editor = new SpinnerCellEditor(parent, numberFormat, SWT.NONE);
	// return editor;
	// }

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		if(locale == null)
			return;

		numberFormat = getNumberFormat(locale);

		if(editor != null)
			editor.dispose();

		editor = null;
	}
}
