package cop.swt.tmp.localization;

import java.util.Locale;

import cop.swt.extensions.LocalizationExtension;
import cop.swt.widgets.localization.interfaces.Localizable;

public enum BundleEnum1 implements Localizable
{
	COL_NAME,
	COL_DATE,
	COL_NUMBER,
	COL_PRICE;

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
