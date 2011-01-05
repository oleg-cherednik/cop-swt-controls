/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id:$
 * $HeadURL:$
 */
package cop.swt.widgets.viewers.html.templates;

import static cop.common.extensions.StringExtension.isEmpty;

import java.util.StringTokenizer;

import cop.swt.widgets.viewers.html.HtmlTag;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 16.08.2010
 */
public abstract class AbstractHtmlTemplate<T> implements HtmlTemplate<T>
{
	protected static final String MACRO_DELIMETER = "%";
	protected static final HtmlTag PART_DELIMETER = HtmlTag.create("hr");

	private final String macroDelimeter;
	private final HtmlTag partDelimeter;
	private final String template;

	protected AbstractHtmlTemplate()
	{
		this(null, null);
	}

	protected AbstractHtmlTemplate(String macroDelimeter, HtmlTag partDelimeter)
	{
		this.macroDelimeter = isEmpty(macroDelimeter) ? MACRO_DELIMETER : macroDelimeter;
		this.partDelimeter = (partDelimeter == null) ? PART_DELIMETER : partDelimeter;
		this.template = getTemplate();
	}

	protected final String getMacro(String macro)
	{
		return isEmpty(macro) ? "" : (macroDelimeter + macro + macroDelimeter);
	}

	protected abstract String getTemplate();

	protected abstract String getMacroValue(String macro, T obj);

	/**
	 * HtmlTemplate
	 */

	@Override
	public final HtmlTag getDelimeter()
	{
		return partDelimeter;
	}

	@Override
	public final String getHtml(T obj)
	{
		if(obj == null || isEmpty(template))
			return "";

		StringTokenizer st = new StringTokenizer(template, macroDelimeter);
		StringBuilder buf = new StringBuilder(1024);

		while(st.hasMoreTokens())
			buf.append(getMacroValue(st.nextToken(), obj));

		return "" + buf;
	}
}
