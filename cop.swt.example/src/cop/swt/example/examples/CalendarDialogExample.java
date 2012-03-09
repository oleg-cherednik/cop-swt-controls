package cop.swt.example.examples;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import cop.extensions.StringExt;
import cop.i18.LocalizationExt;
import cop.swt.extensions.ColorExtension;
import cop.swt.widgets.calendar.CalendarViewer;
import cop.swt.widgets.calendar.viewers.templates.DefaultCalendarConfig;
import cop.swt.widgets.dialogs.CalendarDialog;

public class CalendarDialogExample implements IExample
{
	private CalendarViewer swtcal;
	private Combo localesCombo;
	private Locale[] locales = new Locale[] { Locale.US, Locale.UK, Locale.GERMANY, LocalizationExt.RU,
	                new Locale("en", "RU") };

	private Button button;
	private Text text;
	private Composite parent;

	// private Locale[] locales = Locale.getAvailableLocales();

	@Override
	public void run(Composite _parent)
	{
		parent = createComposite(_parent);
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());

		createLocaleCombo(parent, toolkit);
		swtcal = new CalendarViewer(parent, SWT.BORDER, LocalizationExt.RU, new DefaultCalendarConfig());

		//swtcal.addModifyListener(onDoubleSelectNewDay);

		button = new Button(parent, SWT.BOTTOM | SWT.ARROW);
		text = new Text(parent, SWT.BORDER);

		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

		button.addSelectionListener(onButton);

		// Calendar tmp = Calendar.getInstance(LocaleExtenxion.RUSSIA);

		// swtcal.setDate(tmp);

		// int index = binarySearch(locales, Locale.getDefault());

		// localesCombo.select(isInRangeMin(index, 0, locales.length) ? index : 0);
		// swtcal.setLocale(Locale.getDefault());
	}

	private void createLocaleCombo(Composite parent, FormToolkit toolkit)
	{
		Composite composite = toolkit.createComposite(parent, SWT.NONE);
		composite.setLayout(new RowLayout());

		toolkit.createLabel(composite, "Locale: ", SWT.NONE);

		localesCombo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);

		for(Locale locale : locales)
			if(!StringExt.isEmpty(locale.getCountry()))
				localesCombo.add(locale.getDisplayName());

		localesCombo.addSelectionListener(onChangeLocale);
	}

	private static Composite createComposite(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NONE);

		composite.setBackground(ColorExtension.WHITE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData());

		return composite;
	}

	public Calendar getCalendar()
	{
		return swtcal.getDate();
	}

	public void setDate(Calendar date)
	{
		swtcal.setDate(date);
	}

	/*
	 * Listener
	 */

	private SelectionListener onButton = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent event)
		{
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
			Calendar day = CalendarDialog.getCalendarDate(parent.getShell());
			text.setText((day == null) ? "" : df.format(day.getTime()));
			// Locale locale = locales[localesCombo.getSelectionIndex()];
			//
			// swtcal.setLocale(locale);
		}
	};

	private SelectionListener onChangeLocale = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent event)
		{
			Locale locale = locales[localesCombo.getSelectionIndex()];

			swtcal.setLocale(locale);
		}
	};

	private ModifyListener onDoubleSelectNewDay = new ModifyListener()
	{
		@Override
		public void modifyText(ModifyEvent e)
		{
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
			System.out.println("new day was selected: " + df.format(((Calendar)e.data).getTime()));
		}
	};
}
