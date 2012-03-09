package cop.swt.widgets.dialogs;

import static cop.swt.extensions.ColorExtension.WIDGET_BACKGROUND;
import static cop.swt.widgets.keys.enums.KeyEnum.parseKeyEnum;
import static org.eclipse.swt.SWT.BORDER;
import static org.eclipse.swt.SWT.NO_TRIM;
import static org.eclipse.swt.SWT.ON_TOP;

import java.util.Calendar;
import java.util.Locale;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;

import cop.swt.widgets.calendar.CalendarViewer;

/**
 * @author cop
 */
public class CalendarDialog
{
	private Shell shell;
	private Display display;
	private CalendarViewer calendar;
	private Calendar receiver;

	public static Calendar getCalendarDate(Shell parent)
	{
		return getCalendarDate(parent, Calendar.getInstance(), null, Locale.getDefault());
	}

	public static Calendar getCalendarDate(Shell parent, Point point)
	{
		return getCalendarDate(parent, Calendar.getInstance(), point, Locale.getDefault());
	}

	public static Calendar getCalendarDate(Shell parent, Calendar date, Point point, Locale locale)
	{
		return getCalendarDate(parent, NO_TRIM | ON_TOP | BORDER, date, point, locale);
	}

	public static Calendar getCalendarDate(Shell parent, int style, Calendar date, Point point, Locale locale)
	{
		CalendarDialog obj = new CalendarDialog(parent, style, locale);

		obj.setDate(date);
		obj.setPosition(point);

		return obj.showDialog(parent);
	}

	private CalendarDialog(Shell parent, int style, Locale locale)
	{
		createDialog(parent, style);
		createCalendar(style, locale);

		shell.pack();
	}

	private Calendar showDialog(Composite parent)
	{
		shell.open();

		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}

		return receiver;
	}

	private void setDate(Calendar date)
	{
		calendar.setDate(date);
	}

	private void setPosition(Point point)
	{
		if(point == null)
			return;

		Rectangle tmp = shell.getBounds();

		tmp.x = point.x;
		tmp.y = point.y;

		shell.setBounds(tmp);
	}

	private void createDialog(Shell parent, int style)
	{
		shell = new Shell(parent, style);
		display = shell.getDisplay();

		shell.setLayout(createLayout());
		shell.setBackground(WIDGET_BACKGROUND);
		shell.addShellListener(exitOnDeactivated);
	}

	private void createCalendar(int style, Locale locale)
	{
		calendar = new CalendarViewer(shell, style, locale);

		calendar.addMouseListener(onMouse);
		calendar.addKeyListener(onKeyPress);
	}

	private static Layout createLayout()
	{
		GridLayout gridLayout = new GridLayout(1, false);

		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;

		return gridLayout;
	}

	private void saveResult(boolean isChanged)
	{
		if(isChanged)
			receiver = calendar.getDate();

		shell.close();
	}

	private MouseListener onMouse = new MouseAdapter()
	{
		@Override
		public void mouseDoubleClick(MouseEvent e)
		{
			saveResult(true);
		}
	};

	private KeyListener onKeyPress = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch(parseKeyEnum(e.keyCode))
			{
			case KEY_ESC:
				saveResult(false);
				break;
			case KEY_ENTER:
				saveResult(true);
				break;
			default:
				break;
			}
		}
	};

	private ShellListener exitOnDeactivated = new ShellAdapter()
	{
		@Override
		public void shellDeactivated(ShellEvent e)
		{
			saveResult(false);
		}
	};
}
