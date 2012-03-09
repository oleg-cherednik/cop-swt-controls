package cop.swt.tmp.localization;

import java.util.Locale;

import cop.i18.LocaleStore;
import cop.i18.Localizable;

public enum BundleEnum2 implements Localizable {
	COL_PERCENT,
	COL_AMOUNT,
	COL_COUNT,
	COL_MARKET,
	COL_COLOR;

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
