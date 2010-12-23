package swtcalendar.org.vafada.swtcalendar;

import static cop.common.extensions.BitExtension.isBitSet;
import static cop.swt.extensions.ColorExtension.BLACK;
import static cop.swt.extensions.ColorExtension.DARK_GRAY;
import static cop.swt.extensions.ColorExtension.DARK_RED;
import static cop.swt.extensions.ColorExtension.GRAY;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.swt.widgets.keys.enums.KeyEnum.parseKeyEnum;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import static org.eclipse.swt.SWT.COLOR_LIST_SELECTION;
import static org.eclipse.swt.SWT.COLOR_LIST_SELECTION_TEXT;
import static org.eclipse.swt.SWT.COLOR_WHITE;
import static org.eclipse.swt.SWT.HORIZONTAL;
import static org.eclipse.swt.SWT.NO_FOCUS;
import static org.eclipse.swt.SWT.SEPARATOR;
import static org.eclipse.swt.SWT.TRAVERSE_ARROW_NEXT;
import static org.eclipse.swt.SWT.TRAVERSE_ARROW_PREVIOUS;
import static org.eclipse.swt.SWT.TRAVERSE_PAGE_NEXT;
import static org.eclipse.swt.SWT.TRAVERSE_PAGE_PREVIOUS;
import static org.eclipse.swt.SWT.TRAVERSE_TAB_NEXT;
import static org.eclipse.swt.SWT.TRAVERSE_TAB_PREVIOUS;
import static org.eclipse.swt.layout.GridData.FILL_HORIZONTAL;
import static org.eclipse.swt.layout.GridData.HORIZONTAL_ALIGN_FILL;
import static org.eclipse.swt.layout.GridData.VERTICAL_ALIGN_FILL;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;

public class SWTDayChooser extends Composite
{
	/**
	 * Style constant for making Sundays red.
	 */
	public static final int RED_SUNDAY = 1 << 24; // == SWT.EMBEDDED
	/**
	 * Style constant for making Saturdays red.
	 */
	public static final int RED_SATURDAY = 1 << 28; // == SWT.VIRTUAL
	/**
	 * Style constant for making weekends red.
	 */
	public static final int RED_WEEKEND = RED_SATURDAY | RED_SUNDAY;

	private Label[] dayTitles;
	private DayControl[] days;
	private int dayOffset;
	private Color activeSelectionBackground;
	private Color inactiveSelectionBackground = GRAY;
	private Color otherMonthColor = DARK_GRAY;
	private Color activeSelectionForeground;
	private Color inactiveSelectionForeground;
	private Calendar calendar;
	private Calendar today;
	private Locale locale;
	private List<SWTEventListener> listeners = new ArrayList<SWTEventListener>(3);
	private int style;

	{
		activeSelectionBackground = getDisplay().getSystemColor(COLOR_LIST_SELECTION);
		activeSelectionForeground = getDisplay().getSystemColor(COLOR_LIST_SELECTION_TEXT);
	}

	public SWTDayChooser(Composite parent, int style)
	{
		this(parent, style, Locale.getDefault());
	}

	public SWTDayChooser(Composite parent, int style, Locale locale)
	{
		super(parent, style & ~RED_WEEKEND);

		this.style = style;
		this.locale = locale;
		this.inactiveSelectionForeground = getForeground();

		setBackground(getDisplay().getSystemColor(COLOR_WHITE));
		createMainLayout();
		createWeekDaysLabel();

		//		Label sep = toolkit.createSeparator(this, SWT.HORIZONTAL);
		//		GridData greed = new GridData(GridData.FILL_HORIZONTAL);

		//		greed.horizontalSpan = 3;
		//		sep.setLayoutData(greed);

		{
			final Composite spacer = new Composite(this, NO_FOCUS);
			spacer.setBackground(getBackground());
			final GridData gridData = new GridData(FILL_HORIZONTAL);
			gridData.heightHint = 2;
			gridData.horizontalSpan = 7;
			spacer.setLayoutData(gridData);
			spacer.setLayout(new GridLayout());
			spacer.addMouseListener(selectDayOnMouseDown);
		}

		{
			final Label label = new Label(this, HORIZONTAL | SEPARATOR);
			final GridData gridData = new GridData(FILL_HORIZONTAL);
			gridData.horizontalSpan = 7;
			label.setLayoutData(gridData);
		}

		createDaysControl();
		setTabList(new Control[0]);
		setFont(parent.getFont());

		init();

		addListeners();
	}

