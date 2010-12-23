/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets;

import static cop.swt.widgets.keys.enums.KeyEnum.KEY_ENTER;
import static cop.swt.widgets.keys.enums.KeyEnum.parseKeyEnum;
import static org.eclipse.swt.SWT.FILL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

class ChangeItemNameDialog
{
	private Shell shell;
	private Display display;
	
	private Text nameText;
	private String result;

	private ChangeItemNameDialog(Shell parent, String name, Integer x, Integer y)
	{
		shell = createDialog(parent);
		display = shell.getDisplay();
		
		nameText = new Text(shell, SWT.BORDER);

//		calendar = createCalendar(date);
//		receiver = createReceiver();

		changePosition(x, y);

		shell.pack();
		
		nameText.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(parseKeyEnum(e.keyCode) == KEY_ENTER)
					saveResult();
			}
		});
	}
	
	public static String getCalendarDate(Shell parent, String name)
	{
		return getCalendarDate(parent, name, null, null);
	}

	public static String getCalendarDate(Shell parent)
	{
		return getCalendarDate(parent, "[default]", null, null);
	}

	public static String getCalendarDate(Shell parent, String name, Integer x, Integer y)
	{
		ChangeItemNameDialog obj = new ChangeItemNameDialog(parent, name, x, y);

		return obj.showDialog(parent);
	}

	private String showDialog(Composite parent)
	{
		shell.open();

		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}

		return result;
	}

	private void changePosition(Integer x, Integer y)
	{
		if(x == null || y == null)
			return;

		Rectangle tmp = shell.getBounds();

		tmp.x = x;
		tmp.y = y;

		shell.setBounds(tmp);
	}

	private Shell createDialog(Shell parent)
	{
		Shell shell = new Shell(parent, SWT.NO_TRIM | SWT.ON_TOP);

		shell.setLayout(createLayout());
		shell.setLayoutData(createLayoutData());
		shell.addShellListener(exitOnDeactivated);

		return shell;
	}

//	private DateTime createCalendar(Calendar date)
//	{
//		DateTime obj = new DateTime(shell, CALENDAR | BORDER);
//
//		if(date != null)
//			obj.setDate(date.get(YEAR), date.get(MONTH), date.get(DATE));
//
//		return obj;
//	}

//	private Calendar createReceiver()
//	{
//		final Calendar calendar = Calendar.getInstance();
//
//		calendar.clear();
//
//		return calendar;
//	}

	private Layout createLayout()
	{
		GridLayout gridLayout = new GridLayout(1, false);

		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;

		return gridLayout;
	}

	private GridData createLayoutData()
	{
		return new GridData(FILL, FILL, false, false);
	}

	private void saveResult()
	{
//		receiver.set(Calendar.YEAR, calendar.getYear());
//		receiver.set(Calendar.MONTH, calendar.getMonth());
//		receiver.set(Calendar.DATE, calendar.getDay());
		
		result = nameText.getText();

		shell.close();
	}

	private ShellListener exitOnDeactivated = new ShellAdapter()
	{
		@Override
		public void shellDeactivated(ShellEvent e)
		{
			saveResult();
		}
	};
}
