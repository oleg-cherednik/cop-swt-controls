package cop.swt.widgets.viewers.html.templates;

import static cop.common.extensions.StringExtension.isEmpty;
import static cop.common.enums.HtmlColorEnum.HTML_COLOR_BLUE;
import static cop.swt.widgets.viewers.html.HtmlExtension.makeFontEffect;
import static cop.swt.widgets.viewers.html.enums.HtmlTagEnum.HTML_TAG_BOLD;
import static cop.swt.widgets.viewers.html.enums.HtmlTagEnum.HTML_TAG_ITALIC;
import static cop.swt.widgets.viewers.html.enums.HtmlTagEnum.HTML_TAG_UNDERLINE;
import static cop.swt.widgets.viewers.html.enums.HtmlTagEnum.makeEffect;
import static java.text.DateFormat.SHORT;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import cop.common.extensions.CommonExtension;

@Deprecated
public final class ExampleHtmlTemplate extends AbstractHtmlTemplate<ExampleTO>
{
	private final String MACRO_DATE = "date";
	private final String MACRO_USER = "user";
	private final String MACRO_TITLE = "title";
	private final String MACRO_CONTENT = "content";

	/**
	 * AbstractHtmlTemplate
	 */

	@Override
	protected String getMacroValue(String macro, ExampleTO action)
	{
		if(isEmpty(macro) || CommonExtension.isNull(action))
			return macro;

		if(macro.equalsIgnoreCase(MACRO_USER))
			return action.getUserName();
		if(macro.equalsIgnoreCase(MACRO_DATE))
		{
			DateFormat df = DateFormat.getDateTimeInstance(SHORT, SHORT);
			return df.format(action.getDate().getTime());
		}
		if(macro.equalsIgnoreCase(MACRO_CONTENT))
			return action.getDescription();
		if(macro.equalsIgnoreCase(MACRO_TITLE))
			return action.getTitleName();

		return macro;
	}

	@Override
	protected List<String> createLines()
	{
		String date = makeEffect(getMacro(MACRO_DATE), HTML_TAG_UNDERLINE);
		String user = makeEffect(getMacro(MACRO_USER), HTML_TAG_BOLD);
		String title = makeEffect(getMacro(MACRO_TITLE), HTML_TAG_ITALIC);
		String content = getMacro(MACRO_CONTENT);
		List<String> lines = new LinkedList<String>();

		lines.add(makeFontEffect("[" + date + ", " + user + "] " + title, HTML_COLOR_BLUE));
		lines.add(content);

		return lines;
	}
}

class ExampleTO
{
	private String userName;
	private Calendar date;
	private String description;
	private String titleName;

	public ExampleTO(String userName, Calendar date, String description, String titleName)
	{
		super();
		this.userName = userName;
		this.date = date;
		this.description = description;
		this.titleName = titleName;
	}

	public String getUserName()
	{
		return userName;
	}

	public Calendar getDate()
	{
		return date;
	}

	public String getDescription()
	{
		return description;
	}

	public String getTitleName()
	{
		return titleName;
	}
}
