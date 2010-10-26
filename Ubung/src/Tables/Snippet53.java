package Tables;

/*
 * Table example snippet: remove selected items
 *
 * For a list of all SWT example snippets see
 * http://dev.eclipse.org/viewcvs/index.cgi/%7Echeckout%7E/platform-swt-home/dev.html#snippets
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class Snippet53
{

	public static void main(String[] args)
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		final Table table = new Table(shell, SWT.BORDER | SWT.MULTI);
		table.setSize(200, 200);
		for(int i = 0; i < 128; i++)
		{
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText("Item " + i);
		}
		Menu menu = new Menu(shell, SWT.POP_UP);
		table.setMenu(menu);
		MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText("Delete Selection");
		item.addListener(SWT.Selection, new Listener()
		{
			public void handleEvent(Event event)
			{
				table.remove(table.getSelectionIndices());
			}
		});
		shell.pack();
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
