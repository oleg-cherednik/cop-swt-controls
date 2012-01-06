/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.columns.settings;

import static cop.common.extensions.ReflectionExtension.getType;
import static cop.common.extensions.ReflectionExtension.isBoolean;
import static cop.common.extensions.ReflectionExtension.isInteger;
import static cop.common.extensions.ReflectionExtension.isLocalizable;
import static cop.common.extensions.ReflectionExtension.isNumeric;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_TYPE;
import static cop.swt.widgets.annotations.services.CurrencyService.isCurrency;
import static cop.swt.widgets.annotations.services.PercentService.isPercent;

import java.lang.reflect.AccessibleObject;
import java.util.Calendar;

import org.eclipse.swt.graphics.RGB;

import cop.swt.widgets.viewers.table.columns.ColumnContext;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 04.01.2012
 */
public enum ColumnTypeEnum
{
	ENUM
	{
		@Override
		public boolean check(Class<?> type, AccessibleObject obj)
		{
			return type.isEnum();
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(AccessibleObject obj, ColumnContext context)
		{
			return new EnumColumn<T>(obj, context);
		}
	},
	CALENDAR
	{
		@Override
		public boolean check(Class<?> type, AccessibleObject obj)
		{
			return type.isAssignableFrom(Calendar.class);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(AccessibleObject obj, ColumnContext context)
		{
			return new CalendarColumnDescription<T>(obj, context);
		}
	},
	BOOLEAN
	{
		@Override
		public boolean check(Class<?> type, AccessibleObject obj)
		{
			return isBoolean(type);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(AccessibleObject obj, ColumnContext context)
		{
			return new BooleanColumn<T>(obj, context);
		}
	},
	CURRENCY
	{
		@Override
		public boolean check(Class<?> type, AccessibleObject obj)
		{
			return NUMERIC.check(type, obj) && isCurrency(obj);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(AccessibleObject obj, ColumnContext context)
		{
			return new CurrencyColumn<T>(obj, context);
		}
	},
	PERCENT
	{
		@Override
		public boolean check(Class<?> type, AccessibleObject obj)
		{
			return NUMERIC.check(type, obj) && isPercent(obj);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(AccessibleObject obj, ColumnContext context)
		{
			return new PercentColumn<T>(obj, context);
		}
	},
	INTEGER
	{
		@Override
		public boolean check(Class<?> type, AccessibleObject obj)
		{
			return NUMERIC.check(type, obj) && isInteger(type);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(AccessibleObject obj, ColumnContext context)
		{
			return new IntegerNumberColumn<T>(obj, context);
		}
	},
	NUMERIC
	{
		@Override
		public boolean check(Class<?> type, AccessibleObject obj)
		{
			return isNumeric(type);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(AccessibleObject obj, ColumnContext context)
		{
			return new NumericColumn<T>(obj, context);
		}
	},
	COLOR
	{
		@Override
		public boolean check(Class<?> type, AccessibleObject obj)
		{
			return type.isAssignableFrom(RGB.class);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(AccessibleObject obj, ColumnContext context)
		{
			return new ColorColumn<T>(obj, context);
		}
	},
	LOCALIZABLE_STRING
	{
		@Override
		public boolean check(Class<?> type, AccessibleObject obj)
		{
			return isLocalizable(obj);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(AccessibleObject obj, ColumnContext context)
		{
			return new LocalizableStringColumn<T>(obj, context);
		}
	},
	STRING
	{
		@Override
		public boolean check(Class<?> type, AccessibleObject obj)
		{
			return true;
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(AccessibleObject obj, ColumnContext context)
		{
			return new StringColumn<T>(obj, context);
		}
	};

	public abstract boolean check(Class<?> type, AccessibleObject obj);

	public abstract <T> ColumnSettings<T> createColumnSettings(AccessibleObject obj, ColumnContext context);

	/*
	 * static
	 */

	public static ColumnTypeEnum parseType(AccessibleObject obj)
	{
		Class<?> type = getType(obj, DEF_TYPE);

		for(ColumnTypeEnum columnType : values())
			if(columnType.check(type, obj))
				return columnType;

		throw new IllegalArgumentException("Can't parse " + ColumnTypeEnum.class.getSimpleName());
	}
}
