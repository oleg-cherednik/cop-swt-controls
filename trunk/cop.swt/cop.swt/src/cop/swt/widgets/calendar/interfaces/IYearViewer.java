package cop.swt.widgets.calendar.interfaces;

import java.util.Calendar;

public interface IYearViewer
{
	void setYear(Calendar date);

	void setYear(int year);

	int getYear();
}
