/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.ReflectionExtension.getNumberValue;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.annotations.services.CurrencyService;
import cop.swt.widgets.viewers.table.celleditors.SpinnerCellEditor;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 20.12.2010
 */
public class CurrencyColumn<T> extends NumericColumn<T>
{
	private NumberFormat currencyFormat;

	protected CurrencyColumn(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);
		
		this.currencyFormat = configNumberFormat(NumberFormat.getCurrencyInstance(locale));
		this.range = CurrencyService.getContent(obj, currencyFormat.getMaximumFractionDigits());
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		if(isNotNull(locale))
			currencyFormat = configNumberFormat(NumberFormat.getCurrencyInstance(locale));
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
			return currencyFormat.format(obj);

		return isEmptyable() ? "" : currencyFormat.format(0);

	}

	@Override
	public CellEditor getCellEditor(Composite parent)
	{
		if(editor == null)
		{
			NumberFormat nf = configNumberFormat(NumberFormat.getNumberInstance(locale));
			editor = new SpinnerCellEditor(parent, nf, range, SWT.NONE);
		}

		return editor;
	}

	private static NumberFormat configNumberFormat(NumberFormat numberFormat)
	{
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);

		return numberFormat;
	}
}
