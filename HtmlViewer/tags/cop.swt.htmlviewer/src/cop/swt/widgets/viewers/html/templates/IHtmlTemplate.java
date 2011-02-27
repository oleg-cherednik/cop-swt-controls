/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.templates;

import cop.swt.widgets.viewers.html.HtmlTag;
import cop.swt.widgets.viewers.html.document.HtmlDocument;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 16.08.2010
 */
public interface IHtmlTemplate<T>
{
	HtmlTag getDelimeter();

	HtmlDocument getHtmlDocument();

	String getHtml(T obj);
}