	private void addListeners()
	{
		addDisposeListener(onDisposeListener);
		addMouseListener(selectDayOnMouseDown);
		addFocusListener(updateColorOnFocus);
		addTraverseListener(onTraversed);
		addKeyListener(keyPressed);
	}

	private void createDaysControl()
	{
		days = new DayControl[42];

		for(int i = 0; i < 42; i++)
		{
			DayControl day = new DayControl(this);

			day.setLayoutData(new GridData(HORIZONTAL_ALIGN_FILL | VERTICAL_ALIGN_FILL));
			day.addMouseListener(selectDayOnMouseDown);

			days[i] = day;
		}
	}

	private void createWeekDaysLabel()
	{
		dayTitles = new Label[7];

		for(int i = 0; i < dayTitles.length; i++)
		{
			Label label = new Label(this, SWT.CENTER);

			dayTitles[i] = label;

			label.setLayoutData(new GridData(HORIZONTAL_ALIGN_FILL));
			label.addMouseListener(selectDayOnMouseDown);
		}
	}

	private void createMainLayout()
	{
		GridLayout gridLayout = new GridLayout();

		gridLayout.makeColumnsEqualWidth = true;
		gridLayout.numColumns = 7;
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;

		setLayout(gridLayout);
	}

	protected void init()
	{
		calendar = Calendar.getInstance(locale);
		calendar.setLenient(true);
		today = (Calendar)calendar.clone();
		int firstDayOfWeek = calendar.getFirstDayOfWeek();
		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
		String[] dayNames = dateFormatSymbols.getShortWeekdays();
		int minLength = Integer.MAX_VALUE;
		for(int i = 0; i < dayNames.length; i++)
		{
			int len = dayNames[i].length();
			if(len > 0 && len < minLength)
			{
				minLength = len;
			}
		}
		if(minLength > 2)
		{
			for(int i = 0; i < dayNames.length; i++)
			{
				if(dayNames[i].length() > 0)
				{
					// as suggested by yunjie liu, Because in Chinese the dayNames display as *** ,but only the third
					// word are the keywords.
					if(locale.equals(Locale.CHINA))
					{
						if(dayNames[i].length() > 2)
						{
							dayNames[i] = dayNames[i].substring(2, 3);
						}
					}
					else
					{
						if(dayNames[i].length() > 0)
						{
							dayNames[i] = dayNames[i].substring(0, 1);
						}
					}
				}
			}
		}

		int d = firstDayOfWeek;
		for(int i = 0; i < dayTitles.length; i++)
		{
			Label label = dayTitles[i];
			label.setText(dayNames[d]);
			label.setBackground(getBackground());
			if(d == Calendar.SUNDAY && (style & RED_SUNDAY) != 0 || d == Calendar.SATURDAY
			                && (style & RED_SATURDAY) != 0)
			{
				label.setForeground(getDisplay().getSystemColor(SWT.COLOR_DARK_RED));
			}
			else
			{
				label.setForeground(getForeground());
			}

			d++;
			if(d > dayTitles.length)
			{
				d -= dayTitles.length;
			}
		}

		drawDays();
	}

