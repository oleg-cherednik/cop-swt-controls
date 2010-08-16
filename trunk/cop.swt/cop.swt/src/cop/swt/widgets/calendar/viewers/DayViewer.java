package cop.swt.widgets.calendar.viewers;

import static cop.common.enums.TitleTypeEnum.TWO_CHARS;
import static cop.common.extensions.CalendarExtension.WEEK_DAYS_NUM;
import static cop.common.extensions.CalendarExtension.createCalendar;
import static cop.common.extensions.CalendarExtension.getWeekdaysTitles;
import static cop.common.extensions.CalendarExtension.isDateSame;
import static cop.common.extensions.CalendarExtension.isDay;
import static cop.common.extensions.CalendarExtension.isDaySame;
import static cop.common.extensions.CalendarExtension.isMonth;
import static cop.common.extensions.CalendarExtension.isMonthsSame;
import static cop.common.extensions.CalendarExtension.isYear;
import static cop.common.extensions.CollectionExtension.find;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.NumericExtension.isInRangeMinMax;
import static cop.swt.extensions.ColorExtension.DARK_RED;
import static cop.swt.widgets.calendar.viewers.templates.AbstractCalendarConfig.applyTemplate;
import static cop.swt.widgets.keys.enums.KeyEnum.parseKeyEnum;
import static java.lang.System.currentTimeMillis;
import static java.text.DateFormat.getDateInstance;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.WEEK_OF_YEAR;
import static java.util.Calendar.YEAR;
import static org.eclipse.swt.SWT.CENTER;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.HORIZONTAL;
import static org.eclipse.swt.SWT.TRAVERSE_ARROW_NEXT;
import static org.eclipse.swt.SWT.TRAVERSE_ARROW_PREVIOUS;
import static org.eclipse.swt.SWT.TRAVERSE_PAGE_NEXT;
import static org.eclipse.swt.SWT.TRAVERSE_PAGE_PREVIOUS;
import static org.eclipse.swt.SWT.TRAVERSE_TAB_NEXT;
import static org.eclipse.swt.SWT.TRAVERSE_TAB_PREVIOUS;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.widgets.FormToolkit;

import cop.swt.widgets.calendar.interfaces.IDayViewer;
import cop.swt.widgets.calendar.viewers.templates.CalendarConfigUnit;
import cop.swt.widgets.calendar.viewers.templates.DefaultCalendarConfig;
import cop.swt.widgets.calendar.viewers.templates.ICalendarConfig;
import cop.swt.widgets.interfaces.Clearable;
import cop.swt.widgets.interfaces.Refreshable;

public class DayViewer extends Composite implements IDayViewer, Refreshable, Clearable
{
	public static final String LABEL_TODAY = "Today";
	public static final Color COLOR_FOREGROUND_WEEKEND = DARK_RED;
	private static final int WEEKS_NUM = 6;

	private Label weekNumberSell;
	private Label todayLine;
	private DaySell todayMarkerExample;
	private WeekdayTitleSet titles;
	private WeekNumberSet numbers = new WeekNumberSet();
	private DaySet days = new DaySet();

	private Calendar firstShowDay;
	private Calendar date;
	private Locale locale;
	private Set<MouseListener> mouseListeners = new HashSet<MouseListener>();
	private Set<Listener> setTodayListeners = new HashSet<Listener>();
	private FormToolkit toolkit;
	private ICalendarConfig viewTemplate;

	public DayViewer(Composite parent, int style)
	{
		this(parent, style, Locale.getDefault(), null);
	}

	public DayViewer(Composite parent, int style, Locale locale, ICalendarConfig viewTemplate)
	{
		super(parent, style);

		Assert.isNotNull(locale);

		this.viewTemplate = isNotNull(viewTemplate) ? viewTemplate : new DefaultCalendarConfig();
		this.toolkit = new FormToolkit(getDisplay());
		this.date = Calendar.getInstance(locale);

		configMainComposite(parent);

		createTiltePart();
		createHorizontalSeparator();
		createDaysPart();
		createHorizontalSeparator();
		createTodayPart();

		setLocale(locale);
		addListeners();

		setFont(parent.getFont());
		setTabList(new Control[0]);
	}

