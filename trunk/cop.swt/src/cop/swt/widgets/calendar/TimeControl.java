package cop.swt.widgets.calendar;

import static cop.common.extensions.CalendarExtension.HOUR12_MAX;
import static cop.common.extensions.CalendarExtension.HOUR12_MIN;
import static cop.common.extensions.CalendarExtension.HOUR24_MAX;
import static cop.common.extensions.CalendarExtension.HOUR24_MIN;
import static cop.common.extensions.CalendarExtension.MINUTE_MAX;
import static cop.common.extensions.CalendarExtension.MINUTE_MIN;
import static cop.common.extensions.CalendarExtension.SECOND_MAX;
import static cop.common.extensions.CalendarExtension.SECOND_MIN;
import static cop.common.extensions.CalendarExtension.convertHour24To12;
import static cop.common.extensions.CalendarExtension.createTimeCalendar;
import static cop.common.extensions.CollectionExtension.addNotNull;
import static cop.common.extensions.StringExtension.isEmpty;
import static cop.common.extensions.StringExtension.replaceAll;
import static cop.swt.widgets.calendar.TimeFormatPartEnum.AMPM;
import static cop.swt.widgets.calendar.TimeFormatPartEnum.AMPM_0;
import static cop.swt.widgets.calendar.TimeFormatPartEnum.HOUR12;
import static cop.swt.widgets.calendar.TimeFormatPartEnum.HOUR12_0;
import static cop.swt.widgets.calendar.TimeFormatPartEnum.HOUR24;
import static cop.swt.widgets.calendar.TimeFormatPartEnum.HOUR24_0;
import static cop.swt.widgets.calendar.TimeFormatPartEnum.MINUTE_0;
import static cop.swt.widgets.calendar.TimeFormatPartEnum.SECOND_0;
import static cop.swt.widgets.calendar.TimeFormatPartEnum.isAmPmPart;
import static cop.swt.widgets.calendar.TimeFormatPartEnum.isHourPart;
import static cop.swt.widgets.calendar.TimeFormatPartEnum.isMinutePart;
import static cop.swt.widgets.calendar.TimeFormatPartEnum.isSecondsPart;
import static cop.swt.widgets.calendar.TimeFormatPartEnum.parseTimeFormatPartEnum;
import static cop.swt.widgets.enums.AmPmEnum.AM;
import static cop.swt.widgets.enums.AmPmEnum.parseAmPmEnum;
import static cop.swt.widgets.enums.DateTimePartEnum.AMPM_PART;
import static cop.swt.widgets.enums.DateTimePartEnum.HOUR_PART;
import static cop.swt.widgets.enums.DateTimePartEnum.MINUTE_PART;
import static cop.swt.widgets.enums.DateTimePartEnum.SECOND_PART;
import static cop.system.store.SystemPropertyBridge.getSystemPropertyFactory;
import static cop.system.store.enums.TimePropertyEnum.TIME_AM;
import static cop.system.store.enums.TimePropertyEnum.TIME_FORMAT;
import static cop.system.store.enums.TimePropertyEnum.TIME_PM;
import static cop.system.store.enums.TimePropertyEnum.TIME_SEPARATOR;
import static java.util.Calendar.AM_PM;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import static org.eclipse.swt.SWT.NONE;

import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cop.swt.widgets.PropertiesWrapper;
import cop.swt.widgets.enums.AmPmEnum;
import cop.swt.widgets.enums.DateTimePartEnum;
import cop.swt.widgets.numeric.IntegerNumeric;
import cop.system.store.enums.TimePropertyEnum;
import cop.system.store.interfaces.SystemPropertyFactory;
import cop.system.store.interfaces.SystemPropertyTemplate;

public class TimeControl extends DateTimeControl
{
	public static final String keyTimeSeparator = "time.separator";
	public static final String keyTimeSeparatorSysReplace = "time.separator.replace";
	public static final String keyTimeFormat = "time.format";
	public static final String keyTimeAM = "time.am";
	public static final String keyTimePM = "time.pm";

	public TimeControl(Composite parent)
	{
		this(parent, NONE, null);
	}

	public TimeControl(Composite parent, int style)
	{
		this(parent, style, null);
	}

	public TimeControl(Composite _parent, int style, Properties _properties)
	{
		super(_parent, style);
	}

