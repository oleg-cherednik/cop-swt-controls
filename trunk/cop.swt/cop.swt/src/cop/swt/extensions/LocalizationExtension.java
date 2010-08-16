/**
 * @licence GNU Leser General Public License
 * @author cop
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.extensions;

import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;

import cop.swt.enums.CountryEnum;
import cop.swt.enums.LanguageEnum;
import cop.swt.widgets.localization.interfaces.Localizable;

public final class LocalizationExtension
{
	private LocalizationExtension()
	{}

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
