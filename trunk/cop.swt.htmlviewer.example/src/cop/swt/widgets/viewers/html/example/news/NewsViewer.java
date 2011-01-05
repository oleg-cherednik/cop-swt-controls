/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id:$
 * $HeadURL:$
 */
package cop.swt.widgets.viewers.html.example.news;

import static cop.swt.extensions.ColorExtension.WHITE;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.viewers.html.HtmlViewer;
import cop.swt.widgets.viewers.html.templates.TemplateHtmlViewer;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public class NewsViewer extends Composite
{
	private HtmlViewer<NewsTO> htmlViewer;
	private Button refreshButton;

	public NewsViewer(Composite parent, int style)
	{
		super(parent, style);

		setLayout(new GridLayout());
		setBackground(WHITE);

		createPartControl();
	}

	protected HtmlViewer<NewsTO> getHtmlViewer()
	{
		return htmlViewer;
	}

	private void createPartControl()
	{
		createHtmlViewerPart();
		createRefreshButton();
		refresh();
	}

	private void createRefreshButton()
	{
		refreshButton = new Button(this, SWT.PUSH);
		refreshButton.setText("Refresh");
		// refreshButton.addSelectionListener(refreshListener);
	}

	private void createHtmlViewerPart()
	{
		htmlViewer = new TemplateHtmlViewer<NewsTO>(this, SWT.NONE, new NewsHtmlTemplate());
		htmlViewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	// private SelectionListener refreshListener = new SelectionAdapter()
	// {
	// @Override
	// public void widgetSelected(SelectionEvent e)
	// {
	// refresh();
	// }
	// };

	public void refresh()
	{
		refreshCore();
	}

	protected void refreshCore()
	{
		htmlViewer.clear();
		htmlViewer.print(NewsGenerator.news);
	}
}
