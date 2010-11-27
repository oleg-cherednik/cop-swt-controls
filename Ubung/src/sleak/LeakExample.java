package sleak;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.program.*;

//http://www.eclipse.org/articles/swt-design-2/sleak.htm
public class LeakExample
{
	static Display display;
	static Shell shell;
	static List list;
	static Canvas canvas;
	static Image image;

	public static void main(String[] args)
	{
		DeviceData data = new DeviceData();
		data.tracking = true;
		display = new Display(data);
		//display = new Display();
		Sleak sleak = new Sleak();
		sleak.open();

		shell = new Shell(display);
		shell.setLayout(new FillLayout());
		list = new List(shell, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
		list.setItems(Program.getExtensions());
		canvas = new Canvas(shell, SWT.BORDER);
		canvas.addPaintListener(new PaintListener()
		{
			public void paintControl(PaintEvent e)
			{
				if(image != null)
				{
					e.gc.drawImage(image, 0, 0);
				}
			}
		});
		list.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				image = null; // potentially leak one image
				String[] selection = list.getSelection();
				if(selection.length != 0)
				{
					Program program = Program.findProgram(selection[0]);
					if(program != null)
					{
						ImageData imageData = program.getImageData();
						if(imageData != null)
						{
							if(image != null)
								image.dispose();
							image = new Image(display, imageData);
						}
					}
				}
				canvas.redraw();
			}
		});
		shell.setSize(shell.computeSize(SWT.DEFAULT, 200));
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
	}
}
