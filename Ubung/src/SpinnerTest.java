import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

//import swtcalendar.org.vafada.swtcalendar.Spinner;

public class SpinnerTest
{

	public static void main(String[] args)
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new RowLayout());

		final swtcalendar.org.vafada.swtcalendar.Spinner spinner = new swtcalendar.org.vafada.swtcalendar.Spinner(shell, SWT.BORDER);
		final org.eclipse.swt.widgets.Spinner spinner2 = new org.eclipse.swt.widgets.Spinner(shell, SWT.BORDER);
		final MultiTouchButton button = new MultiTouchButton(shell, SWT.ARROW | SWT.RIGHT);
		spinner.setMaximum(999);
		System.out.println("max set to " + spinner.getMaximum());
		spinner.setMinimum(100);
		System.out.println("min set to " + spinner.getMinimum());
		shell.pack();
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
	}
}