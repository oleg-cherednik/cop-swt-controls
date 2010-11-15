package swtcalendar.org.vafada.swtcalendar;

import static cop.common.extensions.CalendarExtension.getMonths;
import static java.util.Calendar.MONTH;
import static org.eclipse.swt.SWT.DROP_DOWN;
import static org.eclipse.swt.SWT.READ_ONLY;

import java.util.Calendar;
import java.util.Locale;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class MonthCombo extends Combo
{
	private Locale locale;

	public MonthCombo(Composite parent)
	{
		this(parent, Locale.getDefault());
	}

	public MonthCombo(Composite parent, Locale locale)
	{
		super(parent, DROP_DOWN | READ_ONLY);

		setFont(parent.getFont());
		setLayout(new FillLayout());
		setLocale(locale);
		setMonth(Calendar.getInstance().get(MONTH));
	}

	private void init()
	{
		int oldIndex = getSelectionIndex();

		if(getItemCount() > 0)
			removeAll();

		for(String monthName : getMonths(locale))
			if(monthName.length() > 0)
				add(monthName);

		select(getNewIndex(oldIndex));
	}

	private int getNewIndex(int oldIndex)
	{
		if(oldIndex < 0)
			return 0;

		int itemCount = getItemCount();

		return (oldIndex >= itemCount) ? (itemCount - 1) : oldIndex;
	}

	public void setMonth(int month)
	{
		select(month);
	}

	public int getMonth()
	{
		return getSelectionIndex();
	}

	public void setLocale(Locale locale)
	{
		this.locale = locale;

		init();
	}

	/*
	 * Widget
	 */

	@Override
	protected void checkSubclass()
	{}
}
