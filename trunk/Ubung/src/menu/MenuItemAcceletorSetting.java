package menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class MenuItemAcceletorSetting
{

	public static void main(String[] args)
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		Menu bar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(bar);
		MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
		fileItem.setText("&File");
		Menu submenu = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(submenu);
		MenuItem item = new MenuItem(submenu, SWT.PUSH);

		item.setText("Select &All\tCtrl+A");

		item.setAccelerator(SWT.MOD1 + 'A');

		item.addListener(SWT.Selection, new Listener() {

		    public void handleEvent(Event e) {

		        System.out.println("The item was selected.");

		    }

		});

		
		
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
