package cop.swt.widgets.tmp.localization;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cop.common.extensions.LocaleExtension;
import cop.swt.widgets.localization.interfaces.Localizable;

public enum StateBundleEnum implements Localizable<String>
{
	STATE0("State 0", "State 0", "Zustand 0", "Состояние 0"),
	STATE1("State 1", "State 1", "Zustand 1", "Состояние 1"),
	STATE2("State 2", "State 2", "Zustand 2", "Состояние 2"),
	STATE3("State 3", "State 3", "Zustand 3", "Состояние 3");

	private Map<Locale, String> map = new HashMap<Locale, String>();

	private StateBundleEnum(String en_US, String en_UK, String de_DE, String ru_RU)
	{
		map.put(Locale.US, en_US);
		map.put(Locale.UK, en_UK);
		map.put(Locale.GERMANY, de_DE);
		map.put(LocaleExtension.RUSSIA, ru_RU);
	}

	@Override
	public String i18n()
	{
		return LocalizationHelper.i18n(map);
	}

	@Override
	public String i18n(Locale locale)
	{
		return LocalizationHelper.i18n(map, locale);
	}
}
