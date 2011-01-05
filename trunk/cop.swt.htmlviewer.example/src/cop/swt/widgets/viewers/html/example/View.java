/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.example;

import static cop.swt.extensions.ColorExtension.WHITE;
import static org.eclipse.swt.SWT.BORDER;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.NONE;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.ViewPart;

import cop.swt.widgets.viewers.html.example.news.NewsViewer;
import cop.swt.widgets.viewers.html.example.simple.SimpleViewer;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public class View extends ViewPart
{
	public static final String ID = "cop.swt.htmlviewer.example.view";

	private Composite createComposite(Composite parent)
	{
		Composite composite = new Composite(parent, NONE);

		composite.setBackground(WHITE);
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

	/*
	 * IWorkbenchPart
	 */

	@Override
	public void createPartControl(Composite parent)
	{
		parent = createComposite(parent);
		createTabPart(parent);
	}

	@Override
	public void setFocus()
	{}
}
