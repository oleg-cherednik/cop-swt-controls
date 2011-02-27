/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.example;

import static org.eclipse.swt.SWT.BORDER;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.NONE;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import cop.swt.widgets.viewers.html.example.news.NewsViewer;
import cop.swt.widgets.viewers.html.example.simple.SimpleViewer;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public class Main extends ApplicationWindow
{
	public static void main(String[] args)
	{
		new Main().run();
	}

	private Main()
	{
		super(null);
	}

	public void run()
	{
		setBlockOnOpen(true);
		open();
		Display.getCurrent().dispose();
	}

	@Override
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);

		shell.setText("HtmlViewer example");
		shell.setSize(920, 450);
	}

	@Override
	protected Control createContents(Composite parent)
	{
		Composite composite = createComposite(parent);

		createTabPart(composite);

		return composite;
	}

	private Composite createComposite(Composite parent)
	{
		Composite composite = new Composite(parent, NONE);

		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(FILL, FILL, true, false));

		return composite;
	}

	private void createTabPart(Composite parent)
	{
		CTabFolder tabFolder = new CTabFolder(parent, BORDER);
		tabFolder.setLayoutData(new GridData(FILL, FILL, true, true));

		addTabPage("Simple", new SimpleViewer(tabFolder, NONE));
		addTabPage("News", new NewsViewer(tabFolder, NONE));
	}

	private static void addTabPage(String title, Control control)
	{
		CTabFolder parent = (CTabFolder)control.getParent();
		CTabItem tabItem = new CTabItem(parent, NONE);

		tabItem.setText(title);
		tabItem.setControl(control);
	}
}