	private void drawDays()
	{
		calendar.get(DAY_OF_YEAR); // Force calendar update

		Calendar day = (Calendar)calendar.clone();

		day.set(DAY_OF_MONTH, 1);
		updateDayOffset(day);
		day.add(DAY_OF_MONTH, dayOffset);

		for(int i = 0, size = days.length; i < size; day.add(DAY_OF_MONTH, 1))
		{
			DayControl dayControl = days[i++];

			dayControl.setValue(day.get(DAY_OF_MONTH));
			dayControl.setBorderColor(isSameDay(day, today) ? BLACK : getBackground());

			checkSameMonth(day, dayControl);
			checkSameDate(day, dayControl);
		}
	}

	private void updateDayOffset(Calendar day)
	{
		dayOffset = day.getFirstDayOfWeek() - day.get(DAY_OF_WEEK);

		if(dayOffset >= 0)
			dayOffset -= 7;
	}

	private void checkSameMonth(Calendar day, DayControl dayControl)
	{
		Assert.isNotNull(day);

		if(isSameMonth(day, calendar))
		{
			int dayOfWeek = day.get(DAY_OF_WEEK);

			if(dayOfWeek == SUNDAY && isBitSet(style, RED_SUNDAY))
				dayControl.setForeground(DARK_RED);
			else if(dayOfWeek == SATURDAY && isBitSet(style, RED_SATURDAY))
				dayControl.setForeground(DARK_RED);
			else
				dayControl.setForeground(getForeground());
		}
		else
			dayControl.setForeground(otherMonthColor);
	}

	private void checkSameDate(Calendar day, DayControl dayControl)
	{
		if(isSameDay(day, calendar))
		{
			dayControl.setBackground(getSelectionBackgroundColor());
			dayControl.setForeground(getSelectionForegroundColor());
		}
		else
			dayControl.setBackground(getBackground());
	}

	private static boolean isSameDay(Calendar cal1, Calendar cal2)
	{
		return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
		                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	}

	private static boolean isSameMonth(Calendar cal1, Calendar cal2)
	{
		return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
		                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	}

	public void setMonth(int month)
	{
		calendar.set(Calendar.MONTH, month);
		drawDays();
		dateChanged();
	}

	public void setYear(int year)
	{
		calendar.set(Calendar.YEAR, year);
		drawDays();
		dateChanged();
	}

	public void setCalendar(Calendar cal)
	{
		calendar = (Calendar)cal.clone();
		calendar.setLenient(true);
		drawDays();
		dateChanged();
	}

	private MouseListener selectDayOnMouseDown = new MouseAdapter()
	{
		@Override
		public void mouseDown(MouseEvent e)
		{
			if(e.button != 1)
				return;

			setFocus();

			if(e.widget instanceof DayControl)
				selectDay(findDay(e.widget) + 1 + dayOffset);
		}
	};

	/**
	 * Finds position of a control in <code>days</code> array.
	 * 
	 * @param dayControl a control to find.
	 * @return an index of <code>dayControl</code> in <code>days</code> array, or -1 if not found.
	 */
	private int findDay(Widget dayControl)
	{
		for(int i = 0; i < days.length; i++)
		{
			if(days[i] == dayControl)
			{
				return i;
			}
		}

		return -1;
	}

