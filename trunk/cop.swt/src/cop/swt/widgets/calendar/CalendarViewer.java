package cop.swt.widgets.calendar;

import static cop.extensions.CalendarExt.isYear;
import static cop.extensions.CommonExt.isNotNull;
import static cop.extensions.StringExt.isEmpty;
import static cop.extensions.StringExt.replace;
import static cop.swt.widgets.calendar.viewers.templates.AbstractCalendarConfig.applyTemplate;
import static java.lang.System.currentTimeMillis;
import static java.util.Calendar.MONTH;
import static org.eclipse.swt.SWT.ARROW;
import static org.eclipse.swt.SWT.BORDER;
import static org.eclipse.swt.SWT.CENTER;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.FLAT;
import static org.eclipse.swt.SWT.LEFT;
import static org.eclipse.swt.SWT.MouseWheel;
import static org.eclipse.swt.SWT.NONE;
import static org.eclipse.swt.SWT.RIGHT;
import static org.eclipse.swt.SWT.Verify;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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
import org.eclipse.ui.forms.widgets.FormToolkit;

import cop.i18.LocaleSupport;
import cop.swt.widgets.MultiTouchButton;
import cop.swt.widgets.calendar.interfaces.IDateViewer;
import cop.swt.widgets.calendar.viewers.DayViewer;
import cop.swt.widgets.calendar.viewers.MonthComboViewer;
import cop.swt.widgets.calendar.viewers.YearSpinnerViewer;
import cop.swt.widgets.calendar.viewers.templates.DefaultCalendarConfig;
import cop.swt.widgets.calendar.viewers.templates.ICalendarConfig;
import cop.swt.widgets.interfaces.Clearable;
import cop.swt.widgets.interfaces.Refreshable;

public class CalendarViewer extends Composite implements IDateViewer, Refreshable, Clearable, LocaleSupport
{
	private YearSpinnerViewer yearViewer;
	private MonthComboViewer monthViewer;
	private DayViewer dayViewer;
	private boolean monthYearListenersLock;
	private FormToolkit toolkit;
	private ICalendarConfig viewTemplate;
	private List<ModifyListener> modifyListeners = new ArrayList<ModifyListener>();

	public CalendarViewer(Composite parent)
	{
		this(parent, FLAT, Locale.getDefault(), null);
	}

	public CalendarViewer(Composite parent, int style)
	{
		this(parent, style, Locale.getDefault(), null);
	}

	public CalendarViewer(Composite parent, int style, Locale locale)
	{
		this(parent, style, locale, null);
	}

	public CalendarViewer(Composite parent, int style, Locale locale, ICalendarConfig viewTemplate)
	{
		super(parent, style & ~FLAT);
		
		Assert.isNotNull(locale);
		
		this.viewTemplate = isNotNull(viewTemplate) ? viewTemplate : new DefaultCalendarConfig();
		this.toolkit = new FormToolkit(getDisplay());

		configMainComposite(parent);

		Composite header = createHeaderComposite();

		createPreviousMonthButton(header, style);
		createMonthViewer(header, locale);
		createYearViewer(header);
		createNextMonthButton(header, style);
		createDayViewer(style, locale);

		setTabList(new Control[] { header, dayViewer });
		setDate(Calendar.getInstance(locale));
	}

	private void configMainComposite(Composite parent)
	{
		applyTemplate(this, viewTemplate.getBodyPart(parent));
		configMainLayout();
	}

	private Composite createHeaderComposite()
	{
		Composite header = toolkit.createComposite(this, NONE);
		GridLayout layout = new GridLayout();

		layout.numColumns = 4;
		layout.marginWidth = 2;
		layout.marginHeight = 2;

		header.setLayout(layout);
		header.setLayoutData(new GridData(FILL, FILL, true, true));
		header.setBackground(viewTemplate.getHeaderPart(this).getBackground());

		return header;
	}

	private void configMainLayout()
	{
		GridLayout layout = new GridLayout();

		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;

		setLayout(layout);
	}

	private void createMonthViewer(Composite parent, Locale locale)
	{
		monthViewer = new MonthComboViewer(parent, locale);

		monthViewer.setLayoutData(new GridData(CENTER, FILL, true, true));
		monthViewer.setVisibleItemCount(12);
		monthViewer.addModifyListener(onMonthChange);

		applyTemplate(monthViewer, viewTemplate.getMonthYearPart(viewTemplate.getBodyPart(parent)));
	}

	private void createYearViewer(Composite parent)
	{
		yearViewer = new YearSpinnerViewer(parent);

		yearViewer.setLayoutData(new GridData(CENTER, FILL, true, true));
		yearViewer.addListener(Verify, isValidYear);
		yearViewer.addModifyListener(onYearChange);
		yearViewer.addListener(MouseWheel, changeValueOnMouseWheel);

		applyTemplate(yearViewer, viewTemplate.getMonthYearPart(viewTemplate.getBodyPart(parent)));
	}

	private Listener changeValueOnMouseWheel = new Listener()
	{
		@Override
		public void handleEvent(Event event)
		{
			if(event.type != MouseWheel || event.count == 0)
				return;

			if(event.count > 0)
				yearViewer.setYear(yearViewer.getYear() + 1);
			else
				yearViewer.setYear(yearViewer.getYear() - 1);
		}
	};

	public void addModifyListener(ModifyListener listener)
	{
		modifyListeners.add(listener);
	}

