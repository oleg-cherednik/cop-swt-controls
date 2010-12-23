package cop.swt.tmp.localization;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cop.swt.extensions.LocalizationExtension;
import cop.swt.widgets.localization.interfaces.EditLocalizable;

public class Name implements EditLocalizable
{
	private Map<Locale, String> map = new HashMap<Locale, String>();

	public Name(String en_US, String en_UK, String de_DE, String ru_RU)
	{
		map.put(Locale.US, en_US);
		map.put(Locale.UK, en_UK);
		map.put(Locale.GERMANY, de_DE);
		map.put(LocalizationExtension.RU, ru_RU);
	}

	/*
	 * Localizable
	 */

	@Override
	public String i18n()
	{
		return i18n(Locale.getDefault());
	}

	@Override
	public String i18n(Locale locale)
	{
		return LocalizationExtension.i18n(map, locale, map.get(Locale.US));
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
