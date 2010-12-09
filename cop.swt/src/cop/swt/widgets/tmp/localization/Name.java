package cop.swt.widgets.tmp.localization;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cop.common.extensions.LocaleExtension;
import cop.swt.widgets.localization.interfaces.EditLocalizable;

public class Name implements EditLocalizable<String>
{
	private Map<Locale, String> map = new HashMap<Locale, String>();

	public Name(String en_US, String en_UK, String de_DE, String ru_RU)
	{
		map.put(Locale.US, en_US);
		map.put(Locale.UK, en_UK);
		map.put(Locale.GERMANY, de_DE);
		map.put(LocaleExtension.RUSSIA, ru_RU);
	}

	/*
	 * Localizable
	 */

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

	/*
	 * IEditLocalizable
	 */

	@Override
	public void setI18n(String value)
	{
		setI18n(value, Locale.getDefault());
	}

	@Override
	public void setI18n(String value, Locale locale)
	{
		map.put(locale, value);
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		return map.get(Locale.US);
	}
}
