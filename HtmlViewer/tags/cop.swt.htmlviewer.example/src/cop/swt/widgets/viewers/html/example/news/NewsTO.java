/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.example.news;

import java.util.Calendar;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public final class NewsTO
{
	private final String section;
	private final String reporter;
	private final String title;
	private final Calendar date;
	private final String note;
	private final String body;

	public NewsTO(String section, String reporter, String title, Calendar date, String note, String body)
	{
		this.section = section;
		this.reporter = reporter;
		this.title = title;
		this.date = date;
		this.note = note;
		this.body = body;
	}

	public String getSection()
	{
		return section;
	}

	public String getReporter()
	{
		return reporter;
	}

	public String getTitle()
	{
		return title;
	}

	public Calendar getDate()
	{
		return date;
	}

	public String getNote()
	{
		return note;
	}

	public String getBody()
	{
		return body;
	}
}
