package cop.swt.widgets.tmp.localization;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cop.swt.widgets.localization.interfaces.Localizable;

public enum BundleEnum1 implements Localizable<String>
{
	COL_NAME("Name", "Name", "Name", "Имя"),
	COL_DATE("Date", "Date", "Datum", "Дата"),
	COL_NUMBER("Number", "Number", "Nummer", "Номер"),
	COL_PRICE("Price", "Price", "Preis", "Стоимость");

	private Map<Locale, String> map = new HashMap<Locale, String>();

	private BundleEnum1(String en_US, String en_UK, String de_DE, String ru_RU)
	{
		map.put(Locale.US, en_US);
		map.put(Locale.UK, en_UK);
		map.put(Locale.GERMANY, de_DE);
		map.put(new Locale("ru", "RU"), ru_RU);
	}

	@Override
	public String i18n()
	{
		return LocalizationHelper.i18n(map);
	}

	@Override
	public String i18n(Locale locale)
	{
		return LocalizationHelper.i18n(map, locale);
	}
}
