package cop.swt.tmp.localization;

import java.util.Locale;

import cop.swt.extensions.LocalizationExtension;
import cop.swt.widgets.annotations.i18n;
import cop.swt.widgets.localization.interfaces.Localizable;

public enum CountEnum implements Localizable
{
	ONE,
	TWO,
	THREE,
	FOUR,
	FIVE,
	SIX,
	SEVEN,
	EIGHT,
	NINE,
	TEN;

	@i18n
	public static String[] getLocalizedName()
	{
		return getLocalizedName(Locale.getDefault());
	}

	@i18n
	public static String[] getLocalizedName(Locale locale)
	{
		return LocalizationExtension.i18n(values(), locale);
	}

	/*
	 * Localizable
	 */

	@Override
	public String i18n()
	{
		return LocalizationExtension.i18n(this, name());
	}

	@Override
	public String i18n(Locale locale)
	{
		return LocalizationExtension.i18n(this, name(), locale);
	}
}
