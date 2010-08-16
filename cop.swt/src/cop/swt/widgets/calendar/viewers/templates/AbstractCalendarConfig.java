package cop.swt.widgets.calendar.viewers.templates;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isNotEmpty;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Control;

public abstract class AbstractCalendarConfig implements ICalendarConfig
{
	public static void applyTemplate(Control ctrl, CalendarConfigUnit config)
	{
		if(isNotNull(config))
		{
			Color color = config.getBackground();

			if(isNotNull(color))
				ctrl.setBackground(color);
		}

		if(isNotNull(config))
		{
			Color color = config.getForeground();

			if(isNotNull(color))
				ctrl.setForeground(color);
		}

		if(isNotNull(config))
		{
			FontData fontData = config.getFontData();

			if(isNotNull(fontData))
			{
				Device device = ctrl.getDisplay();
				Font font = new Font(device, mergeFontData(ctrl.getFont().getFontData()[0], fontData));
				ctrl.setFont(font);
			}
		}
	}

	public static FontData mergeFontData(FontData dest, FontData src)
	{
		if(isNull(dest) || isNull(src))
			return dest;

		if(src.getHeight() > 0)
			dest.setHeight(src.getHeight());
		if(isNotEmpty(src.getLocale()))
			dest.setLocale(src.getLocale());
		if(isNotEmpty(src.getName()))
			dest.setName(src.getName());
		if(src.getStyle() != -1)
			dest.setStyle(src.getStyle());

		return dest;

	}

	@Override
	public CalendarConfigUnit getHeaderPart(Object parent)
	{
		return new CalendarConfigUnit(parent);
	}

	@Override
	public CalendarConfigUnit getMonthYearPart(Object parent)
	{
		return new CalendarConfigUnit(parent);
	}

	@Override
	public CalendarConfigUnit getWeekdayTitlePart(Object parent)
	{
		return new CalendarConfigUnit(parent);
	}

	@Override
	public CalendarConfigUnit getSaturdayPart(Object parent)
	{
		return new CalendarConfigUnit(parent);
	}

	@Override
	public CalendarConfigUnit getSundayPart(Object parent)
	{
		return new CalendarConfigUnit(parent);
	}

	@Override
	public CalendarConfigUnit getBodyPart(Object parent)
	{
		return new CalendarConfigUnit(parent);
	}

	@Override
	public CalendarConfigUnit getWeekNumberPart(Object parent)
	{
		return new CalendarConfigUnit(parent);
	}

	@Override
	public Color getTodayMarkerColor(Object parent)
	{
		return new CalendarConfigUnit(parent).getBackground();
	}

	@Override
	public CalendarConfigUnit getWeekdaySameMonthPart(Object parent)
	{
		return new CalendarConfigUnit(parent);
	}

	@Override
	public CalendarConfigUnit getWeekdayOtherMonthPart(Object parent)
	{
		return new CalendarConfigUnit(parent);
	}

	@Override
	public CalendarConfigUnit getWeekdayDayActivePart(Object parent)
	{
		return new CalendarConfigUnit(parent);
	}

	@Override
	public CalendarConfigUnit getWeekdayDayInactivePart(Object parent)
	{
		return new CalendarConfigUnit(parent);
	}

	@Override
	public CalendarConfigUnit getTodayLinePart(Object parent)
	{
		return new CalendarConfigUnit(parent);
	}
}
