package cop.swt.widgets.calendar;

import static cop.common.extensions.BitExtension.isBitSet;
import static cop.common.extensions.CalendarExtension.DAY_MAX;
import static cop.common.extensions.CalendarExtension.DAY_MIN;
import static cop.common.extensions.CalendarExtension.MONTH_MAX;
import static cop.common.extensions.CalendarExtension.MONTH_MIN;
import static cop.common.extensions.CollectionExtension.addNotNull;
import static cop.swt.widgets.calendar.DateFormatPartEnum.T_DAY;
import static cop.swt.widgets.calendar.DateFormatPartEnum.T_DAY_0;
import static cop.swt.widgets.calendar.DateFormatPartEnum.T_DAY_WEEK_LONG;
import static cop.swt.widgets.calendar.DateFormatPartEnum.T_DAY_WEEK_SHORT;
import static cop.swt.widgets.calendar.DateFormatPartEnum.T_MONTH;
import static cop.swt.widgets.calendar.DateFormatPartEnum.T_MONTH_0;
import static cop.swt.widgets.calendar.DateFormatPartEnum.T_MONTH_LONG;
import static cop.swt.widgets.calendar.DateFormatPartEnum.T_MONTH_SHORT;
import static cop.swt.widgets.calendar.DateFormatPartEnum.T_YEAR;
import static cop.swt.widgets.calendar.DateFormatPartEnum.T_YEAR_0;
import static cop.swt.widgets.calendar.DateFormatPartEnum.T_YEAR_LONG;
import static cop.swt.widgets.calendar.DateFormatPartEnum.parseDateFormatPartEnum;
import static cop.swt.widgets.enums.DateTimePartEnum.CALENDAR_BUTTON_PART;
import static cop.swt.widgets.enums.DateTimePartEnum.DAY_PART;
import static cop.swt.widgets.enums.DateTimePartEnum.MONTH_PART;
import static cop.swt.widgets.enums.DateTimePartEnum.YEAR_PART;
import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static org.eclipse.swt.SWT.DROP_DOWN;
import static org.eclipse.swt.SWT.FLAT;
import static org.eclipse.swt.SWT.NONE;
import static org.eclipse.swt.SWT.READ_ONLY;

import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cop.swt.widgets.PropertiesWrapper;
import cop.swt.widgets.enums.DateTimePartEnum;
import cop.swt.widgets.numeric.IntegerNumeric;

/**
 * @see SWT#CALENDAR
 * @see SWT#CTRL
 * @see SWT#READ_ONLY
 * 
 * @author Oleg
 * 
 */
public class DateControl extends DateTimeControl
{
	public static final String keyDateSeparator = "date.separator";
	public static final String keyDateSeparatorSysReplace = "date.separator.replace";
	public static final String keyDateFormat = "date.format";
//	private static final String keyDateYearMin = "date.year.min";
//	private static final String keyDateYearMax = "date.year.max";

	public DateControl(Composite parent)
	{
		this(parent, NONE, null);
	}

	public DateControl(Composite parent, int style)
	{
		this(parent, style, null);
	}

	public DateControl(Composite parent, int style, Properties properties)
	{
		super(parent, style, properties);
	}

	@Override
	protected int getNumColumn(int numColumns, int style)
	{
//		numColumns = super.getNumColumn(numColumns, style);
//
//		if(isBitSet(style, DROP_DOWN))
//			numColumns++;
//
//		return numColumns;
		
		return -1;
	}

