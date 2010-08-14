package cop.swt.widgets.calendar;

enum DateFormatPartEnum
{
	T_DAY, // d - 5
	T_DAY_0, // dd - 05
	T_DAY_WEEK_SHORT, // ddd - Чт
	T_DAY_WEEK_LONG, // dddd - Четверг
	T_MONTH, // M - 2
	T_MONTH_0, // MM - 02
	T_MONTH_SHORT, // MMM - фев
	T_MONTH_LONG, // MMMM - Февраль
	T_YEAR, // y - 9
	T_YEAR_0, // yy - 09
	T_YEAR_LONG, // yyy - 2009
	T_SEPARATOR;

	public static DateFormatPartEnum parseDateFormatPartEnum(String str)
	{
		try
		{
			return valueOf(str);
		}
		catch(Exception e)
		{
			return T_SEPARATOR;
		}
	}

	public static boolean isYearPart(DateFormatPartEnum part)
	{
		return part == T_YEAR || part == T_YEAR_0 || part == T_YEAR_LONG;
	}

	public static boolean isMonthPart(DateFormatPartEnum part)
	{
		return part == T_MONTH || part == T_MONTH_0 || part == T_MONTH_SHORT || part == T_MONTH_LONG;
	}

	public static boolean isDayPart(DateFormatPartEnum part)
	{
		return part == T_DAY || part == T_DAY_0 || part == T_DAY_WEEK_SHORT || part == T_DAY_WEEK_LONG;
	}
}
