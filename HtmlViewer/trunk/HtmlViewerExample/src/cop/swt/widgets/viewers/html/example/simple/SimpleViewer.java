/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.example.simple;

import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.NONE;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.LoggerFactory;

import cop.swt.widgets.viewers.html.SimpleHtmlViewer;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public class SimpleViewer extends Composite
{
	private SimpleHtmlViewer htmlViewer;

	public SimpleViewer(Composite parent, int style)
	{
		super(parent, style);
		
		LoggerFactory.getLogger(getClass()).debug("SimpleViewer");

		setLayout(new GridLayout());
		//setBackground(WHITE);

		createPartControl();
	}

	protected SimpleHtmlViewer getHtmlViewer()
	{
		return htmlViewer;
	}

	private void createPartControl()
	{
		createHtmlViewerPart();
		refresh();
	}

	private void createHtmlViewerPart()
	{
		htmlViewer = new SimpleHtmlViewer(this, NONE);
		htmlViewer.setLayoutData(new GridData(FILL, FILL, true, true));
	}

	public void refresh()
	{
		refreshCore();
	}

	protected void refreshCore()
	{
		htmlViewer.clear();
		htmlViewer.print(SimpleGenerator.lines);
	}
}
