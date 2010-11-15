package Tables;

/*
 * Table example snippet: create a table (no columns, no headers)
 *
 * For a list of all SWT example snippets see
 * http://dev.eclipse.org/viewcvs/index.cgi/%7Echeckout%7E/platform-swt-home/dev.html#snippets
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class Snippet35
{

	public static void main(String[] args)
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		Table table = new Table(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		for(int i = 0; i < 12; i++)
		{
			TableItem item = new TableItem(table, 0);
			item.setText("Item " + i);
		}
		table.setSize(100, 100);
		shell.setSize(200, 200);
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
