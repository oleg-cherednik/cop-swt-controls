package d2;

/*
 * GC example snippet: implement a simple scribble program
 *
 * For a list of all SWT example snippets see
 * http://dev.eclipse.org/viewcvs/index.cgi/%7Echeckout%7E/platform-swt-home/dev.html#snippets
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Snippet66
{

	public static void main(String[] args)
	{
		Display display = new Display();
		final Shell shell = new Shell(display);
		Listener listener = new Listener()
		{
			int lastX = 0, lastY = 0;

			public void handleEvent(Event event)
			{
				switch(event.type)
				{
				case SWT.MouseMove:
					if((event.stateMask & SWT.BUTTON1) == 0)
						break;
					GC gc = new GC(shell);
					gc.drawLine(lastX, lastY, event.x, event.y);
					gc.dispose();
					// FALL THROUGH
				case SWT.MouseDown:
					lastX = event.x;
					lastY = event.y;
					break;
				}
			}
		};
		shell.addListener(SWT.MouseDown, listener);
		shell.addListener(SWT.MouseMove, listener);
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
