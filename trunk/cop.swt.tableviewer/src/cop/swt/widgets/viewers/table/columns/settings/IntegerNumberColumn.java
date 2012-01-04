/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.columns.settings;

import static cop.common.extensions.ReflectionExtension.getNumberValue;

import java.text.NumberFormat;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.annotations.contents.RangeContent;
import cop.swt.widgets.annotations.services.RangeService;
import cop.swt.widgets.viewers.table.celleditors.SpinnerCellEditor;
import cop.swt.widgets.viewers.table.columns.ColumnSettingsContext;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 20.12.2010
 */
public class IntegerNumberColumn<T> extends NumericColumn<T>
{
	private NumberFormat integerFormat;
	private final RangeContent range;

	protected IntegerNumberColumn(ColumnSettingsContext context)
	{
		super(context);

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
		if(editor == null)
			editor = new SpinnerCellEditor(parent, integerFormat, range, SWT.NONE);
		return editor;
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		if(locale != null)
			integerFormat = NumberFormat.getIntegerInstance(locale);
	}
}