	private void createHorizontalSeparator()
	{
		Label label = toolkit.createSeparator(this, HORIZONTAL);
		GridData layoutData = new GridData(FILL, CENTER, true, true);

		layoutData.horizontalSpan = WEEK_DAYS_NUM + 1;
		layoutData.verticalIndent = 1;

		label.setLayoutData(layoutData);
	}

	private void addListeners()
	{
		addMouseListener(notifyMouseMainDoubleClick);
		addMouseListener(setFocusOnMainMouseClick);
		addFocusListener(days.updateColorOnFocus);
		addTraverseListener(onTraversed);
		addKeyListener(keyPressed);
	}

	private void createDaysPart()
	{
		for(int i = 0; i < WEEKS_NUM; i++)
		{
			numbers.add();

			for(int j = 0; j < WEEK_DAYS_NUM; j++)
				days.add();
		}
	}

	private void createTodayPart()
	{
		createTodayMarker();
		createTodayLine();
	}

	private void createTodayMarker()
	{
		todayMarkerExample = new DaySell(this);

		todayMarkerExample.setBorderColor(viewTemplate.getTodayMarkerColor(this));
		todayMarkerExample.addMouseListener(setTodayDateOnMouseMainClick);
		todayMarkerExample.addMouseListener(notifyMouseMainDoubleClick);
	}

	private void createTodayLine()
	{
		todayLine = toolkit.createLabel(this, "", CENTER);

		// grid date
		GridData layoutData = new GridData(FILL, FILL, true, true);

		layoutData.horizontalSpan = WEEK_DAYS_NUM;
		layoutData.verticalAlignment = CENTER;

		todayLine.setLayoutData(layoutData);
		todayLine.addMouseListener(setTodayDateOnMouseMainClick);
		todayLine.addMouseListener(notifyMouseMainDoubleClick);
		todayLine.addMouseListener(setFocusOnMainMouseClick);

		applyTemplate(todayLine, viewTemplate.getTodayLinePart(this));
	}

	private Label createLabelSell(String text)
	{
		Label label = toolkit.createLabel(this, text, CENTER);

		label.setLayoutData(new GridData(FILL, CENTER, true, true));
		label.addMouseListener(notifyMouseMainDoubleClick);
		label.addMouseListener(setFocusOnMainMouseClick);

		return label;
	}

	private void createTiltePart()
	{
		weekNumberSell = createLabelSell(null);
		weekNumberSell.setBackground(viewTemplate.getWeekdayTitlePart(this).getBackground());

		titles = new WeekdayTitleSet();
	}

	private void updateTitlePart()
	{
		weekNumberSell.setText("");
		titles.refresh();
	}

	private void updateTodayPart()
	{
		DateFormat df = getDateInstance(DateFormat.SHORT, locale);
		Calendar day = Calendar.getInstance(locale);

		todayLine.setText(LABEL_TODAY + ": " + df.format(day.getTime()));
	}

	private void configMainComposite(Composite parent)
	{
		applyTemplate(this, viewTemplate.getBodyPart(parent));

		GridLayout gridLayout = new GridLayout();

		gridLayout.numColumns = WEEK_DAYS_NUM + 1;
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.horizontalSpacing = 1;
		gridLayout.verticalSpacing = 1;

		super.setLayout(gridLayout);
	}

	private void init()
	{
		date = Calendar.getInstance(locale);

		refresh();

		updateTitlePart();
		updateTodayPart();
	}

	private void changeMonth(int index)
	{
		date.set(DAY_OF_MONTH, index);

		refresh();
	}

	private void notifyMouseDoubleClickListener()
	{
		if(mouseListeners.isEmpty())
			return;

		Event event = createEvent();

		for(MouseListener listener : mouseListeners)
			listener.mouseDoubleClick(new MouseEvent(event));
	}

	private void notifySetTodayListener()
	{
		if(setTodayListeners.isEmpty())
			return;

		Event event = createEvent();

		for(Listener listener : setTodayListeners)
			listener.handleEvent(event);
	}

