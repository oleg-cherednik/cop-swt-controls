package cop.swt.widgets.tmp;

import static cop.common.extensions.StringExtension.isNotEmpty;

import java.util.Locale;

import cop.swt.widgets.localization.interfaces.Localizable;

public enum MenuEnum implements Localizable<String>
{
	;
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
//		String str = map.get(locale);
//
//		return isNotEmpty(str) ? str : map.get(Locale.US);
		return null;
	}
}
