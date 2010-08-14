package cop.swt.widgets.calendar.viewers.templates;

import org.eclipse.swt.graphics.Color;

import cop.swt.widgets.interfaces.WidgetConfig;

public interface ICalendarConfig extends WidgetConfig
{
	CalendarConfigUnit getHeaderPart(Object parent);

	CalendarConfigUnit getMonthYearPart(Object parent);

	CalendarConfigUnit getWeekdayTitlePart(Object parent);

	CalendarConfigUnit getSaturdayPart(Object parent);

	CalendarConfigUnit getSundayPart(Object parent);

	CalendarConfigUnit getBodyPart(Object parent);

	CalendarConfigUnit getWeekNumberPart(Object parent);

	CalendarConfigUnit getWeekdaySameMonthPart(Object parent);

	CalendarConfigUnit getWeekdayOtherMonthPart(Object parent);

	CalendarConfigUnit getWeekdayDayActivePart(Object parent);

	CalendarConfigUnit getWeekdayDayInactivePart(Object parent);

	CalendarConfigUnit getTodayLinePart(Object parent);

	Color getTodayMarkerColor(Object parent);
}
