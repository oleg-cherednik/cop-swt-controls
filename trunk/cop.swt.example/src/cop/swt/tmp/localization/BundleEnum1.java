package cop.swt.tmp.localization;

import java.util.Locale;

import cop.i18.LocaleStore;
import cop.i18.Localizable;

public enum BundleEnum1 implements Localizable {
	COL_NAME,
	COL_DATE,
	COL_NUMBER,
	COL_PRICE;

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
