/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.columns.settings;

import static cop.extensions.CompareExt.compareNumbers;
import static cop.extensions.ReflectionExt.getNumberValue;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import cop.common.RangeContent;
import cop.swt.widgets.annotations.services.DoubleRangeService;
import cop.swt.widgets.viewers.table.celleditors.SpinnerCellEditor;
import cop.swt.widgets.viewers.table.columns.ColumnContext;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 20.12.2010
 */
public class NumericColumn<T> extends AbstractColumnSettings<T>
{
	protected NumberFormat numberFormat;
	protected RangeContent range;

	protected NumericColumn(AccessibleObject obj, ColumnContext context)
	{
		super(obj, context);

		this.numberFormat = getNumberFormat();
		this.range = getRange();
	}

	protected NumberFormat getNumberFormat()
	{
		return NumberFormat.getNumberInstance(locale);
	}

	protected RangeContent getRange()
	{
		return DoubleRangeService.getContent(obj, numberFormat.getMaximumFractionDigits());
	}

	protected Number parseNumber(String value) throws ParseException
	{
		return numberFormat.parse(value);
	}

	/*
	 * Comparator
	 */

	@Override
	public int compare(T item1, T item2)
	{
		try
		{
			Object obj1 = getValue(item1);
			Object obj2 = getValue(item2);

			return compareNumbers(type, obj1, obj2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	/*
	 * ColumnSettings
	 */

	@Override
	public void setValue(T item, Object value) throws Exception
	{
		invoke(item, getNumberValue(type, (Number)value));
	}

	@Override
	protected String getText(Object obj)
	{
		if(obj instanceof Number)
		{
//			if(Double.isNaN(((Number)obj).doubleValue()))
//				return "";
//			else
				return numberFormat.format(obj);
		}

		return isEmptyable() ? "" : numberFormat.format(0);
	}

	@Override
	public Number getCellEditorValue(T item) throws Exception
	{
		return (Number)super.getCellEditorValue(item);
	}

	@Override
	public CellEditor getCellEditor(Composite parent)
	{
		if(editor == null)
			editor = new SpinnerCellEditor(parent, numberFormat, range, SWT.NONE);
		return editor;
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		if(locale == null)
			return;
		if(editor != null)
			editor.dispose();

		editor = null;
		numberFormat = getNumberFormat();
	}
}