	public void removeModifyListener(ModifyListener listener)
	{
		modifyListeners.remove(listener);
	}

	private void createNextMonthButton(Composite parent, int style)
	{
		createMonthButton(parent, (style & FLAT) | RIGHT, setNextMonth);
	}

	private void createPreviousMonthButton(Composite parent, int style)
	{
		createMonthButton(parent, (style & FLAT) | LEFT, setPreviousMonth);
	}

	private static MultiTouchButton createMonthButton(Composite parent, int style, SelectionListener listener)
	{
		MultiTouchButton button = new MultiTouchButton(parent, style & (FLAT | LEFT | RIGHT) | ARROW);

		button.setLayoutData(new GridData(FILL, FILL, true, true));
		button.addSelectionListener(listener);

		return button;
	}

	private void createDayViewer(int style, Locale locale)
	{
		dayViewer = new DayViewer(this, style & ~BORDER, locale, viewTemplate);

		GridData gridData = new GridData(CENTER, CENTER, true, true);

		dayViewer.setLayoutData(gridData);
		dayViewer.addMouseListener(onSelectDayChangeMonthYear);
		dayViewer.addSetTodayListener(setToday);
	}

	private void updateYearMonth(Calendar date)
	{
		monthYearListenersLock = true;

		if(!yearViewer.isFocusControl())
			yearViewer.setYear(date);

		monthViewer.setMonth(date);

		monthYearListenersLock = false;
	}

	private void notifyModifyListener()
	{
		if(modifyListeners.isEmpty())
			return;

		Event event = createEvent();

		for(ModifyListener listener : modifyListeners)
			listener.modifyText(new ModifyEvent(event));
	}

	private Event createEvent()
	{
		Event event = new Event();

		event.widget = this;
		event.display = getDisplay();
		event.time = (int)currentTimeMillis();
		event.data = dayViewer.getDate();

		return event;
	}

	public void nextMonth()
	{
		incMonth(1);
	}

	public void setPreviousMonth()
	{
		incMonth(-1);
	}

	private void incMonth(int val)
	{
		Calendar date = dayViewer.getDate();

		date.add(MONTH, val);
		setDate(date);
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		monthYearListenersLock = true;
		monthViewer.setLocale(locale);
		dayViewer.setLocale(locale);
		monthYearListenersLock = false;
	}

	/*
	 * Clearable
	 */

	@Override
	public void clear()
	{
		monthViewer.clear();
		yearViewer.clear();
		dayViewer.clear();
	}

	/*
	 * Refreshable
	 */

	@Override
	public void refresh()
	{
		monthViewer.refresh();
		yearViewer.refresh();
		dayViewer.refresh();
	}

	/*
	 * Control
	 */

	@Override
	@Deprecated
	public void setFont(Font font)
	{}

	@Override
	public void addMouseListener(MouseListener listener)
	{
		dayViewer.addMouseListener(listener);
	}

	@Override
	public void removeMouseListener(MouseListener listener)
	{
		dayViewer.removeMouseListener(listener);
	}

	@Override
	public void addKeyListener(KeyListener listener)
	{
		yearViewer.addKeyListener(listener);
		monthViewer.addKeyListener(listener);
		dayViewer.addKeyListener(listener);
	}

	@Override
	public void removeKeyListener(KeyListener listener)
	{
		yearViewer.removeKeyListener(listener);
		monthViewer.removeKeyListener(listener);
		dayViewer.removeKeyListener(listener);
	}

	/*
	 * IDateViewer
	 */

	@Override
	public int getMonth()
	{
		return monthViewer.getMonth();
	}

	@Override
	public int getYear()
	{
		return yearViewer.getYear();
	}

	@Override
	public Calendar getDate()
	{
		return dayViewer.getDate();
	}

	@Override
	public void setMonth(Calendar date)
	{
		monthViewer.setMonth(date);
	}

	@Override
	public void setYear(Calendar date)
	{
		yearViewer.setYear(date);

	}

	@Override
	public void setMonth(int month)
	{
		monthViewer.setMonth(month);
	}

	@Override
	public void setYear(int year)
	{
		yearViewer.setYear(year);
	}

	@Override
	public void setDate(Calendar date)
	{
		monthYearListenersLock = true;

		monthViewer.setMonth(date);
		yearViewer.setYear(date);
		dayViewer.setDate(date);

		monthYearListenersLock = false;
	}

	/*
	 * Listeners
	 */

	private Listener setToday = new Listener()
	{
		@Override
		public void handleEvent(Event event)
		{
			setDate(Calendar.getInstance());
		}
	};

	private MouseListener onSelectDayChangeMonthYear = new MouseAdapter()
	{
		@Override
		public void mouseDown(MouseEvent e)
		{
			if(e.data instanceof Calendar)
				updateYearMonth((Calendar)e.data);
		}
	};

	private ModifyListener onMonthChange = new ModifyListener()
	{
		@Override
		public void modifyText(ModifyEvent e)
		{
			if(monthYearListenersLock)
				return;

			dayViewer.setMonth(monthViewer.getMonth());
			monthYearListenersLock = false;
			notifyModifyListener();
		}
	};

	private ModifyListener onYearChange = new ModifyListener()
	{
		@Override
		public void modifyText(ModifyEvent e)
		{
			if(monthYearListenersLock)
				return;

			dayViewer.setYear(yearViewer.getYear());
			monthYearListenersLock = false;
			notifyModifyListener();
		}
	};

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
			setPreviousMonth();
		}
	};
}
