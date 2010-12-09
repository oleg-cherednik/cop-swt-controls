package cop.swt.widgets.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.eclipse.swt.SWT.*;

import cop.common.extensions.LocaleExtension;
import cop.swt.widgets.localization.interfaces.Localizable;
import cop.swt.widgets.tmp.localization.LocalizationHelper;

public enum SortDirectionEnum implements Localizable<String>
{
	SORT_OFF(NONE, "Sort off", "Sort off", "Sortierung ausschalten", "Сортировка выкл."),
	SORT_ASC(UP, "Sort a->z", "Sort a->z", "Sortierung a->z", "Сортировка а->я"),
	SORT_DESC(DOWN, "Sort a<-z", "Sort a<-z", "Sortierung a<-z", "Сортировка а<-я");

	private int swt;
	private Map<Locale, String> map = new HashMap<Locale, String>();

	private SortDirectionEnum(int direction, String en_US, String en_UK, String de_DE, String ru_RU)
	{
		this.swt = direction;

		map.put(Locale.US, en_US);
		map.put(Locale.UK, en_UK);
		map.put(Locale.GERMANY, de_DE);
		map.put(LocaleExtension.RUSSIA, ru_RU);
	}

	public int getSwtDirection()
	{
		return swt;
	}

	public static SortDirectionEnum parseSwtDirection(int swt)
	{
		for(SortDirectionEnum value : values())
			if(value.swt == swt)
				return value;

		return SORT_OFF;
	}

	/*
	 * Localizable
	 */

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
