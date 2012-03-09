package cop.swt.tmp.localization;

import java.util.Locale;

import cop.i18.LocaleStore;
import cop.i18.Localizable;
import cop.i18.LocalizationExt;
import cop.i18.annotations.i18n;

public enum CountEnum implements Localizable {
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
	public static String[] getLocalizedName() {
		return getLocalizedName(Locale.getDefault());
	}

	@i18n
	public static String[] getLocalizedName(Locale locale) {
		return LocalizationExt.i18n(values(), locale);
	}

	/*
	 * Localizable
	 */

	@Override
	public String i18n() {
		return LocaleStore._i18n(this, name());
	}

	@Override
	public String i18n(Locale locale) {
		return LocaleStore._i18n(this, name(), locale);
	}
}
