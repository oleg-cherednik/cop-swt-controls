/**
 * @licence GNU Leser General Public License
 * @author cop
 * 
 * $Id$
 * $HeadURL$
 */
package cop.i18;

import java.util.Locale;
import java.util.Map;

public final class LocalizationExt {
	public static final String[] EMPTY_STR_ARR = new String[0];

	public static final Locale RUSSIAN = new Locale("ru", "");
	public static final Locale RU = new Locale("ru", "RU");
	public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

	private LocalizationExt() {}

	public static Locale getDefaultApplicationLocale() {
		return Locale.getDefault();
	}

	public static String i18n(Map<Locale, String> map, String def) {
		return i18n(map, getDefaultApplicationLocale(), def);
	}

	public static String i18n(Map<Locale, String> map, Locale locale, String def) {
		if (isEmpty(map))
			return def;

		if (locale == null)
			return getNotEmptyText(map.get(DEFAULT_LOCALE), def);

		return getNotEmptyText(map.get(locale), getNotEmptyText(map.get(DEFAULT_LOCALE), def));
	}

	// public static Locale createLocale(LanguageEnum language, CountryEnum country) {
	// Assert.isLegal(isNotNull(language));
	//
	// if (isNotNull(country))
	// return new Locale(language.getCode(), country.getCode());
	//
	// return new Locale(language.getCode());
	// }
	//
	// public static String[] getLanguagesName(LanguageEnum[] languages) {
	// return getLanguagesName(languages, null);
	// }
	//
	// public static String[] getLanguagesName(LanguageEnum[] languages, Locale locale) {
	// if (isEmpty(languages))
	// return new String[0];
	//
	// List<String> names = new ArrayList<String>();
	//
	// for (LanguageEnum language : languages)
	// names.add(language.getLocalizedName(locale));
	//
	// return names.toArray(new String[0]);
	// }
	//
	public static String[] i18n(Localizable[] objs) {
		return i18n(objs, getDefaultApplicationLocale());
	}

	public static String[] i18n(Localizable[] objs, Locale locale) {
		if (isEmpty(objs) || locale == null)
			return EMPTY_STR_ARR;

		String[] res = new String[objs.length];

		for (int i = 0, size = objs.length; i < size; i++)
			res[i] = objs[i].i18n(locale);

		return res;
	}

	public static String i18n(Object obj, String key) {
		return LocaleStore._i18n(obj, key);
	}

	public static String i18n(Object obj, String key, Locale locale) {
		return LocaleStore._i18n(obj, key, locale);
	}
//
//	private static ResourceBundle getBundle(Object bundle, Locale locale) {
//		String baseName = "i18n." + bundle.getClass().getSimpleName();
//		ClassLoader loader = bundle.getClass().getClassLoader();
//
//		return ResourceBundle.getBundle(baseName, locale, loader);
//	}
//
	private static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	private static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	private static <T> boolean isEmpty(T[] arr) {
		return arr == null || arr.length == 0;
	}

	private static String getNotEmptyText(String str, String def) {
		return isEmpty(str) ? def : str;
	}
}
