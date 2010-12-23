package cop.swt.widgets.calendar.viewers;

import static cop.common.extensions.CalendarExtension.getMonths;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.NumericExtension.isInRangeMinMax;
import static cop.common.extensions.StringExtension.isNotEmpty;
import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.JANUARY;
import static java.util.Calendar.MONTH;
import static org.eclipse.swt.SWT.READ_ONLY;

import java.util.Calendar;
import java.util.Locale;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.calendar.interfaces.IMonthViewer;
import cop.swt.widgets.interfaces.Clearable;
import cop.swt.widgets.interfaces.Refreshable;

public class MonthComboViewer extends Combo implements IMonthViewer, Refreshable, Clearable
{
	private Locale locale;

	public MonthComboViewer(Composite parent)
	{
		this(parent, Locale.getDefault());
	}

	public MonthComboViewer(Composite parent, Locale locale)
	{
		super(parent, READ_ONLY);

		setFont(parent.getFont());
		setLayout(new FillLayout());
		setLocale(locale);

		setMonth(Calendar.getInstance(locale).get(MONTH));
	}

	private int getNewIndex(int oldIndex)
	{
		if(oldIndex < 0)
			return 0;

		int itemCount = getItemCount();

		return (oldIndex >= itemCount) ? (itemCount - 1) : oldIndex;
	}

	public void setLocale(Locale locale)
	{
		this.locale = locale;
		refresh();
	}

	/*
	 * Widget
	 */

	@Override
	protected void checkSubclass()
	{}

	/*
	 * IMonthViewer
	 */

	@Override
	public void setMonth(Calendar date)
	{
		if(isNotNull(date))
			super.select(date.get(MONTH));
	}

	@Override
	public void setMonth(int month)
	{
		if(isInRangeMinMax(month, JANUARY, DECEMBER))
			super.select(month);
	}

	@Override
	public int getMonth()
	{
		return super.getSelectionIndex();
	}

	/*
	 * Refreshable
	 */

	@Override
	public void refresh()
	{
		int oldMonth = getMonth();

		if(getItemCount() > 0)
			removeAll();

		for(String monthName : getMonths(locale))
			if(isNotEmpty(monthName))
				add(monthName);

		setMonth(getNewIndex(oldMonth));
	}

	/*
	 * Clearable
	 */

	@Override
	public void clear()
	{
	// TODO Auto-generated method stub
	}

	/*
	 * Combo
	 */

	@Override
	@Deprecated
	public int getSelectionIndex()
	{
		return super.getSelectionIndex();
	}

	@Override
	@Deprecated
	public void setText(String string)
	{}

	@Override
	@Deprecated
	public void select(int index)
	{
		setMonth(index);
	}
}
