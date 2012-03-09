package cop.swt.preferences.obj;

import static cop.extensions.StringExt.isEmpty;
import static cop.swt.enums.CountryEnum.parseCountryEnum;
import static cop.swt.enums.LanguageEnum.parseLanguageEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;

import cop.extensions.ArrayExt;
import cop.i18.Localizable;
import cop.i18.LocalizationExt;
import cop.swt.enums.CountryEnum;
import cop.swt.enums.LanguageEnum;

public final class LocalizationPreference extends AbstractPreference {
	private static final String PROP_LOCALE_LANGUAGE = "LOCALE_LANGUAGE";
	private static final String PROP_LOCALE_COUNTRY = "LOCALE_COUNTRY";
	private static final String PROP_LOCALE_LANGUAGE_DEF = "LOCALE_LANGUAGE_DEF";
	private static final String PROP_LOCALE_COUNTRY_DEF = "LOCALE_COUNTRY_DEF";
	private static final String PROP_LOCALE_CURRENCY = "LOCALE_CURRENCY";
	private static Set<String> properties;

	private LanguageEnum language;
	private CountryEnum country;
	// private CurrencyComboWrapper currency;
	private boolean useDefaultLanguage;
	private boolean useDefaultCountry;

	static {
		properties = new HashSet<String>();

		properties.add(PROP_LOCALE_LANGUAGE);
		properties.add(PROP_LOCALE_COUNTRY);
		properties.add(PROP_LOCALE_LANGUAGE_DEF);
		properties.add(PROP_LOCALE_COUNTRY_DEF);
		properties.add(PROP_LOCALE_CURRENCY);
	}

	public LocalizationPreference(IPreferenceStore store) {
		super(store);

		try {
			read();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dispose() {
		properties.clear();
	}

	public Locale getLocale() {
		return createLocale(language, country);
	}

	public LanguageEnum getLanguage() {
		return language;
	}

	public CountryEnum getCountry() {
		return country;
	}

	// public CurrencyComboWrapper getCurrency()
	// {
	// return currency;
	// }

	public boolean isUseDefaultLanguage() {
		return useDefaultLanguage;
	}

	public boolean isUseDefaultCountry() {
		return useDefaultCountry;
	}

	public void setLanguage(LanguageEnum language) {
		this.language = language;
	}

	public void setCountry(CountryEnum country) {
		this.country = country;
	}

	// public void setCurrency(CurrencyComboWrapper currency)
	// {
	// this.currency = currency;
	// }

	public void setUseDefaultLanguage(boolean useDefaultLanguage) {
		this.useDefaultLanguage = useDefaultLanguage;
	}

	public void setUseDefaultCountry(boolean useDefaultCountry) {
		this.useDefaultCountry = useDefaultCountry;
	}

	public void save() {
		store.setValue(PROP_LOCALE_LANGUAGE, language.getCode());
		store.setValue(PROP_LOCALE_COUNTRY, country.getCode());
		store.setValue(PROP_LOCALE_LANGUAGE_DEF, useDefaultLanguage);
		store.setValue(PROP_LOCALE_COUNTRY_DEF, useDefaultCountry);
		// store.setValue(PROP_LOCALE_CURRENCY, currency.getCode());
	}

	private void saveDefaults() {
		store.setDefault(PROP_LOCALE_LANGUAGE, language.getCode());
		store.setDefault(PROP_LOCALE_COUNTRY, country.getCode());
		store.setDefault(PROP_LOCALE_LANGUAGE_DEF, useDefaultLanguage);
		store.setDefault(PROP_LOCALE_COUNTRY_DEF, useDefaultCountry);
		// store.setDefault(PROP_LOCALE_CURRENCY, currency.getCode());
	}

	public void read() throws ParseException {
		restoreDefaults();

		if (store.contains(PROP_LOCALE_LANGUAGE))
			language = parseLanguageEnum(store.getString(PROP_LOCALE_LANGUAGE));
		if (store.contains(PROP_LOCALE_COUNTRY))
			country = parseCountryEnum(store.getString(PROP_LOCALE_COUNTRY));

		useDefaultLanguage = store.getBoolean(PROP_LOCALE_LANGUAGE_DEF);
		useDefaultCountry = store.getBoolean(PROP_LOCALE_COUNTRY_DEF);

		// if(store.contains(PROP_LOCALE_COUNTRY))
		// currency = new CurrencyComboWrapper(store.getString(PROP_LOCALE_CURRENCY));
	}

	public void restoreDefaults() {
		try {
			Locale locale = Locale.getDefault();

			this.language = parseLanguageEnum(locale);
			this.country = parseCountryEnum(locale);
			this.useDefaultLanguage = true;
			this.useDefaultCountry = true;
			// this.currency = new CurrencyComboWrapper(language, country);

			saveDefaults();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void updateLanguage(String code) {
		try {
			language = parseLanguageEnum(code);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void updateCountry(String code) {
		try {
			country = parseCountryEnum(code);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/*
	 * AbstractPreference
	 */

	@Override
	protected Set<String> getProperties() {
		return properties;
	}

	/*
	 * Listenre
	 */

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getProperty();

		if (isEmpty(property))
			return;

		if (PROP_LOCALE_LANGUAGE.equals(property))
			updateLanguage("" + event.getNewValue());
		else if (PROP_LOCALE_COUNTRY.equals(property))
			updateCountry("" + event.getNewValue());
		// if(PROP_LOCALE_CURRENCY.equals(property))
		// country = CountryEnum.parseCountryEnum("" + event.getNewValue());
		else if (PROP_LOCALE_LANGUAGE_DEF.equals(property))
			useDefaultLanguage = (Boolean)event.getNewValue();
		else if (PROP_LOCALE_COUNTRY_DEF.equals(property))
			useDefaultCountry = (Boolean)event.getNewValue();

		// private CurrencyComboWrapper currency;
	}

	/*
	 * 
	 */

	public static Locale createLocale(LanguageEnum language, CountryEnum country) {
		if (country != null)
			return new Locale(language.getCode(), country.getCode());

		return new Locale(language.getCode());
	}

	public static String[] getLanguagesName(LanguageEnum[] languages) {
		return getLanguagesName(languages, null);
	}

	public static String[] getLanguagesName(LanguageEnum[] languages, Locale locale) {
		if (ArrayExt.isEmpty(languages))
			return new String[0];

		List<String> names = new ArrayList<String>();

		for (LanguageEnum language : languages)
			names.add(language.getLocalizedName(locale));

		return names.toArray(new String[0]);
	}

	public static String[] i18n(Localizable[] objs) {
		return LocalizationExt.i18n(objs, getDefaultApplicationLocale());
	}

	public static Locale getDefaultApplicationLocale() {
		return Locale.getDefault();
	}
}
