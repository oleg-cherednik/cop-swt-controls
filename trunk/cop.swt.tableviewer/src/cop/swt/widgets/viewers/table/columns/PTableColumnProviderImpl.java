/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.columns;

import static cop.common.extensions.ReflectionExtension.getType;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_TYPE;

import java.lang.reflect.AccessibleObject;

import cop.swt.widgets.viewers.table.columns.settings.ColumnSettings;
import cop.swt.widgets.viewers.table.columns.settings.ColumnTypeEnum;
import cop.swt.widgets.viewers.table.interfaces.PTableColumnProvider;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 03.09.2010
 */
public final class PTableColumnProviderImpl implements PTableColumnProvider
{
	private static final PTableColumnProvider INSTANSE = new PTableColumnProviderImpl();

	private PTableColumnProviderImpl()
	{}

	/*
	 * PTableColumnProvider
	 */

	@Override
	public <T> ColumnSettings<T> createColumn(AccessibleObject obj, ColumnContext context)
	{
		Class<?> type = getType(obj, DEF_TYPE);

		for(ColumnTypeEnum columnType : ColumnTypeEnum.values())
			if(columnType.check(type, obj))
				return columnType.createColumnSettings(obj, context);

		throw new IllegalArgumentException("Can't parse " + ColumnTypeEnum.class.getSimpleName());
	}

	/*
	 * static
	 */

	public static <T> ColumnSettings<T> createColumnSettings(AccessibleObject obj, PTableColumnProvider columnProvider,
	                ColumnContext context)
	{
		ColumnSettings<T> column = null;

		if(columnProvider != null)
			column = columnProvider.createColumn(obj, context);

		return (column != null) ? column : INSTANSE.<T> createColumn(obj, context);
	}
}
