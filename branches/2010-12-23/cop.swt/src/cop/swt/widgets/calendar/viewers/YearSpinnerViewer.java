package cop.swt.widgets.calendar.viewers;

import static cop.common.extensions.CalendarExtension.YEAR_MAX;
import static cop.common.extensions.CalendarExtension.YEAR_MIN;
import static cop.common.extensions.CalendarExtension.isYear;
import static cop.common.extensions.CommonExtension.isNotNull;
import static java.util.Calendar.YEAR;
import static org.eclipse.swt.SWT.BORDER;

import java.util.Calendar;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;

import cop.swt.widgets.calendar.interfaces.IYearViewer;
import cop.swt.widgets.interfaces.Clearable;
import cop.swt.widgets.interfaces.Refreshable;

public class YearSpinnerViewer extends Spinner implements IYearViewer, Refreshable, Clearable
{
	public YearSpinnerViewer(Composite parent)
	{
		super(parent, BORDER);

		super.setMinimum(YEAR_MIN);
		super.setMaximum(YEAR_MAX);
		super.setIncrement(1);
		super.setPageIncrement(10);
		super.setTextLimit(4);

		setYear(Calendar.getInstance());
	}

	/*
	 * Widget
	 */

	@Override
	protected void checkSubclass()
	{}

	/*
	 * Spinner
	 */

	@Override
	@Deprecated
	public void setIncrement(int value)
	{}

	@Override
	@Deprecated
	public void setPageIncrement(int value)
	{}

	@Override
	@Deprecated
	public void setMinimum(int value)
	{}

	@Override
	@Deprecated
	public void setMaximum(int value)
	{}

	@Override
	@Deprecated
	public int getSelection()
	{
		return 0;
	}

	@Override
	@Deprecated
	public void setSelection(int value)
	{}

	@Override
	@Deprecated
	public void setTextLimit(int limit)
	{}

	/*
	 * IYearViewer
	 */

	@Override
	public void setYear(Calendar date)
	{
		if(isNotNull(date))
			super.setSelection(date.get(YEAR));
	}

	@Override
	public int getYear()
	{
		return super.getSelection();
	}

	@Override
	public void setYear(int year)
	{
		if(isYear(year))
			super.setSelection(year);
	}

	/*
	 * Refreshable
	 */

	@Override
	public void refresh()
	{
		update();
		redraw();
	}

	/*
	 * Clearable
	 */
	@Override
	public void clear()
	{}
}
