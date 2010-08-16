package cop.swt.widgets.localization.interfaces;

import java.util.Locale;

public interface Localizable<T>
{
	T i18n();

	T i18n(Locale locale);
}