	private void notifyMouseDownListener()
	{
		if(mouseListeners.isEmpty())
			return;

		Event event = createEvent();

		for(MouseListener listener : mouseListeners)
			listener.mouseDown(new MouseEvent(event));
	}

	private Event createEvent()
	{
		Event event = new Event();

		event.widget = this;
		event.display = getDisplay();
		event.time = (int)currentTimeMillis();
		event.data = date.clone();

		return event;
	}

	public void setLocale(Locale locale)
	{
		this.locale = locale;
		init();
	}

	public void setDay(int day)
	{
		if(!isDay(day))
			return;

		date.set(DAY_OF_MONTH, day);
		refresh();
	}

	public void setMonth(int month)
	{
		if(!isMonth(month))
			return;

		date.set(MONTH, month);
		refresh();
	}

	public void setYear(int year)
	{
		if(!isYear(year))
			return;

		date.set(YEAR, year);
		refresh();
	}

	public void addSetTodayListener(Listener listener)
	{
		setTodayListeners.add(listener);
	}

	public void removeSetTodayListener(Listener listener)
	{
		setTodayListeners.remove(listener);
	}

	/*
	 * Control
	 */

	@Override
	public void addMouseListener(MouseListener listener)
	{
		mouseListeners.add(listener);
	}

	@Override
	public void removeMouseListener(MouseListener listener)
	{
		mouseListeners.remove(listener);
	}

	@Override
	public boolean isFocusControl()
	{
		return days.focusControl;
	}

	/*
	 * Composite
	 */

	@Override
	@Deprecated
	public void setLayout(Layout layout)
	{}

	/*
	 * IDayViewer
	 */

	@Override
	public void setDate(Calendar date)
	{
		if(isNull(date))
			return;

		this.date = createCalendar(date, locale);
		refresh();
	}

	@Override
	public Calendar getDate()
	{
		return (Calendar)date.clone();
	}

	/*
	 * Refreshable
	 */

	@Override
	public void refresh()
	{
		date.get(DAY_OF_YEAR); // Force calendar update
		firstShowDay = days.getFirstShowDate();

		numbers.refresh();
		days.refresh();
	}

	/*
	 * Clearable
	 */

	@Override
	public void clear()
	{
		date.clear();
		refresh();
	}

	/*
	 * Listener
	 */

	private MouseListener setTodayDateOnMouseMainClick = new MouseAdapter()
	{
		@Override
		public void mouseDown(MouseEvent e)
		{
			setDate(Calendar.getInstance());
			notifySetTodayListener();
		}
	};

	private MouseListener notifyMouseMainDoubleClick = new MouseAdapter()
	{
		@Override
		public void mouseDoubleClick(MouseEvent e)
		{
			if(e.button != 1 || !(e.widget instanceof DaySell))
				return;

			if(!isDateSame(((DaySell)e.widget).getDate(), date))
				return;

			notifyMouseDoubleClickListener();
		}
	};

	private MouseListener setFocusOnMainMouseClick = new MouseAdapter()
	{
		@Override
		public void mouseDown(MouseEvent e)
		{
			if(e.button != 1)
				return;

			setFocus();

			if(e.widget instanceof DaySell)
				days.selectDay(days.findDay((DaySell)e.widget) + 1 + days.dayOffset);
		}
	};

	private TraverseListener onTraversed = new TraverseListener()
	{
		@Override
		public void keyTraversed(TraverseEvent e)
		{
			switch(e.detail)
			{
			case TRAVERSE_ARROW_PREVIOUS:
			case TRAVERSE_ARROW_NEXT:
			case TRAVERSE_PAGE_PREVIOUS:
			case TRAVERSE_PAGE_NEXT:
				e.doit = false;
				break;
			case TRAVERSE_TAB_NEXT:
			case TRAVERSE_TAB_PREVIOUS:
				e.doit = true;
			}
		}
	};

