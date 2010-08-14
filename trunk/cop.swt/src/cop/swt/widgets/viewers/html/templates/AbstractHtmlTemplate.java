package cop.swt.widgets.viewers.html.templates;

import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isEmpty;
import static cop.swt.widgets.viewers.html.enums.HtmlTagEnum.HTML_TAG_NEW_LINE;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public abstract class AbstractHtmlTemplate<T> implements HtmlTemplate<T>
{
	private static final String DEFAULT_DELIMETER = "%";
	private final String delimeter;
	private List<String> lines = new LinkedList<String>();

	protected AbstractHtmlTemplate()
	{
		this(null);
	}

	protected AbstractHtmlTemplate(String delimeter)
	{
		this.delimeter = isEmpty(delimeter) ? DEFAULT_DELIMETER : delimeter;
		this.lines.addAll(createLines());
	}

	private final String getMacroFreeString(String macroStr, T obj)
	{
		if(isEmpty(macroStr))
			return "";

		StringTokenizer st = new StringTokenizer(macroStr, delimeter);
		StringBuilder buf = new StringBuilder();

		while(st.hasMoreTokens())
			buf.append(getMacroValue(st.nextToken(), obj));

		return "" + buf;
	}

	public final String getMacro(String macro)
	{
		if(isEmpty(macro))
			return "";

		return delimeter + macro + delimeter;
	}

	protected abstract List<String> createLines();

	protected abstract String getMacroValue(String macro, T obj);

	/**
	 * HtmlTemplate
	 */

	@Override
	public final String getHtml(T obj)
	{
		if(isNull(obj))
			return "";

		StringBuilder buf = new StringBuilder();

		for(int i = 0, size = lines.size(); i < size; i++)
		{
			buf.append(getMacroFreeString(lines.get(i), obj));

			if(i < size - 1)
				buf.append(HTML_TAG_NEW_LINE.open());
		}

		return "" + buf;
	}
}
