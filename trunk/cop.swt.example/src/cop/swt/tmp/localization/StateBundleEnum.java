package cop.swt.tmp.localization;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cop.i18.Localizable;
import cop.i18.LocalizationExt;

public enum StateBundleEnum implements Localizable {
	STATE0("State 0", "State 0", "Zustand 0", "Состояние 0"),
	STATE1("State 1", "State 1", "Zustand 1", "Состояние 1"),
	STATE2("State 2", "State 2", "Zustand 2", "Состояние 2"),
	STATE3("State 3", "State 3", "Zustand 3", "Состояние 3");

	private Map<Locale, String> map = new HashMap<Locale, String>();

	private StateBundleEnum(String en_US, String en_UK, String de_DE, String ru_RU) {
		map.put(Locale.US, en_US);
		map.put(Locale.UK, en_UK);
		map.put(Locale.GERMANY, de_DE);
		map.put(LocalizationExt.RU, ru_RU);
	}

	@Override
	public String i18n() {
		return i18n(Locale.getDefault());
	}

	@Override
	public String i18n(Locale locale) {
		return LocalizationExt.i18n(map, locale, name());
	}
}
