//Send questions, comments, bug reports, etc. to the authors:

//Rob Warner (rwarner@interspatial.com)
//Robert Harris (rbrt_harris@yahoo.com)
package Tables;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * This class demonstrates the SWT.VIRTUAL style
 */
public class VirtualTable
{
	private String[] items;

	/**
	 * Runs the application
	 */
	public void run()
	{
		// Create the data for the table
		items = new String[5000];
		for(int i = 0, n = items.length; i < n; i++)
		{
			items[i] = "I am item number " + i;
		}

		// Normal SWT stuff
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Virtual Table");
		createContents(shell);
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		display.dispose();
	}

	/**
	 * Creates the shell's contents
	 * 
	 * @param shell
	 *            the shell
	 */
	private void createContents(Shell shell)
	{
		shell.setLayout(new FillLayout());

		// Create a table and column
		final Table table = new Table(shell, SWT.VIRTUAL);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		TableColumn tc = new TableColumn(table, SWT.NONE);
		tc.setText("Virtual Value");
		tc.setWidth(400);

		// Tell the table how many items it has
		table.setItemCount(items.length);

		// Provide the callback handler--this handler
		// is invoked when the table needs new rows
		table.addListener(SWT.SetData, new Listener()
		{
			public void handleEvent(Event event)
			{
				System.out.println("handleEvent()");
				TableItem item = (TableItem)event.item;
				item.setText(items[table.indexOf(item)]);
			}
		});
	}

	/**
	 * The application entry point
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args)
	{
		new VirtualTable().run();
	}
}