	@Override
	protected void createMainControls(List<String> parts)
	{
//		int currYear = Calendar.getInstance().get(Calendar.YEAR);

		for(String str : parts)
		{
			DateFormatPartEnum part = parseDateFormatPartEnum(str);

			switch(part)
			{
			case T_YEAR:
			case T_YEAR_0:
			case T_YEAR_LONG:
			{
//				int minumum = getIntegerProperty(keyDateYearMin, currYear - 10);
//				int maximum = getIntegerProperty(keyDateYearMax, currYear + 20);
//				Correctable correctionMethod = new Correctable()
//				{
//					@Override
//					public int correct(int value, Integer minimum, Integer maximum)
//					{
//						return getYearLong(value, minimum);
//					}
//				};

//				addControl(YEAR_PART, createTextControl(this, part == T_YEAR_0, minumum, maximum,
//				                (part == T_YEAR_LONG) ? 4 : 2, correctionMethod));
			}
				break;
			case T_MONTH:
			case T_MONTH_0:
				addControl(MONTH_PART, createTextControl(this, part == T_MONTH_0, MONTH_MIN + 1, MONTH_MAX + 1));
				break;
			case T_MONTH_SHORT:
			case T_MONTH_LONG:
			{
				//String country = Locale.getDefault().getCountry();
				//String[] names = getMonthNames(new Locale(country, country), (part == T_MONTH_LONG) ? LONG : SHORT);
		//		int length = getMaximumLength(names);

				//addControl(MONTH_PART, new EnumText(this, NONE, names, length));
			}
				break;
			case T_DAY:
			case T_DAY_0:
				addControl(DAY_PART, createTextControl(this, part == T_DAY_0, DAY_MIN, DAY_MAX));
				break;
			case T_DAY_WEEK_SHORT:
			case T_DAY_WEEK_LONG:
			{
				//String country = Locale.getDefault().getCountry();
				//String[] names = getWeekdayNames(new Locale(country, country), (part == T_DAY_WEEK_LONG) ? LONG : SHORT);
				//int length = getMaximumLength(names);

				//addControl(DAY_PART, new EnumText(this, NONE, names, length));
			}
				break;
			case T_SEPARATOR:
				createSeparator(str);
				break;
			}
		}
	}

