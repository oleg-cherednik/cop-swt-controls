package cop.swt.widgets.calendar.viewers.templates;

import static cop.swt.extensions.ColorExtension.BLACK;
import static cop.swt.extensions.ColorExtension.BLUE;
import static cop.swt.extensions.ColorExtension.DARK_GRAY;
import static cop.swt.extensions.ColorExtension.GRAY;
import static cop.swt.extensions.ColorExtension.MAGENTA;
import static cop.swt.extensions.ColorExtension.RED;
import static cop.swt.extensions.ColorExtension.SELECTION_BACKGROUND;
import static cop.swt.extensions.ColorExtension.SELECTION_FOREGROUND;
import static cop.swt.extensions.ColorExtension.TITLE_INACTIVE_BACKGROUND;
import static cop.swt.extensions.ColorExtension.TITLE_INACTIVE_FOREGROUND;
import static cop.swt.extensions.ColorExtension.WHITE;
import static cop.swt.extensions.ColorExtension.WIDGET_FOREGROUND;
import static org.eclipse.swt.SWT.BOLD;
import static org.eclipse.swt.SWT.ITALIC;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;

public class DefaultCalendarConfig extends AbstractCalendarConfig
{
	@Override
	public CalendarConfigUnit getHeaderPart(Object parent)
	{
		return new CalendarConfigUnit(BLUE, MAGENTA, parent);
	}

	@Override
	public CalendarConfigUnit getWeekdayTitlePart(Object parent)
	{
		return new CalendarConfigUnit(null, BLACK, parent);
	}

	@Override
	public CalendarConfigUnit getSaturdayPart(Object parent)
	{
		return new CalendarConfigUnit(null, RED, parent);
	}

	@Override
	public CalendarConfigUnit getSundayPart(Object parent)
	{
		return new CalendarConfigUnit(null, RED, parent);
	}

	@Override
	public CalendarConfigUnit getBodyPart(Object parent)
	{
		return new CalendarConfigUnit(WHITE, WIDGET_FOREGROUND, parent);
	}

	@Override
	public CalendarConfigUnit getWeekNumberPart(Object parent)
	{
		return new CalendarConfigUnit(null, GRAY, new FontData("", 0, ITALIC | BOLD), parent);
	}

	@Override
	public Color getTodayMarkerColor(Object parent)
	{
		return RED;
	}

	@Override
	public CalendarConfigUnit getWeekdayOtherMonthPart(Object parent)
	{
		return new CalendarConfigUnit(null, DARK_GRAY, null, parent);
	}

	@Override
	public CalendarConfigUnit getWeekdayDayActivePart(Object parent)
	{
		return new CalendarConfigUnit(SELECTION_BACKGROUND, SELECTION_FOREGROUND, parent);
	}

	@Override
	public CalendarConfigUnit getWeekdayDayInactivePart(Object parent)
	{
		return new CalendarConfigUnit(TITLE_INACTIVE_BACKGROUND, TITLE_INACTIVE_FOREGROUND, parent);
	}

	@Override
	public CalendarConfigUnit getTodayLinePart(Object parent)
	{
		return new CalendarConfigUnit(null, null, new FontData("", 0, BOLD), parent);
	}
}
