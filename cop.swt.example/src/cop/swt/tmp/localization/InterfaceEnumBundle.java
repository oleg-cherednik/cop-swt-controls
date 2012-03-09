package cop.swt.tmp.localization;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cop.i18.Localizable;
import cop.i18.LocalizationExt;

public enum InterfaceEnumBundle implements Localizable
{
	VISIBILITY("Visibility", "Visibility", "Sichtbarkeit", "Видимость"),
	VISIBLE("visible", "visible", "sichtbar", "показать"),
	HIDEABLE("hideable", "hideable", "versteckbare", "скрываемый"),
	MOVABLE("movable", "movable", "versteckbare", "двигать"),
	READ_ONLY("read only", "read only", "nur lesen", "т.д. чтения"),
	SORTABLE("sortable", "sortable", "sortierbar", "сортируемый"),
	RESIZEABLE("resizeable", "resizeable", "vergroesserbar", "изм. размер"), ;

	private Map<Locale, String> map = new HashMap<Locale, String>();

	private InterfaceEnumBundle(String en_US, String en_UK, String de_DE, String ru_RU)
	{
		map.put(Locale.US, en_US);
		map.put(Locale.UK, en_UK);
		map.put(Locale.GERMANY, de_DE);
		map.put(LocalizationExt.RU, ru_RU);
	}

	@Override
	public String i18n()
	{
		return i18n(Locale.getDefault());
	}

	@Override
	public String i18n(Locale locale)
	{
		return LocalizationExt.i18n(map, locale, name());
	}

}
