package cop.swt.enums;

import static cop.extensions.CommonExt.isNotNull;
import static cop.extensions.StringExt.isNotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.bindings.keys.ParseException;

import cop.swt.widgets.annotations.Label;

public enum LanguageEnum implements LocalizationAttribute {
	ENGLISH("en", true),
	RUSSIAN("ru", true),
	FINNISH("fi", false),
	GERMAN("de", true);

	private String code;
	private boolean active;
	private String localizedName;

	private LanguageEnum(String code, boolean active) {
		this.code = code;
		this.active = active;

		Locale locale = new Locale(getCode());
		localizedName = locale.getDisplayName(locale);
	}

	public Locale getLocale() {
		return getLocale(null);
	}

	public Locale getLocale(CountryEnum country) {
		return isNotNull(country) ? new Locale(getCode(), country.getCode()) : new Locale(getCode());
	}

	public String getLocalizedName() {
		return getLocalizedName(null);
	}

	@Label
	public String getLocalizedName(Locale locale) {
		return isNotNull(locale) ? getLocale().getDisplayLanguage(locale) : localizedName;
	}

	public static LanguageEnum[] getActiveLanguages() {
		List<LanguageEnum> languages = new ArrayList<LanguageEnum>();

		for (LanguageEnum language : values())
			if (language.isActive())
				languages.add(language);

		return languages.toArray(new LanguageEnum[0]);
	}

	public static LanguageEnum parseLanguageEnum(String language) throws ParseException {
		Assert.isTrue(isNotEmpty(language));

		for (LanguageEnum _language : values())
			if (_language.getCode().equals(language))
				return _language;

		throw new ParseException("Can't parese '" + language + "' to LanguageEnum");
	}

	public static LanguageEnum parseLanguageEnum(Locale locale) throws ParseException {
		Assert.isNotNull(locale);

		String code = locale.getLanguage();

		for (LanguageEnum language : values())
			if (language.getCode().equals(code))
				return language;

		throw new ParseException("Can't parese '" + locale + "' to LanguageEnum");
	}

	/*
	 * LocalizationAttribute
	 */

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public boolean isActive() {
		return active;
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
}
