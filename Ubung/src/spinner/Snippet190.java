package spinner;

import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import cop.swt.widgets.keys.enums.KeyEnum;

/*
 * Floating point values in Spinner
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */

public class Snippet190
{

	public static void main(String[] args)
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Spinner with float values");
		shell.setLayout(new GridLayout());
		
		Locale.setDefault(Locale.US);
		final Spinner spinner = new Spinner(shell, SWT.NONE);
		// allow 3 decimal places
		spinner.setDigits(3);
		// set the minimum value to 0.001
		spinner.setMinimum(1);
		// set the maximum value to 20
		spinner.setMaximum(20000);
		// set the increment value to 0.010
		spinner.setIncrement(10);
		// set the seletion to 3.456
		spinner.setSelection(3456);
		spinner.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				int selection = spinner.getSelection();
				int digits = spinner.getDigits();
				System.out.println("Selection is " + (selection / Math.pow(10, digits)));
			}
		});
		
		spinner.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(KeyEnum.KEY_GREY_DOT.getKeyCode() == e.keyCode)
				{
					//e.doit = false;
					
					Event event = new Event();
					
					event.keyCode = KeyEnum.KEY_COMMA.getKeyCode();
					event.character = ',';
					
					e.widget.notifyListeners(SWT.KeyDown, event);
				}
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