	private KeyListener keyPressed = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch(parseKeyEnum(e.keyCode))
			{
			case KEY_LEFT:
				days.selectDay(date.get(DAY_OF_MONTH) - 1);
				break;
			case KEY_RIGHT:
				days.selectDay(date.get(DAY_OF_MONTH) + 1);
				break;
			case KEY_UP:
				days.selectDay(date.get(DAY_OF_MONTH) - 7);
				break;
			case KEY_DOWN:
				days.selectDay(date.get(DAY_OF_MONTH) + 7);
				break;
			case KEY_PAGE_UP:
				setMonth(date.get(MONTH) - 1);
				break;
			case KEY_PAGE_DOWN:
				setMonth(date.get(MONTH) + 1);
				break;
			default:
				break;
			}
		}
	};

	/*
	 * Classes
	 */

	private class WeekdayTitleSet implements Refreshable
	{
		private Label[] titles;

		public WeekdayTitleSet()
		{
			titles = new Label[WEEK_DAYS_NUM];

			for(int i = 0; i < WEEK_DAYS_NUM; i++)
			{
				titles[i] = createLabelSell(null);
				applyTemplate(titles[i], viewTemplate.getWeekdayTitlePart(DayViewer.this));
			}
		}

		@Override
		public void refresh()
		{
			String[] weekdays = getWeekdaysTitles(TWO_CHARS, locale);
			int firstWeekday = date.getFirstDayOfWeek();

			for(Label title : titles)
			{
				if(firstWeekday > WEEK_DAYS_NUM)
					firstWeekday -= WEEK_DAYS_NUM;

				title.setText(weekdays[firstWeekday]);

				if(firstWeekday == SUNDAY)
					applyTemplate(title, viewTemplate.getSundayPart(titles[3]));
				else if(firstWeekday == SATURDAY)
					applyTemplate(title, viewTemplate.getSaturdayPart(titles[3]));
				else
					applyTemplate(title, viewTemplate.getWeekdayTitlePart(titles[3]));

				firstWeekday++;
			}
		}
	}

	private class WeekNumberSet implements Refreshable
	{
		private Label[] numbers = new Label[WEEKS_NUM];
		private int size;

		public void add()
		{
			if(size < numbers.length)
				numbers[size++] = createLabelSell(null);
		}

		@Override
		public void refresh()
		{
			Calendar tmp = (Calendar)firstShowDay.clone();

			for(Label number : numbers)
			{
				number.setText("" + tmp.get(WEEK_OF_YEAR));
				tmp.add(DAY_OF_MONTH, WEEK_DAYS_NUM);
				applyTemplate(number, viewTemplate.getWeekNumberPart(DayViewer.this));
			}
		}
	}

	private class DaySet implements Refreshable
	{
		private int dayOffset;
		private DaySell[] days = new DaySell[WEEKS_NUM * WEEK_DAYS_NUM];
		private int size;
		private boolean focusControl;

		public void add()
		{
			if(size == days.length)
				return;

			days[size] = new DaySell(DayViewer.this);
			days[size].addMouseListener(notifyMouseMainDoubleClick);
			days[size].addMouseListener(setFocusOnMainMouseClick);

			size++;
		}

		@Override
		public void refresh()
		{
			Calendar tmp = (Calendar)firstShowDay.clone();
			Calendar today = Calendar.getInstance(locale);

			for(DaySell day : days)
			{
				day.setDate(tmp);
				CalendarConfigUnit parentTemplate = viewTemplate.getBodyPart(DayViewer.this);

				if(isDateSame(today, tmp))
					day.setBorderColor(viewTemplate.getTodayMarkerColor(parentTemplate));
				else
					day.setBorderColor(getBackground());

				checkSameMonth(tmp, day);
				tmp.add(DAY_OF_MONTH, 1);
			}
		}

		private void checkSameMonth(Calendar day, DaySell daySell)
		{
			Assert.isNotNull(day);
			Assert.isNotNull(daySell);

			if(isMonthsSame(day, date))
			{
				int dayOfWeek = day.get(DAY_OF_WEEK);
				CalendarConfigUnit parentTemplate = viewTemplate.getWeekdaySameMonthPart(DayViewer.this);

				if(dayOfWeek == SUNDAY)
					applyTemplate(daySell, viewTemplate.getSundayPart(parentTemplate));
				else if(dayOfWeek == SATURDAY)
					applyTemplate(daySell, viewTemplate.getSaturdayPart(parentTemplate));
				else
					applyTemplate(daySell, viewTemplate.getWeekdaySameMonthPart(parentTemplate));

				checkSameDate(day, daySell);
			}
			else
				applyTemplate(daySell, viewTemplate.getWeekdayOtherMonthPart(viewTemplate.getBodyPart(DayViewer.this)));
		}

		private void checkSameDate(Calendar day, DaySell daySell)
		{
			Assert.isNotNull(day);
			Assert.isNotNull(daySell);

			if(isDaySame(day, date))
			{
				CalendarConfigUnit parentTemplate = viewTemplate.getWeekdaySameMonthPart(DayViewer.this);

				if(focusControl)
					applyTemplate(daySell, viewTemplate.getWeekdayDayActivePart(parentTemplate));
				else
					applyTemplate(daySell, viewTemplate.getWeekdayDayInactivePart(parentTemplate));
			}
		}

		public DaySell getSelectedDayControl()
		{
			return days[date.get(DAY_OF_MONTH) - 1 - dayOffset];
		}

		private int findDay(DaySell daySell)
		{
			return find(days, daySell);
		}

		private Calendar getFirstShowDate()
		{
			Calendar day = (Calendar)date.clone();

			day.setMinimalDaysInFirstWeek(7);
			day.set(DAY_OF_MONTH, 1);
			updateDayOffset(day);
			day.add(DAY_OF_MONTH, dayOffset);

			return day;
		}

		private void updateDayOffset(Calendar day)
		{
			int firstWeekday = day.getFirstDayOfWeek();
			int currentWeekday = day.get(DAY_OF_WEEK);

			dayOffset = firstWeekday - currentWeekday;

			if(dayOffset >= 0)
				dayOffset -= 7;
		}

		private void selectDay(int selectedIndex)
		{
			date.get(DAY_OF_YEAR); // Force calendar update

			int minimum = date.getActualMinimum(DAY_OF_MONTH);
			int maximum = date.getActualMaximum(DAY_OF_MONTH);

			if(isInRangeMinMax(selectedIndex, minimum, maximum))
			{
				int dayOfWeek = date.get(DAY_OF_WEEK);
				DaySell selectedDay = getSelectedDayControl();
				CalendarConfigUnit parentTemplate = viewTemplate.getWeekdaySameMonthPart(DayViewer.this);

				if(dayOfWeek == SUNDAY)
					applyTemplate(selectedDay, viewTemplate.getSundayPart(parentTemplate));
				else if(dayOfWeek == SATURDAY)
					applyTemplate(selectedDay, viewTemplate.getSaturdayPart(parentTemplate));
				else
					applyTemplate(selectedDay, viewTemplate.getWeekdaySameMonthPart(parentTemplate));

				date.set(DAY_OF_MONTH, selectedIndex);
				selectedDay = getSelectedDayControl();

				applyTemplate(selectedDay, viewTemplate.getWeekdayDayActivePart(parentTemplate));
			}
			else
				changeMonth(selectedIndex);

			notifyMouseDownListener();
		}

		/*
		 * Listener
		 */

		private FocusListener updateColorOnFocus = new FocusListener()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				focusControl = false;
				DaySell selectedDay = getSelectedDayControl();
				CalendarConfigUnit parentTemplate = viewTemplate.getWeekdaySameMonthPart(DayViewer.this);

				applyTemplate(selectedDay, viewTemplate.getWeekdayDayInactivePart(parentTemplate));
			}

			@Override
			public void focusGained(FocusEvent e)
			{
				focusControl = true;
				DaySell selectedDay = getSelectedDayControl();
				CalendarConfigUnit parentTemplate = viewTemplate.getWeekdaySameMonthPart(DayViewer.this);

				applyTemplate(selectedDay, viewTemplate.getWeekdayDayActivePart(parentTemplate));
			}
		};
	}
}
