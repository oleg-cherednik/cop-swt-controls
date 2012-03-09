package cop.swt.tmp;

import static com.ibm.icu.util.Currency.LONG_NAME;
import static cop.extensions.ArrayExt.isEmpty;
import static cop.extensions.CommonExt.isNotNull;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import org.eclipse.core.runtime.Assert;

import com.ibm.icu.util.Currency;

import cop.swt.enums.CountryEnum;
import cop.swt.enums.LanguageEnum;
import cop.swt.widgets.annotations.Label;

/*
 * Represents cu
 */

public final class CurrencyComboWrapper
{
	private Currency currency;

	public CurrencyComboWrapper(CountryEnum country)
	{
		this(country.getLocale());
	}

	public CurrencyComboWrapper(LanguageEnum language, CountryEnum country)
	{
		this(createLocale(language, country));
	}

	public CurrencyComboWrapper(String code)
	{
		this(Currency.getInstance(code));
	}

	public CurrencyComboWrapper(Currency currency)
	{
		Assert.isNotNull(currency);

		this.currency = currency;
	}

	public CurrencyComboWrapper(Locale locale)
	{
		Assert.isNotNull(locale);

		this.currency = Currency.getInstance(locale);
	}

	public String getSymbol()
	{
		return currency.getSymbol();
	}

	public String getSymbol(Locale locale)
	{
		Assert.isNotNull(locale);

		return currency.getSymbol(locale);
	}

	public String getCode()
	{
		return currency.getCurrencyCode();
	}

	public String getName()
	{
		return getName(Locale.getDefault());
	}

	public String getName(Locale locale)
	{
		return currency.getName(isNotNull(locale) ? locale : Locale.getDefault(), LONG_NAME, null, null);
	}

	@Label
	public String getComboString(Locale locale)
	{
		return getName(locale) + " (" + currency.getSymbol(locale) + ")";
	}

	public static CurrencyComboWrapper[] getCurrencies(CountryEnum[] countries)
	{
		if(isEmpty(countries))
			return new CurrencyComboWrapper[0];

		Set<CurrencyComboWrapper> currencies = new LinkedHashSet<CurrencyComboWrapper>();

		for(CountryEnum country : countries)
			currencies.add(new CurrencyComboWrapper(country));

		return currencies.toArray(new CurrencyComboWrapper[0]);
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		return currency.getCurrencyCode() + " (" + currency.getSymbol() + ")";
	}
	
	public static Locale createLocale(LanguageEnum language, CountryEnum country) {
		Assert.isLegal(isNotNull(language));

		if (isNotNull(country))
			return new Locale(language.getCode(), country.getCode());

		return new Locale(language.getCode());
	}
}