	@Override
	protected void createMainControls(List<String> parts)
	{
		for(String str : parts)
		{
			TimeFormatPartEnum part = parseTimeFormatPartEnum(str);

			if(isHourPart(part))
			{
				boolean leadZero = part == HOUR12_0 || part == HOUR24_0;
				int minimum = (part == HOUR12 || part == HOUR12_0) ? HOUR12_MIN : HOUR24_MIN;
				int maximum = (part == HOUR12 || part == HOUR12_0) ? HOUR12_MAX : HOUR24_MAX;

				addControl(HOUR_PART, createTextControl(this, leadZero, minimum, maximum));
			}
			else if(isMinutePart(part))
				addControl(MINUTE_PART, createTextControl(this, part == MINUTE_0, MINUTE_MIN, MINUTE_MAX));
			else if(isSecondsPart(part))
				addControl(SECOND_PART, createTextControl(this, part == SECOND_0, SECOND_MIN, SECOND_MAX));
			else if(isAmPmPart(part))
			{
				String am = getProperty(keyTimeAM);
				String pm = getProperty(keyTimePM);

				if(isEmpty(am) && isEmpty(pm))
					break;

				int length = (part == TimeFormatPartEnum.AMPM_0) ? 2 : 1;
				createAmPmControl(length);
			}
			else if(part == TimeFormatPartEnum.SEPARATOR)
				createSeparator(str);
		}
	}

	private void createAmPmControl(int length)
	{
//		String am = getProperty(keyTimeAM);
//		String pm = getProperty(keyTimePM);
//		String[] ampms = new String[] { am, pm };

//		EnumText ctrl = new EnumText(this, NONE, ampms, length);
//		addControl(AMPM_PART, ctrl);
	}

	private void setHour(int hour)
	{
		if(!isEnabled())
			return;

		for(Control ctrl : getControl(HOUR_PART))
		{
			if(!(ctrl instanceof IntegerNumeric))
				continue;

			int maximum = ((IntegerNumeric)ctrl).getMaximum();

			((IntegerNumeric)ctrl).setValue((hour > maximum) ? convertHour24To12(hour) : hour);
		}
	}

	private void setMinute(int minute)
	{
		if(!isEnabled())
			return;

		for(Control ctrl : getControl(MINUTE_PART))
			if(ctrl instanceof IntegerNumeric)
				((IntegerNumeric)ctrl).setValue(minute);
	}

	private void setSecond(int second)
	{
		if(!isEnabled())
			return;

		for(Control ctrl : getControl(SECOND_PART))
			if(ctrl instanceof IntegerNumeric)
				((IntegerNumeric)ctrl).setValue(second);
	}

	private void setAmPm(AmPmEnum amPm)
	{
//		String val = getProperty((amPm == AM) ? keyTimeAM : keyTimePM);

//		for(Control ctrl : getControl(AMPM_PART))
//			if(ctrl instanceof EnumText)
//				((EnumText)ctrl).setText(val);
	}

	private AmPmEnum getAmPm()
	{
//		for(Control ctrl : getControl(AMPM_PART))
//			if(ctrl instanceof EnumText)
//				parseAmPmEnum(((EnumText)ctrl).getValue());

		return AM;
	}

	private int getHour()
	{
		if(isEnabled())
		{
			for(Control ctrl : getControl(HOUR_PART))
				if(ctrl instanceof IntegerNumeric)
					return ((IntegerNumeric)ctrl).getValue();
		}

		return DEFAULT_VALUE;
	}

	private int getMinute()
	{
		if(isEnabled())
		{
			for(Control ctrl : getControl(MINUTE_PART))
				if(ctrl instanceof IntegerNumeric)
					return ((IntegerNumeric)ctrl).getValue();
		}

		return DEFAULT_VALUE;
	}

	private int getSecond()
	{
		if(isEnabled())
		{
			for(Control ctrl : getControl(SECOND_PART))
				if(ctrl instanceof IntegerNumeric)
					return ((IntegerNumeric)ctrl).getValue();
		}

		return DEFAULT_VALUE;
	}

	@Override
	public void setDate(Calendar date)
	{
		if(date == null)
			return;

		setHour(date.get(HOUR_OF_DAY));
		setMinute(date.get(MINUTE));
		setSecond(date.get(SECOND));
		setAmPm(parseAmPmEnum(date.get(AM_PM)));
	}

	@Override
	public Calendar getDate()
	{
		if(!isEnabled())
			return null;

		return createTimeCalendar(getHour(), getMinute(), getSecond());
	}

	@Override
	protected Set<Control> getEnableControls()
	{
		Set<Control> set = super.getEnableControls();

		addNotNull(set, getControl(AMPM_PART));
		addNotNull(set, getControl(HOUR_PART));
		addNotNull(set, getControl(MINUTE_PART));
		addNotNull(set, getControl(SECOND_PART));

		return set;
	}

