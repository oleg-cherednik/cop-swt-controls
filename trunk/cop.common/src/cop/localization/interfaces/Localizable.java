package cop.localization.interfaces;

import java.util.Locale;

public interface Localizable
{
	String i18n();

	String i18n(Locale locale);
}
