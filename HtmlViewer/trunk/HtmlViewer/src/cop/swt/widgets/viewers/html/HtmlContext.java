/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html;

import static cop.swt.widgets.viewers.html.HtmlExtension.printBuffer;
import cop.extensions.StringExt;
import cop.swt.widgets.viewers.html.css.CssContext;
import cop.swt.widgets.viewers.html.interfaces.IAppendable;

/**
 * @author Oleg Cherednik
 * @since 05.01.2011
 */
public class HtmlContext implements IAppendable {
	private String styleId;
	private final CssContext style;

	public HtmlContext() {
		this(new CssContext());
	}

	public HtmlContext(CssContext style) {
		this.style = style;
	}

	public CssContext getStyle() {
		return style;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
		this.style.clear();
	}

	/*
	 * IAppendable
	 */

	@Override
	public boolean isEmpty() {
		return style.isEmpty() && StringExt.isEmpty(styleId);
	}

	@Override
	public StringBuilder append(StringBuilder buf) {
		if (buf == null || isEmpty())
			return buf;

		if (!StringExt.isEmpty(styleId))
			buf.append("id=\"" + styleId + "\"");
		if (!style.isEmpty())
			style.append(StringExt.isEmpty(styleId) ? buf : buf.append(" "));

		return buf;
	}

	/*
	 * Object
	 */

	@Override
	public String toString() {
		return printBuffer(this);
	}
}
