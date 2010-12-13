package cop.swt.tmp;

import static cop.common.extensions.StringExtension.isNotEmpty;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cop.swt.extensions.LocalizationExtension;
import cop.swt.extensions.LocalizationExtension;
import cop.swt.widgets.annotations.i18n;
import cop.swt.widgets.localization.interfaces.Localizable;

public enum CountEnum implements Localizable<String>
{
	ONE("one", "one", "ein", "����"),
	TWO("two", "two", "zwei", "���"),
	THREE("three", "three", "drei", "���"),
	FOUR("four", "four", "vier", "������"),
	FIVE("five", "five", "funf", "����"),
	SIX("six", "six", "sechs", "�����"),
	SEVEN("seven", "seven", "sieben", "����"),
	EIGHT("eight", "eight", "acht", "������"),
	NINE("nine", "nine", "neun", "������"),
	TEN("ten", "ten", "zehn", "������");

	private Map<Locale, String> map = new HashMap<Locale, String>();

	private CountEnum(String en_US, String en_UK, String de_DE, String ru_RU)
	{
		map.put(Locale.US, en_US);
		map.put(Locale.UK, en_UK);
		map.put(Locale.GERMANY, de_DE);
		map.put(LocalizationExtension.RUSSIA, ru_RU);
	}

	@i18n
	public static String[] getLocalizedName()
	{
		return getLocalizedName(Locale.getDefault());
	}

	@i18n
	public static String[] getLocalizedName(Locale locale)
	{
		return LocalizationExtension.getLocalizedNames(values(), locale);
	}

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
		String str = map.get(locale);

		return isNotEmpty(str) ? str : map.get(Locale.getDefault());
	}
}
