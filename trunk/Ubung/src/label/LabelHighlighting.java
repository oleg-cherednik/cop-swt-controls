package label;

/******************************************************************************
 * Copyright (c) 1998, 2004 Jackwind Li Guojie
 * All right reserved.
 * 
 * Created on Oct 28, 2003 8:10:30 PM by JACK
 * $Id$
 * 
 * visit: http://www.asprise.com/swt
 *****************************************************************************/

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class LabelHighlighting
{
	Display display = new Display();
	Shell shell = new Shell(display);
	Label label = new Label(shell, SWT.SHADOW_IN | SWT.CENTER);
	Listener listener = new MouseEnterExitListener();

	public LabelHighlighting()
	{

		label.setText("Point your cursor here ...");
		label.setBounds(30, 30, 200, 30);

		label.addListener(SWT.MouseEnter, listener);
		label.addListener(SWT.MouseExit, listener);

		shell.setText("Move your cursor to test ...");
		shell.setSize(260, 120);
		shell.open();

		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
			{// If no more entries in event queue
				display.sleep();
			}
		}

		display.dispose();
	}

	class MouseEnterExitListener implements Listener
	{
		public void handleEvent(Event e)
		{
			switch(e.type)
			{
			case SWT.MouseEnter:
				display.syncExec(new Runnable()
				{
					public void run()
					{
						label.setBackground(display.getSystemColor(SWT.COLOR_YELLOW));
						label.setText("Cursor enters the label");
					}
				});

				break;

			case SWT.MouseExit:
				display.syncExec(new Runnable()
				{
					public void run()
					{
						label.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
						label.setText("Cursor leaves the label");
					}
				});

				break;
			}
		}
	}

	public static void main(String[] args)
	{
		new LabelHighlighting();
	}
}