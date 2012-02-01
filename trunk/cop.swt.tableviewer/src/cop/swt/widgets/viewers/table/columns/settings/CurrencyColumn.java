/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.columns.settings;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;

import cop.common.RangeContent;
import cop.swt.widgets.annotations.services.DoubleRangeService;
import cop.swt.widgets.viewers.table.columns.ColumnContext;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 20.12.2010
 */
public class CurrencyColumn<T> extends NumericColumn<T>
{
	protected CurrencyColumn(AccessibleObject obj, ColumnContext context)
	{
		super(obj, context);
	}

	/*
	 * NumericColumn
	 */

	@Override
	protected NumberFormat getNumberFormat()
	{
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);

		return numberFormat;
	}

	@Override
	protected RangeContent getRange()
	{
		return DoubleRangeService.getContent(obj, numberFormat.getMaximumFractionDigits());
	}
}
