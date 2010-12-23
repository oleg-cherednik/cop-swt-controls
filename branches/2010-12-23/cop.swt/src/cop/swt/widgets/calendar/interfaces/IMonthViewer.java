package cop.swt.widgets.calendar.interfaces;

import java.util.Calendar;

public interface IMonthViewer
{
	void setMonth(Calendar date);

	void setMonth(int month);

	int getMonth();
}
