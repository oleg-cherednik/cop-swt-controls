package cop.swt.preferences.detailpages;

//import static cop.swt.preferences.detailpages.ColumnDescriptionComboWrapper.createDescriptionWrapper;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.READ_ONLY;

import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import cop.i18.LocaleSupport;
import cop.swt.preferences.obj.LocalizationPreference;
import cop.swt.widgets.viewers.PComboViewer;

public class ColumnMasterDetailsPage<T> implements LocaleSupport
{
	private Composite composite;
	private FormToolkit toolkit;
	private PComboViewer<ColumnDescriptionComboWrapper<T>> comboViewer;
	private T obj;
	private LocalizationPreference localePreference;
	private ColumnDetailPage columnDetailPage;

	public ColumnMasterDetailsPage(T obj, Composite parent, IPreferenceStore store)
	{
		Assert.isNotNull(obj);
		Assert.isNotNull(parent);

		this.obj = obj;
		this.toolkit = new FormToolkit(parent.getDisplay());

		this.localePreference = new LocalizationPreference(store);
		this.localePreference.addPropertyChangeListener(onLocalePreferenceChange);

		createComposite(parent);
		createContents();
		init();
	}

	public void dispose()
	{
		localePreference.dispose();
	}

	private void createComposite(Composite parent)
	{
		composite = toolkit.createComposite(parent);

		composite.setLayout(createLayout());
		composite.setLayoutData(new GridData(FILL, FILL, true, true));
	}

	public Composite getComposite()
	{
		return composite;
	}

	private void init()
	{
//		try
//		{
//			//comboViewer.setItems(createDescriptionWrapper(obj, ColumnService.getDescriptions(obj.getClass(), null)));
//		}
//		catch(AnnotationDeclarationException e)
//		{
//			e.printStackTrace();
//		}
	}

	private void createContents()
	{
		createMasterPart();
		createDetailsPart();
	}

	private void createMasterPart()
	{
		Composite masterComposite = toolkit.createComposite(composite);
		masterComposite.setLayout(new GridLayout());
		masterComposite.setBackground(composite.getBackground());

		comboViewer = new PComboViewer<ColumnDescriptionComboWrapper<T>>(masterComposite, READ_ONLY);
		comboViewer.setLocale(localePreference.getLocale());
	}

	private void createDetailsPart()
	{
		// createTopDetailsPart();
		createBottomDetailsPart();
	}

	// private void createTopDetailsPart()
	// {
	// Composite topDetailsComposite = toolkit.createComposite(composite);
	// topDetailsComposite.setLayout(new GridLayout(2, false));
	// topDetailsComposite.setLayoutData(new GridData(FILL, CENTER, true, false));
	// topDetailsComposite.setBackground(composite.getBackground());
	//
	// Button defaultButton = toolkit.createButton(topDetailsComposite, "Use default type settings", CHECK);
	// defaultButton.setBackground(topDetailsComposite.getBackground());
	// }

	private void createBottomDetailsPart()
	{
		columnDetailPage = new ColumnDetailPage(composite, localePreference.getLocale());
	}

	private static GridLayout createLayout()
	{
		GridLayout layout = new GridLayout(1, false);

		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;

		return layout;
	}

	/*
	 * Listener
	 */

	private IPropertyChangeListener onLocalePreferenceChange = new IPropertyChangeListener()
	{
		@Override
		public void propertyChange(PropertyChangeEvent event)
		{
			setLocale(localePreference.getLocale());
		}
	};

	/*
	 * LocaleSupport
	 */

	@Override
	public void setLocale(Locale locale)
	{
		comboViewer.setLocale(locale);
		columnDetailPage.setLocale(locale);

		composite.layout();
	}
}
