package cop.localization.interfaces;

import java.util.Locale;

public interface EditLocalizable extends Localizable
{
	void setI18n(String value);

	void setI18n(String value, Locale locale);
}
