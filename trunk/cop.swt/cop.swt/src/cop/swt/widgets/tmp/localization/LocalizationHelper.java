package cop.swt.widgets.tmp.localization;

import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isNotEmpty;

import java.util.Locale;
import java.util.Map;

public final class LocalizationHelper
{
	public static final Locale DEF_LOCALE = Locale.US;

	private LocalizationHelper()
	{}
	
	public static Locale getApplicationDefaultLocale()
	{
		return Locale.getDefault();
	}

	public static String i18n(Map<Locale, String> map)
	{
		return i18n(map, getApplicationDefaultLocale());
	}

	public static String i18n(Map<Locale, String> map, Locale locale)
	{
		if(isNull(map) || map.isEmpty())
			return "";

		if(isNull(locale))
		{
			String str = map.get(DEF_LOCALE);
			return isNotEmpty(str) ? str : "";
		}

		String str = map.get(locale);

		if(isNotEmpty(str))
			return str;

		str = map.get(DEF_LOCALE);

		return isNotEmpty(str) ? str : "";
	}
}
