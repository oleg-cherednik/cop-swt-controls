/*
 * StyledText snippet: Draw a box around text.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import label.BlinkingLabel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import cop.swt.extensions.ColorExtension;

public class Blinked
{
	static String SEARCH_STRING = "box";

	public static void main(String[] args)
	{
		final Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());

		createContent(shell);

		shell.pack();
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private static void createContent(Composite parent)
	{
		final BlinkingLabel label = new BlinkingLabel(parent, SWT.BACKGROUND);

		label.setText("This is a text label");

		FontData fontData = label.getFont().getFontData()[0];

		fontData.setHeight(20);
		fontData.setStyle(SWT.BOLD);

		label.setFont(new Font(parent.getDisplay(), fontData));

		label.setBackground(ColorExtension.BLUE);
		label.setForeground(ColorExtension.CYAN);

		Button button = new Button(parent, SWT.NONE);

		button.setText("change");

		button.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				label.setBlinkingEnabled(!label.isBlinkingEnabled());
			}
		});
	}
}