	@Override
	protected Set<Control> getDataControls()
	{
		Set<Control> set = new LinkedHashSet<Control>();

		addNotNull(set, getControl(HOUR_PART));
		addNotNull(set, getControl(MINUTE_PART));
		addNotNull(set, getControl(SECOND_PART));

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
		case HOUR_PART:
			for(Control ctrl : getControl(HOUR_PART))
				ctrl.setBackground(color);
			break;
		case MINUTE_PART:
			for(Control ctrl : getControl(MINUTE_PART))
				ctrl.setBackground(color);
			break;
		case SECOND_PART:
			for(Control ctrl : getControl(SECOND_PART))
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
		case HOUR_PART:
			for(Control ctrl : getControl(HOUR_PART))
				ctrl.setForeground(color);
			break;
		case MINUTE_PART:
			for(Control ctrl : getControl(MINUTE_PART))
				ctrl.setForeground(color);
			break;
		case SECOND_PART:
			for(Control ctrl : getControl(SECOND_PART))
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
		case HOUR_PART:
			for(Control ctrl : getControl(HOUR_PART))
				return ctrl.getBackground().getRGB();
			break;
		case MINUTE_PART:
			for(Control ctrl : getControl(MINUTE_PART))
				return ctrl.getBackground().getRGB();
			break;
		case SECOND_PART:
			for(Control ctrl : getControl(SECOND_PART))
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
		case HOUR_PART:
			for(Control ctrl : getControl(HOUR_PART))
				return ctrl.getForeground().getRGB();
			break;
		case MINUTE_PART:
			for(Control ctrl : getControl(MINUTE_PART))
				return ctrl.getForeground().getRGB();
			break;
		case SECOND_PART:
			for(Control ctrl : getControl(SECOND_PART))
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
		SystemPropertyFactory factory = getSystemPropertyFactory();
		SystemPropertyTemplate<TimePropertyEnum> sysProp = factory.getTimeSystemPropertyTemplate();

		properties.setOnlyNewProperty(keyTimeAM, sysProp.getSystemProperty(TIME_AM));
		properties.setOnlyNewProperty(keyTimePM, sysProp.getSystemProperty(TIME_PM));

		String sysTimeSeparator = sysProp.getSystemProperty(TIME_SEPARATOR);
		String sysTimeFormat = sysProp.getSystemProperty(TIME_FORMAT);
		String timeSeparator = properties.getProperty(keyTimeSeparator, null);
		String timeFormat = properties.getProperty(keyTimeFormat, null);
		boolean timeSeparatorSysReplace = properties.getBooleanProperty(keyTimeSeparatorSysReplace, false);

		StringBuilder buf = new StringBuilder();

		if(isEmpty(timeFormat))
		{
			buf.append(sysTimeFormat);

			if(!timeSeparatorSysReplace && !isEmpty(timeSeparator))
				replaceAll(buf, sysTimeSeparator, timeSeparator);
		}
		else
		{
			buf.append(timeFormat);

			if(timeSeparatorSysReplace && !isEmpty(timeSeparator))
				replaceAll(buf, timeSeparator, sysTimeSeparator);
		}

		properties.setProperty(keyTimeFormat, "" + buf);
		properties.remove(keyTimeSeparator);

		return properties;
	}

	@Override
	protected List<String> getParts()
	{
		String format = getProperty(keyTimeFormat, "H:mm:ss");
		List<String> parts = new LinkedList<String>();
		Pattern p = Pattern.compile("H+|h+|M+|m+|S+|s+|T+|t+|[^H+|h+|M+|m+|S+|s+|T+|t+]+");
		Matcher m = p.matcher(format);

		while(m.find())
		{
			String group = m.group();
			boolean leadZero = group.length() > 1;

			if(group.matches("H+"))
				parts.add("" + (leadZero ? HOUR24_0 : HOUR24));
			else if(group.matches("h+"))
				parts.add("" + (leadZero ? HOUR12_0 : HOUR12));
			else if(group.matches("M+|m+"))
				parts.add("" + (leadZero ? MINUTE_0 : MINUTE));
			else if(group.matches("S+|s+"))
				parts.add("" + (leadZero ? SECOND_0 : SECOND));
			else if(group.matches("T+|t+"))
				parts.add("" + (leadZero ? AMPM_0 : AMPM));
			else
				parts.add(group);
		}

		return parts.isEmpty() ? null : parts;
	}

	@Override
	public void dispose()
	{
		for(Control ctrl : getAllControls())
			ctrl.dispose();
	}

	@Override
	public void set(int field, int value)
	{
		if(field == HOUR_OF_DAY)
			setHour(value);
		else if(field == MINUTE)
			setMinute(value);
		else if(field == SECOND)
			setSecond(value);
		else if(field == AM_PM)
			setAmPm(parseAmPmEnum(value));
	}

	@Override
	public int get(int field)
	{
		if(field == HOUR_OF_DAY)
			return getHour();

		if(field == MINUTE)
			return getMinute();

		if(field == SECOND)
			return getSecond();

		if(field == AM_PM)
			return getAmPm().getVal();

		return -1;
	}

	@Override
	// @Deprecated
	public void setLayoutData(Object layoutData)
	{
		super.setLayoutData(layoutData);

		for(Control ctrl : getDataControls())
			ctrl.setLayoutData(layoutData);
	}

}
