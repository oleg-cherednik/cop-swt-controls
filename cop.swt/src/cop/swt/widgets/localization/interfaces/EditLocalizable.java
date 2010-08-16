package cop.swt.widgets.localization.interfaces;

import java.util.Locale;

public interface EditLocalizable<T> extends Localizable<T>
{
	void setI18n(T value);

	void setI18n(T value, Locale locale);
}
