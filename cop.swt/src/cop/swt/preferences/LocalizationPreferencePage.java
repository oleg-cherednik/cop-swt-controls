package cop.swt.preferences;

import static cop.swt.enums.CountryEnum.getActiveCountries;
import static cop.swt.enums.LanguageEnum.getActiveLanguages;
//import static cop.swt.tmp.CurrencyComboWrapper.getCurrencies;
import static org.eclipse.swt.SWT.CENTER;
import static org.eclipse.swt.SWT.CHECK;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.LEFT;
import static org.eclipse.swt.SWT.READ_ONLY;
import static org.eclipse.swt.SWT.SHADOW_OUT;
import static org.eclipse.swt.SWT.TOP;

import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cop.swt.preferences.obj.LocalizationPreference;
import static cop.extensions.CommonExt.*;
import cop.swt.enums.CountryEnum;
import cop.swt.enums.LanguageEnum;
//import cop.swt.tmp.CurrencyComboWrapper;
import cop.swt.widgets.viewers.PComboViewer;

public class LocalizationPreferencePage extends AbstractPreferencePage
{
	private PComboViewer<LanguageEnum> languageCombo;
	private PComboViewer<CountryEnum> countryCombo;
	//private PComboViewer<CurrencyComboWrapper> currencyCombo;
	private Button defaultLanguageButton;
	private Button defaultCountryButton;
	private Label resultLabel;
	private Locale defaultLocale = Locale.getDefault();

	private LocalizationPreference preference = new LocalizationPreference(store);

	private void createLocalizationPart(Composite parent)
	{
		Composite composite = createCompositeGroup(parent, "Location", new GridLayout(2, true));

		createLabel(composite, "Language");
		createLabel(composite, "Country");

		createLanguageCombo(composite);
		createCountryCombo(composite);
		createDefaultLanguageButon(composite);
		createDefaultCountryButon(composite);

		createResultLabel(composite);
	}

	private void createCurrencyPart(Composite parent)
	{
		Composite composite = createCompositeGroup(parent, "Currency", new GridLayout());

		createCurrencyCombo(composite);
	}

	private static Composite createCompositeGroup(Composite parent, String str, GridLayout layout)
	{
		Assert.isNotNull(parent);

		Group group = createGroup(parent, str);

		return createComposite(group, layout);
	}

	private static Group createGroup(Composite parent, String str)
	{
		Assert.isNotNull(parent);

		Group group = new Group(parent, NONE);

		group.setText(str);
		group.setBackground(parent.getBackground());
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(FILL, TOP, true, false));