	protected void createCalendarButton()
	{
		Button button = new Button(this, SWT.RADIO | FLAT);

		button.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				//setDate(getCalendarDate(getShell(), getDate()));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e)
			{}
		});

		button.setLayoutData(createGridData());
		button.setSelection(true);

		addControl(CALENDAR_BUTTON_PART, button);
	}

	@Override
	protected void createButtonControls(int style)
	{
		if(isBitSet(style, READ_ONLY))
			return;

		if(isBitSet(style, DROP_DOWN))
			createCalendarButton();

		super.createButtonControls(style);
	}

	public void setYear(int year)
	{
		if(!isEnabled())
			return;

		for(Control ctrl : getControl(YEAR_PART))
			if(ctrl instanceof IntegerNumeric)
				((IntegerNumeric)ctrl).setValue(year);
	}

	public void setMonth(int month)
	{
		if(!isEnabled())
			return;

		for(Control ctrl : getControl(MONTH_PART))
		{
			if(ctrl instanceof IntegerNumeric)
				((IntegerNumeric)ctrl).setValue(month + 1);

//			if(ctrl instanceof EnumText)
//				((EnumText)ctrl).setValue(month);
		}
	}

	public void setDay(int day)
	{
		if(!isEnabled())
			return;

		for(Control ctrl : getControl(DAY_PART))
			if(ctrl instanceof IntegerNumeric)
				((IntegerNumeric)ctrl).setValue(day);
	}

	@Override
	public void setDate(Calendar date)
	{
		if(date == null)
			return;

		setYear(date.get(Calendar.YEAR));
		setMonth(date.get(Calendar.MONTH));
		setDay(date.get(Calendar.DATE));
	}

	public int getYear()
	{
		if(isEnabled())
		{
			for(Control ctrl : getControl(YEAR_PART))
				if(ctrl instanceof IntegerNumeric)
					return ((IntegerNumeric)ctrl).getValue();
		}

		return DEFAULT_VALUE;
	}

	public int getMonth()
	{
		if(isEnabled())
		{
			for(Control ctrl : getControl(MONTH_PART))
			{
				if(ctrl instanceof IntegerNumeric)
					return ((IntegerNumeric)ctrl).getValue() - 1;

//				if(ctrl instanceof EnumText)
//					return ((EnumText)ctrl).getValue();
			}
		}

		return DEFAULT_VALUE;
	}

	public int getDay()
	{
		if(isEnabled())
		{
			for(Control ctrl : getControl(DAY_PART))
				if(ctrl instanceof IntegerNumeric)
					return ((IntegerNumeric)ctrl).getValue();
		}

		return DEFAULT_VALUE;
	}

	@Override
	public Calendar getDate()
	{
		if(!isEnabled())
			return null;

		Calendar date = Calendar.getInstance();

		date.clear();
		date.set(getYear(), getMonth(), getDay());

		return date;
	}

	@Override
	protected Set<Control> getEnableControls()
	{
		Set<Control> set = super.getEnableControls();

		addNotNull(set, getControl(YEAR_PART));
		addNotNull(set, getControl(MONTH_PART));
		addNotNull(set, getControl(DAY_PART));
		addNotNull(set, getControl(CALENDAR_BUTTON_PART));

		return set;
	}

	@Override
	protected Set<Control> getButtonControls()
	{
		Set<Control> set = super.getButtonControls();

		addNotNull(set, getControl(CALENDAR_BUTTON_PART));

		return set;
	}

	@Override
	protected Set<Control> getDataControls()
	{
		Set<Control> set = new LinkedHashSet<Control>();

		addNotNull(set, getControl(YEAR_PART));
		addNotNull(set, getControl(MONTH_PART));
		addNotNull(set, getControl(DAY_PART));

		return set;
	}

	@Override
	public void setBackgroundRGB(DateTimePartEnum part, RGB rgb)
	{
		super.setBackgroundRGB(part, rgb);

		if(part == null || rgb == null)
			return;

		Color color = new Color(getDisplay(), rgb);

		switch(part)
		{
		case COMPOSITE_PART:
		case CALENDAR_BUTTON_PART:
			for(Control ctrl : getControl(CALENDAR_BUTTON_PART))
				ctrl.setBackground(color);
			break;
		case YEAR_PART:
			for(Control ctrl : getControl(YEAR_PART))
				ctrl.setBackground(color);
			break;
		case MONTH_PART:
			for(Control ctrl : getControl(MONTH_PART))
				ctrl.setBackground(color);
			break;
		case DAY_PART:
			for(Control ctrl : getControl(DAY_PART))
				ctrl.setBackground(color);
			break;
		default:
			break;
		}
	}

	@Override
	public void setForegroundRGB(DateTimePartEnum part, RGB rgb)
	{
		super.setForegroundRGB(part, rgb);

		if(part == null || rgb == null)
			return;

		Color color = new Color(getDisplay(), rgb);

		switch(part)
		{
		case COMPOSITE_PART:
		case CALENDAR_BUTTON_PART:
			for(Control ctrl : getControl(CALENDAR_BUTTON_PART))
				ctrl.setForeground(color);
			break;
		case YEAR_PART:
			for(Control ctrl : getControl(YEAR_PART))
				ctrl.setForeground(color);
			break;
		case MONTH_PART:
			for(Control ctrl : getControl(MONTH_PART))
				ctrl.setForeground(color);
			break;
		case DAY_PART:
			for(Control ctrl : getControl(DAY_PART))
				ctrl.setForeground(color);
			break;
		default:
			break;
		}
	}

	@Override
	public RGB getBackgroundRGB(DateTimePartEnum part)
	{
		RGB rgb = super.getBackgroundRGB(part);

		if(rgb != null)
			return rgb;

		switch(part)
		{
		case CALENDAR_BUTTON_PART:
			for(Control ctrl : getControl(CALENDAR_BUTTON_PART))
				return ctrl.getBackground().getRGB();
			break;
		case YEAR_PART:
			for(Control ctrl : getControl(YEAR_PART))
				return ctrl.getBackground().getRGB();
			break;
		case MONTH_PART:
			for(Control ctrl : getControl(MONTH_PART))
				return ctrl.getBackground().getRGB();
			break;
		case DAY_PART:
			for(Control ctrl : getControl(DAY_PART))
				return ctrl.getBackground().getRGB();
			break;
		default:
			break;
		}

		return null;
	}

	@Override
	public RGB getForegroundRGB(DateTimePartEnum part)
	{
		RGB rgb = super.getForegroundRGB(part);

		if(rgb != null)
			return rgb;

		switch(part)
		{
		case CALENDAR_BUTTON_PART:
			for(Control ctrl : getControl(CALENDAR_BUTTON_PART))
				return ctrl.getForeground().getRGB();
			break;
		case YEAR_PART:
			for(Control ctrl : getControl(YEAR_PART))
				return ctrl.getForeground().getRGB();
			break;
		case MONTH_PART:
			for(Control ctrl : getControl(MONTH_PART))
				return ctrl.getForeground().getRGB();
			break;
		case DAY_PART:
			for(Control ctrl : getControl(DAY_PART))
				return ctrl.getForeground().getRGB();
			break;
		default:
			break;
		}

		return null;
	}

	@Override
	protected PropertiesWrapper checkProperties(PropertiesWrapper properties)
	{
//		SystemPropertyFactory factory = getSystemPropertyFactory();
//		SystemPropertyTemplate<DatePropertyEnum> sysProp = factory.getDateSystemPropertyTemplate();
//
//		// properties.setOnlyNewProperty(keyTimeAM, sysProp.getSystemProperty(TIME_AM));
//		// properties.setOnlyNewProperty(keyTimePM, sysProp.getSystemProperty(TIME_PM));
//
//		Map<DatePropertyEnum, String> map = sysProp.getSystemProperties();
////		String sysDateSeparator = sysProp.getSystemProperty(DATE_SEPARATOR);
////		String sysDateShort = sysProp.getSystemProperty(DATE_SHORT);
////		String sysDateLong = sysProp.getSystemProperty(DATE_LONG);
//		String minYear = sysProp.getSystemProperty(YEAR_MIN2, "1930");
//		String maxYear = sysProp.getSystemProperty(YEAR_MAX2, "2029");
//		// String sysDateLong = sysProp.getSystemProperty(DATE_LONG);
//
//		String dateSeparator = properties.getProperty(keyDateSeparator, null);
//		String dateFormat = properties.getProperty(keyDateFormat, null);
//
//		boolean dateSeparatorSysReplace = properties.getBooleanProperty(keyDateSeparatorSysReplace, false);
//
////		StringBuilder buf = new StringBuilder(sysDateShort);
//
//		// if(isEmpty(dateFormat))
//		// {
//		// buf.append(sysTimeFormat);
//		//
//		// if(!timeSeparatorSysReplace && !isEmpty(timeSeparator))
//		// replaceAll(buf, sysTimeSeparator, timeSeparator);
//		// }
//		// else
//		// {
//		// buf.append(dateFormat);
//		//
//		// if(timeSeparatorSysReplace && !isEmpty(timeSeparator))
//		// replaceAll(buf, timeSeparator, sysTimeSeparator);
//		// }
//		// private static final String keyDateYearMin = "date.year.min";
//		// private static final String keyDateYearMax = "date.year.max";
//		properties.setProperty(keyDateYearMin, minYear);
//		properties.setProperty(keyDateYearMax, maxYear);
//		//properties.setProperty(keyDateFormat, "" + buf);
//		properties.remove(keyDateSeparator);
//
//		int a = 0;
//		a++;
//
//		return properties;
		
		return null;
	}

	@Override
	protected List<String> getParts()
	{
		String format = getProperty(keyDateFormat, "d MMMM yyyy 'y.'");
		List<String> parts = new LinkedList<String>();
		Pattern p = Pattern.compile("Y+|y+|M+|m+|D+|d+|[^Y+|y+|M+|m+|D+|d+]+");
		Matcher m = p.matcher(format);

		while(m.find())
		{
			String group = m.group();
			int length = group.length();

			if(group.matches("Y+|y+"))
				addYearPart(parts, length);
			else if(group.matches("M+|m+"))
				addMonthPart(parts, length);
			else if(group.matches("D+|d+"))
				addDaysPart(parts, length);
			else
				parts.add(group);
		}

		return parts.isEmpty() ? null : parts;
	}

	private static void addYearPart(List<String> parts, int length)
	{
		if(length == 1)
			parts.add("" + T_YEAR);
		else if(length == 2)
			parts.add("" + T_YEAR_0);
		else
			parts.add("" + T_YEAR_LONG);
	}

	private static void addMonthPart(List<String> parts, int length)
	{
		if(length == 1)
			parts.add("" + T_MONTH);
		else if(length == 2)
			parts.add("" + T_MONTH_0);
		else if(length == 3)
			parts.add("" + T_MONTH_SHORT);
		else
			parts.add("" + T_MONTH_LONG);
	}

	private static void addDaysPart(List<String> parts, int length)
	{
		if(length == 1)
			parts.add("" + T_DAY);
		else if(length == 2)
			parts.add("" + T_DAY_0);
		else if(length == 3)
			parts.add("" + T_DAY_WEEK_SHORT);
		else
			parts.add("" + T_DAY_WEEK_LONG);
	}

	// public SelectionListener showCalendarOnSelection = new SelectionListener()
	// {
	// @Override
	// public void widgetSelected(SelectionEvent e)
	// {
	// setDate(getCalendarDate(getShell(), getDate()));
	// }
	//
	// @Override
	// public void widgetDefaultSelected(SelectionEvent e)
	// {}
	// };

	@Override
	public void set(int field, int value)
	{
		if(field == YEAR)
			setYear(value);
		else if(field == MONTH)
			setMonth(value);
		else if(field == DATE)
			setDay(value);
	}

	@Override
	public int get(int field)
	{
		if(field == YEAR)
			return getYear();

		if(field == MONTH)
			return getMonth();

		if(field == DATE)
			return getDay();

		return -1;
	}
}
