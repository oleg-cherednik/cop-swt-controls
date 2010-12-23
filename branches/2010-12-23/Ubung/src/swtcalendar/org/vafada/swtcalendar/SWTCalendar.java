package swtcalendar.org.vafada.swtcalendar;

import static cop.common.extensions.CalendarExtension.YEAR_MAX;
import static cop.common.extensions.CalendarExtension.YEAR_MIN;
import static cop.common.extensions.CalendarExtension.isYear;
import static cop.common.extensions.StringExtension.isEmpty;
import static cop.common.extensions.StringExtension.replace;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static org.eclipse.swt.SWT.ARROW;
import static org.eclipse.swt.SWT.BORDER;
import static org.eclipse.swt.SWT.CENTER;
import static org.eclipse.swt.SWT.FLAT;
import static org.eclipse.swt.SWT.LEFT;
import static org.eclipse.swt.SWT.NONE;
import static org.eclipse.swt.SWT.RIGHT;
import static org.eclipse.swt.SWT.Verify;

import java.util.Calendar;
import java.util.Locale;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;

public class SWTCalendar extends Composite
{
	/**
	 * Style constant for making Sundays red.
	 */
	public static final int RED_SUNDAY = SWTDayChooser.RED_SUNDAY;
	/**
	 * Style constant for making weekends red.
	 */
	public static final int RED_WEEKEND = SWTDayChooser.RED_WEEKEND;

	private boolean settingDate;

	private Spinner yearChooser;
	private MonthCombo monthChooser;
	private SWTDayChooser dayChooser;
	private boolean settingYearMonth;

	public SWTCalendar(Composite parent)
	{
		this(parent, FLAT);
	}

	/**
	 * Constructs a calendar control.
	 * 
	 * @param parent a parent container.
	 * @param style FLAT to make the buttons flat, or NONE.
	 */
	public SWTCalendar(Composite parent, int style)
	{
		super(parent, (style & ~(FLAT | RED_WEEKEND)));

		createMainLayout();

		Composite header = createHeaderComposite(this);

		createPreviousMonthButton(header, style);
		createMonthYearPart(header);
		createNextMonthButton(header, style);
		createDayChooser(this, style);

		setTabList(new Control[] { header, dayChooser });
		setFont(parent.getFont());
	}

	private void createMonthYearPart(Composite parent)
	{
		Composite composite = createMonthYearPartComposite(parent);

		createMonthChooser(composite);
		createYearChooser(composite);

		parent.setTabList(new Control[] { composite });
	}

	private Composite createMonthYearPartComposite(Composite parent)
	{
		Composite composite = new Composite(parent, NONE);
		GridData layoutData = new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_CENTER);
		GridLayout layout = new GridLayout();

		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		composite.setLayout(layout);
		composite.setLayoutData(layoutData);

