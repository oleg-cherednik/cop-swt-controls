/**
 * @licence GNU Leser General Public License
 * @author cop
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.extensions;

import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.getNotEmptyText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.core.runtime.Assert;

import cop.swt.enums.CountryEnum;
import cop.swt.enums.LanguageEnum;
import cop.swt.widgets.localization.interfaces.Localizable;

public final class LocalizationExtension
{
	public static final Locale RUSSIA = new Locale("ru", "RU");
	public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

	private LocalizationExtension()
	{}

	public static Locale getApplicationDefaultLocale()
	{
		return Locale.getDefault();
	}

	public static String i18n(Map<Locale, String> map, String def)
	{
		return i18n(map, getApplicationDefaultLocale(), def);
	}

	public static String i18n(Map<Locale, String> map, Locale locale, String def)
	{
		if(isNull(map) || map.isEmpty())
			return def;

		if(isNull(locale))
			return getNotEmptyText(map.get(DEFAULT_LOCALE), def);

		return getNotEmptyText(map.get(locale), getNotEmptyText(map.get(DEFAULT_LOCALE), def));
	}

	public static Locale createLocale(LanguageEnum language, CountryEnum country)
	{
		Assert.isLegal(isNotNull(language));

		if(isNotNull(country))
			return new Locale(language.getCode(), country.getCode());

		return new Locale(language.getCode());
	}

	public static String[] getLanguagesName(LanguageEnum[] languages)
	{
		return getLanguagesName(languages, null);
	}

	public static String[] getLanguagesName(LanguageEnum[] languages, Locale locale)
	{
		if(isEmpty(languages))
			return new String[0];

		List<String> names = new ArrayList<String>();

		for(LanguageEnum language : languages)
			names.add(language.getLocalizedName(locale));

		return names.toArray(new String[0]);
	}

	public static String[] getLocalizedNames(Localizable<String>[] objs, Locale locale)
	{
		if(isEmpty(objs) || isNull(locale))
			return new String[0];

		String[] res = new String[objs.length];

		for(int i = 0, size = objs.length; i < size; i++)
			res[i] = objs[i].i18n(locale);

		return res;
	}
}