	private void selectDay(int day)
	{
		calendar.get(Calendar.DAY_OF_YEAR); // Force calendar update
		if(day >= calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
		                && day <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
		{
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			// Stay on the same month.
			DayControl selectedDay = getSelectedDayControl();
			selectedDay.setBackground(getBackground());
			if(dayOfWeek == Calendar.SUNDAY)
			{
				selectedDay.setForeground(getDisplay().getSystemColor(SWT.COLOR_DARK_RED));
			}
			else
			{
				selectedDay.setForeground(getForeground());
			}

			calendar.set(Calendar.DAY_OF_MONTH, day);

			selectedDay = getSelectedDayControl();
			selectedDay.setBackground(getSelectionBackgroundColor());
			selectedDay.setForeground(getSelectionForegroundColor());

		}
		else
		{
			// Move to a different month.
			calendar.set(Calendar.DAY_OF_MONTH, day);
			drawDays();
		}

		dateChanged();
	}

	private DayControl getSelectedDayControl()
	{
		return days[calendar.get(Calendar.DAY_OF_MONTH) - 1 - dayOffset];
	}

	private Color getSelectionBackgroundColor()
	{
		return isFocusControl() ? activeSelectionBackground : inactiveSelectionBackground;
	}

	private Color getSelectionForegroundColor()
	{
		return isFocusControl() ? activeSelectionForeground : inactiveSelectionForeground;
	}

	@Override
	public boolean isFocusControl()
	{
		for(Control control = getDisplay().getFocusControl(); control != null; control = control.getParent())
		{
			if(control == this)
			{
				return true;
			}
		}

		return false;
	}

	public void addSWTCalendarListener(SWTCalendarListener listener)
	{
		this.listeners.add(listener);
	}

	public void removeSWTCalendarListener(SWTCalendarListener listener)
	{
		this.listeners.remove(listener);
	}

	private void dateChanged()
	{
		if(listeners.isEmpty())
			return;

		SWTCalendarListener[] listenersArray = new SWTCalendarListener[listeners.size()];

		listeners.toArray(listenersArray);

		for(int i = 0; i < listenersArray.length; i++)
		{
			Event event = new Event();
			event.widget = this;
			event.display = getDisplay();
			event.time = (int)System.currentTimeMillis();
			event.data = calendar.clone();
			listenersArray[i].dateChanged(new SWTCalendarEvent(event));
		}
	}

	public Calendar getCalendar()
	{
		return (Calendar)calendar.clone();
	}

	public void setLocale(Locale locale)
	{
		this.locale = locale;
		init();
	}

	/*
	 * Control
	 */

	@Override
	public void setFont(Font font)
	{
		super.setFont(font);

		for(int i = 0; i < dayTitles.length; i++)
		{
			dayTitles[i].setFont(font);
		}

		for(int i = 0; i < days.length; i++)
		{
			days[i].setFont(font);
		}
	}

	/*
	 * Widget
	 */

	@Override
	public void dispose()
	{
		if(isNotNull(activeSelectionBackground))
			activeSelectionBackground.dispose();

		if(isNotNull(activeSelectionForeground))
			activeSelectionForeground.dispose();

		super.dispose();
	}

	/*
	 * Listener
	 */

	private DisposeListener onDisposeListener = new DisposeListener()
	{
		public void widgetDisposed(DisposeEvent event)
		{
			otherMonthColor.dispose();
		}
	};

	private FocusListener updateColorOnFocus = new FocusListener()
	{
		@Override
		public void focusLost(FocusEvent e)
		{
			DayControl selectedDay = getSelectedDayControl();

			selectedDay.setBackground(getSelectionBackgroundColor());
			selectedDay.setForeground(getSelectionForegroundColor());
		}

		@Override
		public void focusGained(FocusEvent e)
		{
			DayControl selectedDay = getSelectedDayControl();

			selectedDay.setBackground(getSelectionBackgroundColor());
			selectedDay.setForeground(getSelectionForegroundColor());
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
				selectDay(calendar.get(DAY_OF_MONTH) - 1);
				break;
			case KEY_RIGHT:
				selectDay(calendar.get(DAY_OF_MONTH) + 1);
				break;
			case KEY_UP:
				selectDay(calendar.get(DAY_OF_MONTH) - 7);
				break;
			case KEY_DOWN:
				selectDay(calendar.get(DAY_OF_MONTH) + 7);
				break;
			case KEY_PAGE_UP:
				setMonth(calendar.get(MONTH) - 1);
				break;
			case KEY_PAGE_DOWN:
				setMonth(calendar.get(MONTH) + 1);
				break;
			default:
				break;
			}
		}
	};
}
