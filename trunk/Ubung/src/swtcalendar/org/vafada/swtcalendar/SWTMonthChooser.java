package swtcalendar.org.vafada.swtcalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class SWTMonthChooser extends Composite
{
	private SWTDayChooser dayChooser;
	private Combo comboBox;
	private Locale locale;

	public SWTMonthChooser(Composite parent)
	{
		super(parent, SWT.NONE);

		locale = Locale.getDefault();
		setLayout(new FillLayout());
		comboBox = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);

		initNames();

		setMonth(Calendar.getInstance().get(Calendar.MONTH));
		setFont(parent.getFont());
	}

	private void initNames()
	{
		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
		String[] monthNames = dateFormatSymbols.getMonths();

		int month = comboBox.getSelectionIndex();
		if(comboBox.getItemCount() > 0)
		{
			comboBox.removeAll();
		}

		for(int i = 0; i < monthNames.length; i++)
		{
			String name = monthNames[i];
			if(name.length() > 0)
			{
				comboBox.add(name);
			}
		}

		if(month < 0)
		{
			month = 0;
		}
		else if(month >= comboBox.getItemCount())
		{
			month = comboBox.getItemCount() - 1;
		}

		comboBox.select(month);
	}

	public void addSelectionListener(SelectionListener listener)
	{
		comboBox.addSelectionListener(listener);
	}

	public void removeSelectionListener(SelectionListener listener)
	{
		comboBox.removeSelectionListener(listener);
	}

	public void setMonth(int newMonth)
	{
		comboBox.select(newMonth);
	}

	public int getMonth()
	{
		return comboBox.getSelectionIndex();
	}

	public void setLocale(Locale locale)
	{
		this.locale = locale;
		initNames();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setFont(org.eclipse.swt.graphics.Font)
	 */
	public void setFont(Font font)
	{
		super.setFont(font);
		comboBox.setFont(getFont());
	}
}
