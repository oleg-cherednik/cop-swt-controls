/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html;

import java.util.Collection;

import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 16.08.2010
 */
public class SimpleHtmlViewer extends HtmlViewer<String> {
	public SimpleHtmlViewer(Composite parent, int style) {
		super(parent, style);
	}

	/*
	 * HtmlViewer
	 */

	@Override
	public void println(String html) {
		addTextLn(html);
	}

	@Override
	public void print(String html) {
		addText(html);
	}

	@Override
	public void print(Collection<String> html) {
		int size = html.size();
		int i = 0;

		for (String part : html)
			addText(part, ++i < size);

		refresh();
	}
}
