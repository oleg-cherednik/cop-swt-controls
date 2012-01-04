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

import cop.swt.widgets.viewers.table.columns.ColumnSettingsContext;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 04.01.2012
 */
public enum ColumnTypeEnum
{
	ENUM
	{
		@Override
		protected boolean check(Class<?> type, AccessibleObject obj)
		{
			return type.isEnum();
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(ColumnSettingsContext context)
		{
			return new EnumColumn<T>(context);
		}
	},
	CALENDAR
	{
		@Override
		protected boolean check(Class<?> type, AccessibleObject obj)
		{
			return type.isAssignableFrom(Calendar.class);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(ColumnSettingsContext context)
		{
			return new CalendarColumnDescription<T>(context);
		}
	},
	BOOLEAN
	{
		@Override
		protected boolean check(Class<?> type, AccessibleObject obj)
		{
			return isBoolean(type);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(ColumnSettingsContext context)
		{
			return new BooleanColumn<T>(context);
		}
	},
	CURRENCY
	{
		@Override
		protected boolean check(Class<?> type, AccessibleObject obj)
		{
			return NUMERIC.check(type, obj) && isCurrency(obj);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(ColumnSettingsContext context)
		{
			return new CurrencyColumn<T>(context);
		}
	},
	PERCENT
	{
		@Override
		protected boolean check(Class<?> type, AccessibleObject obj)
		{
			return NUMERIC.check(type, obj) && isPercent(obj);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(ColumnSettingsContext context)
		{
			return new PercentColumn<T>(context);
		}
	},
	INTEGER
	{
		@Override
		protected boolean check(Class<?> type, AccessibleObject obj)
		{
			return NUMERIC.check(type, obj) && isInteger(type);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(ColumnSettingsContext context)
		{
			return new IntegerNumberColumn<T>(context);
		}
	},
	NUMERIC
	{
		@Override
		protected boolean check(Class<?> type, AccessibleObject obj)
		{
			return isNumeric(type);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(ColumnSettingsContext context)
		{
			return new NumericColumn<T>(context);
		}
	},
	COLOR
	{
		@Override
		protected boolean check(Class<?> type, AccessibleObject obj)
		{
			return type.isAssignableFrom(RGB.class);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(ColumnSettingsContext context)
		{
			return new ColorColumn<T>(context);
		}
	},
	LOCALIZABLE_STRING
	{
		@Override
		protected boolean check(Class<?> type, AccessibleObject obj)
		{
			return isLocalizable(obj);
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(ColumnSettingsContext context)
		{
			return new LocalizableStringColumn<T>(context);
		}
	},
	STRING
	{
		@Override
		protected boolean check(Class<?> type, AccessibleObject obj)
		{
			return true;
		}

		@Override
		public <T> ColumnSettings<T> createColumnSettings(ColumnSettingsContext context)
		{
			return new StringColumn<T>(context);
		}
	};

	protected abstract boolean check(Class<?> type, AccessibleObject obj);

	public abstract <T> ColumnSettings<T> createColumnSettings(ColumnSettingsContext context);

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
