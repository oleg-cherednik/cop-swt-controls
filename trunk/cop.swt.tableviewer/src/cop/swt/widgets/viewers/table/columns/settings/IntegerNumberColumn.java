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
import cop.swt.widgets.annotations.services.IntegerRangeService;
import cop.swt.widgets.viewers.table.columns.ColumnContext;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 20.12.2010
 */
public class IntegerNumberColumn<T> extends NumericColumn<T>
{
	protected IntegerNumberColumn(AccessibleObject obj, ColumnContext context)
	{
		super(obj, context);
	}

	/*
	 * NumericColumn
	 */

	@Override
	protected NumberFormat getNumberFormat()
	{
		return NumberFormat.getIntegerInstance(locale);
	}

	@Override
	protected RangeContent getRange()
	{
		return IntegerRangeService.getContent(obj);
	}
}
