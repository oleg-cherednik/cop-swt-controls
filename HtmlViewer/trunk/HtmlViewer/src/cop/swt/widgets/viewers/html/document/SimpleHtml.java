/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.document;

/**
 * @author Oleg Cherednik
 * @since 05.01.2011
 */
public class SimpleHtml extends HtmlDocument {
	public void println(String html) {
		partBody.append(html).append(HTML_TAG_NEW_LINE).append("\n");
	}

	public void print(String html) {
		partBody.append(html);
	}
}
