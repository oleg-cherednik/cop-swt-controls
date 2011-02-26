/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id:$
 * $HeadURL:$
 */
package cop.swt.widgets.viewers.html;

import static cop.swt.widgets.viewers.html.HtmlExtension.printBuffer;

import java.io.IOException;

import cop.common.extensions.StringExtension;
import cop.swt.widgets.viewers.html.css.CssContext;
import cop.swt.widgets.viewers.html.interfaces.IAppendable;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public class HtmlContext implements IAppendable
{
	private String styleId;
	private final CssContext style;

	public HtmlContext()
	{
		this(new CssContext());
	}

	public HtmlContext(CssContext style)
	{
		this.style = style;
	}

	public CssContext getStyle()
	{
		return style;
	}

	public void setStyleId(String styleId)
	{
		this.styleId = styleId;
	}

	public void clear()
	{
		styleId = null;
		style.clear();
	}

	/*
	 * IAppendable
	 */

	@Override
	public boolean isEmpty()
	{
		return style.isEmpty() && StringExtension.isEmpty(styleId);
	}

	@Override
	public Appendable append(Appendable buf) throws IOException
	{
		if(buf == null || isEmpty())
			return buf;

		if(StringExtension.isNotEmpty(styleId))
			buf.append("id=\"" + styleId + "\"");

		if(!style.isEmpty())
			style.append(StringExtension.isNotEmpty(styleId) ? buf.append(" ") : buf);

		return buf;
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		return printBuffer(this);
	}
}
