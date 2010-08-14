package cop.swt.widgets.calendar;

enum TimeFormatPartEnum
{
	HOUR12, HOUR24, MINUTE, SECOND, AMPM, HOUR12_0, HOUR24_0, MINUTE_0, SECOND_0, AMPM_0, SEPARATOR;

	public static TimeFormatPartEnum parseTimeFormatPartEnum(String str)
	{
		try
		{
			return valueOf(str);
		}
		catch(Exception e)
		{
			return SEPARATOR;
		}
	}

	public static boolean isHourPart(TimeFormatPartEnum part)
	{
		return part == HOUR12 || part == HOUR24 || part == HOUR12_0 || part == HOUR24_0;
	}

	public static boolean isMinutePart(TimeFormatPartEnum part)
	{
		return part == MINUTE || part == MINUTE_0;
	}

	public static boolean isSecondsPart(TimeFormatPartEnum part)
	{
		return part == SECOND || part == SECOND_0;
	}

	public static boolean isAmPmPart(TimeFormatPartEnum part)
	{
		return part == AMPM || part == AMPM_0;
	}
}
