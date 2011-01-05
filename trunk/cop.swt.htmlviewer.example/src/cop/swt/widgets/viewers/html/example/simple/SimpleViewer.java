/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id:$
 * $HeadURL:$
 */
package cop.swt.widgets.viewers.html.example.simple;

import static cop.swt.extensions.ColorExtension.WHITE;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

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

		setLayout(new GridLayout());
		setBackground(WHITE);

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
		try
		{
			htmlViewer = new SimpleHtmlViewer(this, SWT.NONE);
			htmlViewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
