/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.example.news;

import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.NONE;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.viewers.html.HtmlViewer;
import cop.swt.widgets.viewers.html.templates.TemplateHtmlViewer;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public class NewsViewer extends Composite {
	private HtmlViewer<NewsTO> htmlViewer;

	public NewsViewer(Composite parent, int style) {
		super(parent, style);

		setLayout(new GridLayout());

		createPartControl();
	}

	protected HtmlViewer<NewsTO> getHtmlViewer() {
		return htmlViewer;
	}

	private void createPartControl() {
		createHtmlViewerPart();
		refresh();
	}

	private void createHtmlViewerPart() {
		htmlViewer = new TemplateHtmlViewer<NewsTO>(this, NONE, new NewsHtmlTemplate());
		htmlViewer.setLayoutData(new GridData(FILL, FILL, true, true));
	}

	public void refresh() {
		refreshCore();
	}

	protected void refreshCore() {
		htmlViewer.clear();
		htmlViewer.print(NewsGenerator.news);
	}
}
