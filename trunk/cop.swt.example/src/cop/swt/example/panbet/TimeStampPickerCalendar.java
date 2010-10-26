package cop.swt.example.panbet;

import static java.util.Calendar.HOUR;
import static java.util.Calendar.MINUTE;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.forms.widgets.FormToolkit;

import cop.common.extensions.CalendarExtension;
import cop.common.extensions.CommonExtension;
import cop.swt.extensions.ColorExtension;
import cop.swt.widgets.dialogs.CalendarDialog;
import cop.swt.widgets.localization.interfaces.LocaleSupport;

/**
 * @author cop
 */
public final class TimeStampPickerCalendar extends Composite implements LocaleSupport
{
	private TimeStampPicker dateTimePicker;
	private Button button;
	private boolean above = false;
	private int hour = 0;
	private int minute = 0;
	@SuppressWarnings("unused")
	private int second = 0;

	private Locale locale = Locale.getDefault();

	public static TimeStampPickerCalendar getDateTimeInstance(FormToolkit toolkit, Composite parent, int style,
	                boolean more2000, boolean isSecond)
	{
		return getDateTimeInstance(toolkit, parent, style, more2000, isSecond, false);
	}

	public static TimeStampPickerCalendar getDateInstance(FormToolkit toolkit, Composite parent, int style)
	{
		return getDateInstance(toolkit, parent, style, false);
	}

	public static TimeStampPickerCalendar getDateTimeInstance(FormToolkit toolkit, Composite parent, int style,
	                boolean more2000, boolean isSecond, boolean above)
	{
		return new TimeStampPickerCalendar(toolkit, parent, style, more2000, isSecond, above, false);
	}

	public static TimeStampPickerCalendar getDateInstance(FormToolkit toolkit, Composite parent, int style,
	                boolean above)
	{
		return new TimeStampPickerCalendar(toolkit, parent, style, false, false, above, true);
	}

	public TimeStampPickerCalendar(FormToolkit toolkit, Composite parent, int style, boolean more2000,
	                boolean isSecond, boolean above, boolean onlyData)
	{
		super(parent, style);

		this.above = above;

		configureLayout();
		configureLayoutData();

		createTimeStampPicker(toolkit, (style | SWT.SINGLE) & ~SWT.BORDER, more2000, isSecond, onlyData);

		button = createButton();

		setBackground(ColorExtension.WHITE);
		setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
	}

	private void createTimeStampPicker(FormToolkit toolkit, int style, boolean more2000, boolean isSecond,
	                boolean onlyDate)
	{
		if(onlyDate)
			dateTimePicker = new TimeStampPicker(this, style);
		else
			dateTimePicker = new TimeStampPicker(this, style | SWT.SINGLE, more2000, isSecond);

		dateTimePicker.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
	}

	public void setTimeDefault(int hour, int minute, int second)
	{
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	private void configureLayout()
	{
		GridLayout layout = new GridLayout(2, false);

		layout.horizontalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;

		setLayout(layout);
	}

	private void configureLayoutData()
	{
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, false, false);

		setLayoutData(layoutData);
	}

	private Button createButton()
	{
		final Button button = new Button(this, SWT.ARROW | SWT.DOWN);
		button.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		button.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				Calendar time = dateTimePicker.getCalendar();
				Point point = calculatePoint(button.getBounds());
				Calendar date = CalendarDialog.getCalendarDate(getShell(), time, point, locale);

				if(date != null)
				{
					if(time == null)
					{
						date.set(HOUR, hour);
						date.set(MINUTE, minute);
					}
					else
						CalendarExtension.setTime(date, time);

					dateTimePicker.setDate(date.getTime());

					if(time == null || time.compareTo(date) != 0)
						dateTimePicker.notifyListeners(SWT.KeyDown, new Event());
				}
			}
		});

		return button;
	}

	private Point calculatePoint(Rectangle rect)
	{
		Point point = button.getParent().toDisplay(new Point(rect.x, rect.y));

		point.x -= rect.width * 5;
		point.y = above ? (point.y - rect.height - 136) : (point.y + rect.height);

		return point;
	}

	public void setSQLDate(java.sql.Date date)
	{
		dateTimePicker.setDate(date);
	}

	public void setDate(Date date)
	{
		dateTimePicker.setDate(date);
	}

	public void setCurrentTime()
	{
		dateTimePicker.setCurrentTime();
	}

	public Timestamp getTimestamp()
	{
		return dateTimePicker.getTimestamp();
	}

	public Date getSQLDate()
	{
		return dateTimePicker.getSQLDate();
	}

	public Date getDate()
	{
		return dateTimePicker.getDate();
	}

	@Override
	public void setData(Object data)
	{
		dateTimePicker.setData(data);
		button.setData(data);
	}

	public void addModifyListener(ModifyListener listener)
	{
		if(dateTimePicker != null)
			dateTimePicker.addModifyListener(listener);
	}

	public void removeModifyListener(ModifyListener listener)
	{
		if(dateTimePicker != null)
			dateTimePicker.removeModifyListener(listener);
	}

	@Override
	public void setBackground(Color color)
	{
		super.setBackground(color);

		if(dateTimePicker != null)
			dateTimePicker.setBackground(color);
		if(button != null)
			button.setBackground(color);
	}

	@Override
	public void setForeground(Color color)
	{
		super.setForeground(color);

		if(dateTimePicker != null)
			dateTimePicker.setForeground(color);
		if(button != null)
			button.setForeground(color);
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		if(dateTimePicker != null)
			dateTimePicker.setEnabled(enabled);
	}

	public void setEditable(boolean editable)
	{
		super.setEnabled(editable);

		if(dateTimePicker != null)
			dateTimePicker.setEditable(editable);
	}

	public void setSelection(Point selection)
	{
		if(dateTimePicker != null)
			dateTimePicker.setSelection(selection);
	}

	public void setSelection(int start)
	{
		if(dateTimePicker != null)
			dateTimePicker.setSelection(start);
	}

	public void setSelection(int start, int end)
	{
		if(dateTimePicker != null)
			dateTimePicker.setSelection(start, end);
	}

	@Override
	public void addFocusListener(FocusListener listener)
	{
		if(dateTimePicker != null)
			dateTimePicker.addFocusListener(listener);
	}

	@Override
	public void addKeyListener(KeyListener listener)
	{
		if(listener == null)
			return;

		super.addKeyListener(listener);

		if(dateTimePicker != null)
			dateTimePicker.addKeyListener(listener);
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		if(CommonExtension.isNotNull(locale))
			this.locale = locale;
	}
}
