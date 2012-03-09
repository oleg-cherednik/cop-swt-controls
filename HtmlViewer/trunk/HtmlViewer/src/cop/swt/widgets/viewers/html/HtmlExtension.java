/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html;

import static cop.swt.widgets.viewers.html.HtmlTag.HTML_TAG_CLOSE;
import static cop.swt.widgets.viewers.html.HtmlTag.HTML_TAG_END;
import static cop.swt.widgets.viewers.html.HtmlTag.HTML_TAG_OPEN;

import java.io.IOException;

import cop.swt.widgets.viewers.html.enums.HtmlColorEnum;
import cop.swt.widgets.viewers.html.interfaces.IAppendable;

/**
 * Class provides common methods that can be used within HTML context.<br>
 * This class contains only <i><u>static</u></i> methods. It can't be instantiated or inherited.<br>
 * For simple html tags {@link HtmlColorEnum} can be used.<br>
 * This class provides methods to use complex html tags with number of parameters.
 * 
 * @author Oleg Cherednik
 * @since 23.12.2009
 * @see HtmlColorEnum
 */
public final class HtmlExtension {
	/**
	 * Closed constructor
	 */
	private HtmlExtension() {}

	public static String openTag(String tag) {
		return HTML_TAG_OPEN + tag + HTML_TAG_CLOSE;
	}

	public static String closeTag(String tag) {
		return HTML_TAG_OPEN + HTML_TAG_END + tag + HTML_TAG_CLOSE;
	}

	public static StringBuilder openTag(StringBuilder buf, String tag, HtmlContext context) {
		if (buf == null || HtmlContext.isEmpty(tag))
			return buf;

		buf.append(HTML_TAG_OPEN);
		buf.append(tag);

		if (context != null && !context.isEmpty())
			context.append(buf.append(" "));

		buf.append(HTML_TAG_CLOSE);

		return buf;
	}

	public static String printBuffer(IAppendable buf) {
		try {
			if (buf != null && !buf.isEmpty())
				return buf.append(new StringBuilder()).toString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}
}
