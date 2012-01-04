package cop.swt.widgets.viewers.table.columns;

import static cop.common.extensions.ReflectionExtension.isBoolean;
import static cop.common.extensions.ReflectionExtension.isInteger;
import static cop.common.extensions.ReflectionExtension.isLocalizable;
import static cop.common.extensions.ReflectionExtension.isNumeric;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_TYPE;
import static cop.swt.widgets.annotations.services.CurrencyService.isCurrency;
import static cop.swt.widgets.annotations.services.PercentService.isPercent;

import java.lang.reflect.AccessibleObject;
import java.util.Calendar;
import java.util.Locale;

import org.eclipse.swt.graphics.RGB;

import cop.common.extensions.ReflectionExtension;
import cop.swt.images.ImageProvider;
import cop.swt.widgets.viewers.table.columns.settings.AbstractColumnSettings;
import cop.swt.widgets.viewers.table.columns.settings.BooleanColumn;
import cop.swt.widgets.viewers.table.columns.settings.CalendarColumnDescription;
import cop.swt.widgets.viewers.table.columns.settings.ColorColumn;
import cop.swt.widgets.viewers.table.columns.settings.CurrencyColumn;
import cop.swt.widgets.viewers.table.columns.settings.EnumColumn;
import cop.swt.widgets.viewers.table.columns.settings.IntegerNumberColumn;
import cop.swt.widgets.viewers.table.columns.settings.LocalizableStringColumn;
import cop.swt.widgets.viewers.table.columns.settings.NumericColumn;
import cop.swt.widgets.viewers.table.columns.settings.PercentColumn;
import cop.swt.widgets.viewers.table.columns.settings.StringColumn;

public class PTableColumnSettingsProvider
{
//	public static <T> ColumnSettings<T> createColumnDescription(Class<?> typeAccessibleObject obj, ImageProvider imageProvider,
//	                Locale locale)
//	{
//		Class<?> type = ReflectionExtension.getType(obj, DEF_TYPE);
//
//		if(type.isEnum())
//			return new EnumColumn<T>(obj, locale);
//		if(type.isAssignableFrom(Calendar.class))
//			return new CalendarColumnDescription<T>(obj, locale);
//		if(isBoolean(type))
//			return new BooleanColumn<T>(obj, imageProvider, locale);
//		if(isNumeric(type))
//		{
//			if(isCurrency(obj))
//				return new CurrencyColumn<T>(obj, locale);
//			if(isPercent(obj))
//				return new PercentColumn<T>(obj, locale);
//			if(isInteger(type))
//				return new IntegerNumberColumn<T>(obj, locale);
//
//			return new NumericColumn<T>(obj, locale);
//		}
//		if(type.isAssignableFrom(RGB.class))
//			return new ColorColumn<T>(obj, locale);
//		if(isLocalizable(obj))
//			return new LocalizableStringColumn<T>(obj, locale);
//
//		return new StringColumn<T>(obj, locale);
//	}
}
