package cop.swt.enums;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.bindings.keys.ParseException;

import cop.swt.widgets.annotations.Label;

public enum CountryEnum implements LocalizationAttribute
{
	GREAT_BRITAIN("GB", "en", true),
	RUSSIAN_FEDERATION("RU", "ru", true),
	GERMANY("DE", "de",true),
	UNITED_STATES("US", "en", true);

	private String code;
	private String language;
	private boolean active;

	private CountryEnum(String code, String language, boolean active)
	{
		this.code = code;
		this.language = language;
		this.active = active;
	}
	
	public Locale getLocale()
	{
		return new Locale(language, code);
	}

	public Locale getLocale(LanguageEnum language)
	{
		if(isNull(language))
			throw new NullPointerException("To create locale, language must be set");

		return new Locale(language.getCode(), getCode());
	}

	public Locale getLocale(Locale locale)
	{
		if(isNull(locale) || isEmpty(locale.getLanguage()))
			throw new NullPointerException("To create locale, language must be set");

		return new Locale(locale.getLanguage(), getCode());
	}
	
	public String getLanguage()
	{
		return language;
	}

	public String getLocalizedName()
	{
		return getLocalizedName(Locale.getDefault());
	}

	@Label
	public String getLocalizedName(Locale locale)
	{
		return isNotNull(locale) ? getLocale(locale).getDisplayCountry(locale) : getLocalizedName();
	}

	public static CountryEnum parseCountryEnum(String country) throws ParseException
	{
		Assert.isNotNull(country);

		for(CountryEnum _country : values())
			if(_country.getCode().equals(country))
				return _country;

		throw new ParseException("Can't parese '" + country + "' to CountryEnum");
	}

	public static CountryEnum parseCountryEnum(Locale locale) throws ParseException
	{
		Assert.isNotNull(locale);

		String code = locale.getCountry();

		for(CountryEnum country : values())
			if(country.getCode().equals(code))
				return country;

		throw new ParseException("Can't parese '" + locale + "' to CountryEnum");
	}

	public static CountryEnum[] getActiveCountries()
	{
		List<CountryEnum> languages = new ArrayList<CountryEnum>();

		for(CountryEnum language : CountryEnum.values())
			if(language.isActive())
				languages.add(language);

		return languages.toArray(new CountryEnum[0]);
	}

	/*
	 * LocalizationAttribute
	 */

	@Override
	public String getCode()
	{
		return code;
	}

	@Override
	public boolean isActive()
	{
		return active;
	}
}
