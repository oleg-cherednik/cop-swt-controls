package cop.system.store.widows;

import static cop.system.store.widows.HKeyEnum.HKCU;

interface PropertyPath
{
	String REGEX_TOKEN = "\\w+\\t\\w+\\t.*";
	String REGEX_VOLUME = "[a-zA-Z_ ]+\\\\.*";
	String REGQUERY_UTIL = "reg query";

	String CONTROL_PANEL = HKCU + "\\Control Panel";
	String INTERNATIONAL = CONTROL_PANEL + "\\International";
	String CALENDARS = INTERNATIONAL + "\\Calendars";
	String TWO_DIGIT_YEAR_MAX = CALENDARS + "\\TwoDigitYearMax";
}
