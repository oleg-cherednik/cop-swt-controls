package cop.swt.widgets.localization;

import static cop.swt.widgets.tmp.localization.LocalizationHelper.getApplicationDefaultLocale;

import cop.swt.widgets.localization.interfaces.Localizable;

public abstract class LocaleAdapter implements Localizable<String>
{
	@Override
	public String i18n()
	{
		return i18n(getApplicationDefaultLocale());
	}
}