		return composite;
	}

	private Composite createHeaderComposite(Composite parent)
	{
		Composite header = new Composite(parent, NONE);
		GridLayout layout = new GridLayout();
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);

		layout.numColumns = 3;
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		header.setLayout(layout);
		header.setLayoutData(layoutData);

		return header;
	}

	private void createMainLayout()
	{
		GridLayout layout = new GridLayout();

		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;

		setLayout(layout);
	}

	private void createMonthChooser(Composite parent)
	{
		monthChooser = new MonthCombo(parent);

		monthChooser.setLayoutData(new GridData(GridData.FILL_VERTICAL));

		monthChooser.addSelectionListener(new SelectionAdapter()
		{
			@Override
            public void widgetSelected(SelectionEvent e)
			{
				if(!settingYearMonth)
				{
					dayChooser.setMonth(monthChooser.getMonth());
				}
			}
		});
	}

	private void createYearChooser(Composite composite)
	{
		yearChooser = new Spinner(composite, BORDER);

		yearChooser.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL));
		yearChooser.setMinimum(YEAR_MIN);
		yearChooser.setMaximum(YEAR_MAX);
		yearChooser.setIncrement(1);
		yearChooser.setPageIncrement(10);
		yearChooser.setSelection(Calendar.getInstance().get(YEAR));

		yearChooser.addListener(Verify, isValidYear);

		yearChooser.addSelectionListener(new SelectionAdapter()
		{
			@Override
            public void widgetSelected(SelectionEvent e)
			{
				if(!settingYearMonth)
				{
					dayChooser.setYear(yearChooser.getSelection());
				}
			}
		});
	}

	private void createNextMonthButton(Composite parent, int style)
	{
		createMonthButton(parent, style | RIGHT, setNextMonth);
	}

	private void createPreviousMonthButton(Composite parent, int style)
	{
		createMonthButton(parent, style | LEFT, setPreviousMonth);
	}

	private static RepeatingButton createMonthButton(Composite parent, int style, SelectionListener listener)
	{
		style &= FLAT | LEFT | RIGHT;

		RepeatingButton button = new RepeatingButton(parent, ARROW | CENTER | style);

		button.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL));
		button.addSelectionListener(listener);

		return button;
	}

	private void createDayChooser(Composite parent, int style)
	{
		dayChooser = new SWTDayChooser(parent, BORDER | (style & RED_WEEKEND));

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		dayChooser.setLayoutData(gridData);

		dayChooser.addSWTCalendarListener(new SWTCalendarListener()
		{
			public void dateChanged(SWTCalendarEvent event)
			{
				refreshYearMonth(event.getCalendar());
			}
		});
	}

	public void setCalendar(Calendar cal)
	{
		settingDate = true;
		try
		{
			refreshYearMonth(cal);
			dayChooser.setCalendar(cal);
		}
		finally
		{
			settingDate = false;
		}
	}

	private void refreshYearMonth(Calendar cal)
	{
		System.out.println("refreshYearMonth()");

		settingYearMonth = true;

		if(!yearChooser.isFocusControl())
			yearChooser.setSelection(cal.get(Calendar.YEAR));

		monthChooser.setMonth(cal.get(MONTH));
		settingYearMonth = false;
	}

	public void nextMonth()
	{
		Calendar cal = dayChooser.getCalendar();
		cal.add(MONTH, 1);
		refreshYearMonth(cal);
		dayChooser.setCalendar(cal);
	}

	public void previousMonth()
	{
		Calendar cal = dayChooser.getCalendar();
		cal.add(MONTH, -1);
		refreshYearMonth(cal);
		dayChooser.setCalendar(cal);
	}

	public Calendar getCalendar()
	{
		return dayChooser.getCalendar();
	}

	public void addSWTCalendarListener(SWTCalendarListener listener)
	{
		dayChooser.addSWTCalendarListener(listener);
	}

	public void removeSWTCalendarListener(SWTCalendarListener listener)
	{
		dayChooser.removeSWTCalendarListener(listener);
	}

	public void setLocale(Locale locale)
	{
		System.out.println("setLocale()");

		monthChooser.setLocale(locale);
		dayChooser.setLocale(locale);
		yearChooser.setSelection(getCalendar().get(Calendar.YEAR));
	}

	public boolean isSettingDate()
	{
		return settingDate;
	}

	/*
	 * Control
	 */
	@Override
	public void setFont(Font font)
	{
		super.setFont(font);

		monthChooser.setFont(font);
		yearChooser.setFont(font);
		dayChooser.setFont(font);
	}

	/*
	 * Listeners
	 */
	private Listener isValidYear = new Listener()
	{
		@Override
		public void handleEvent(Event e)
		{
			try
			{
				String year = replace(((Spinner)e.widget).getText(), e.text, e.start, e.end);

				e.doit = isEmpty(year) ? true : isYear(year);
			}
			catch(Exception ex)
			{
				e.doit = false;
			}
		}
	};

	private SelectionListener setNextMonth = new SelectionAdapter()
	{
		@Override
        public void widgetSelected(SelectionEvent e)
		{
			nextMonth();
		}
	};

	private SelectionListener setPreviousMonth = new SelectionAdapter()
	{
		@Override
        public void widgetSelected(SelectionEvent e)
		{
			previousMonth();
		}
	};
}