		return group;
	}

	private static Composite createComposite(Composite parent, GridLayout layout)
	{
		Assert.isNotNull(parent);

		Composite composite = new Composite(parent, NONE);
		composite.setBackground(parent.getBackground());
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(FILL, FILL, true, true));

		return composite;
	}

	private static void createLabel(Composite parent, String str)
	{
		Assert.isNotNull(parent);

		Label label = new Label(parent, LEFT | SHADOW_OUT);

		label.setBackground(parent.getBackground());
		label.setText(str);
	}

	private void createLanguageCombo(Composite parent)
	{
		languageCombo = new PComboViewer<LanguageEnum>(parent, READ_ONLY);

		languageCombo.setItems(getActiveLanguages());
		languageCombo.addSelectionListener(setLocale);
		languageCombo.setLayoutData(new GridData(FILL, CENTER, true, false));
	}

	private void createDefaultLanguageButon(Composite parent)
	{
		defaultLanguageButton = new Button(parent, CHECK);

		defaultLanguageButton.setText("default");
		defaultLanguageButton.setBackground(parent.getBackground());
		defaultLanguageButton.addSelectionListener(useDefaultLanguage);
	}

	private void createCountryCombo(Composite parent)
	{
		countryCombo = new PComboViewer<CountryEnum>(parent, READ_ONLY);

		countryCombo.setItems(getActiveCountries());
		countryCombo.addSelectionListener(setLocale);
		countryCombo.setLayoutData(new GridData(FILL, CENTER, true, false));
	}

	private void createCurrencyCombo(Composite parent)
	{
//		currencyCombo = new PComboViewer<CurrencyComboWrapper>(parent, READ_ONLY);
//
//		currencyCombo.setItems(getCurrencies(getActiveCountries()));
//		currencyCombo.addSelectionListener(setLocale);
//		currencyCombo.setLayoutData(new GridData(FILL, CENTER, true, false));
	}

	private void createDefaultCountryButon(Composite parent)
	{
		defaultCountryButton = new Button(parent, CHECK);

		defaultCountryButton.setText("default");
		defaultCountryButton.setBackground(parent.getBackground());
		defaultCountryButton.addSelectionListener(useDefaultCountry);
	}

	private void createResultLabel(Composite parent)
	{
		resultLabel = new Label(parent, CENTER | SHADOW_OUT);

		GridData layoutData = new GridData(FILL, CENTER, true, false);
		layoutData.horizontalSpan = 2;

		resultLabel.setLayoutData(layoutData);
		resultLabel.setBackground(parent.getBackground());
	}

	private void setLocale()
	{
//		Locale locale = new Locale(getLanguage(defaultLocale), getCountry(defaultLocale));
//
//		countryCombo.setLocale(locale);
//		currencyCombo.setLocale(locale);
//
//		resultLabel.setText(locale.getDisplayName(locale));
//
//		preference.setLanguage(languageCombo.getSelectionItem());
//		preference.setCountry(countryCombo.getSelectionItem());
//		preference.setCurrency(currencyCombo.getSelectionItem());
//		preference.setUseDefaultLanguage(defaultLanguageButton.getSelection());
//		preference.setUseDefaultCountry(defaultCountryButton.getSelection());
	}

	private String getLanguage(Locale locale)
	{
		Assert.isNotNull(locale);

		LanguageEnum language = languageCombo.getSelectionItem();
		String code;

		if(defaultLanguageButton.getSelection())
			code = locale.getLanguage();
		else
			code = language.getCode();

		return code;
	}

	private String getCountry(Locale locale)
	{
		Assert.isNotNull(locale);

		CountryEnum country = countryCombo.getSelectionItem();
		String code;

		if(defaultCountryButton.getSelection())
			code = locale.getCountry();
		else
			code = country.getCode();

		return code;
	}

	private void setDefaultLanguage(boolean flag, boolean setLocale)
	{
		defaultLanguageButton.setSelection(flag);
		languageCombo.setEnabled(!flag);

		if(setLocale)
			setLocale();
	}

	private void setDefaultCountry(boolean flag, boolean setLocale)
	{
		defaultCountryButton.setSelection(flag);
		countryCombo.setEnabled(!flag);

		if(setLocale)
			setLocale();
	}

	/*
	 * DialogPage
	 */

	@Override
	public void dispose()
	{
		if(isNotNull(preference))
			preference.dispose();

		super.dispose();
	}

	/*
	 * PreferencePage
	 */

	@Override
	protected Control createContents(Composite parent)
	{
		Composite composite = createComposite(parent, new GridLayout());

		createLocalizationPart(composite);
		createCurrencyPart(composite);

		try
		{
			preference.read();

			languageCombo.setSelection(preference.getLanguage());
			countryCombo.setSelection(preference.getCountry());
			//currencyCombo.setSelection(preference.getCurrency());
			setDefaultLanguage(preference.isUseDefaultLanguage(), false);
			setDefaultCountry(preference.isUseDefaultCountry(), true);

			setLocale();
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}

		return composite;
	}

	@Override
	public boolean performOk()
	{
		preference.save();

		return super.performOk();
	}

	/*
	 * Listener
	 */

	private SelectionListener useDefaultLanguage = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			setDefaultLanguage(((Button)e.widget).getSelection(), true);
		}
	};

	private SelectionListener useDefaultCountry = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			setDefaultCountry(((Button)e.widget).getSelection(), true);
		}
	};

	private SelectionListener setLocale = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			setLocale();
		}
	};
}
