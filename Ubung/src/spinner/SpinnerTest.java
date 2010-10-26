package spinner;

/*
 * (c) Copyright IBM Corp. 2000, 2001. All Rights Reserved.
 */

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

public class SpinnerTest
{

	public static void main(String[] args)
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new RowLayout());

		final Spinner spinner = new Spinner(shell, 0);
		spinner.setMaximum(999);
		System.out.println("max set to " + spinner.getMaximum());
		spinner.setSelection(500);
		System.out.println("selection set to " + spinner.getSelection());
		spinner.setMinimum(100);
		System.out.println("min set to " + spinner.getMinimum());
		Font font = new Font(display, "Courier", 20, SWT.NORMAL);
		spinner.setFont(font);
		spinner.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				System.out.println(spinner.getSelection());
			}
		});
		shell.pack();
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		font.dispose();
	}
}

//class Spinner extends Composite
//{
//	int handleSpinner;
//
//	static Hashtable table = new Hashtable();
//	static
//	{
//		System.loadLibrary("spinner");
//	}

//	public Spinner(Composite parent, int style)
//	{
//		super(parent, style);
//		int handleParent = handle;
//		handleSpinner = createControl(handleParent);
//		if(handleSpinner == 0)
//			SWT.error(SWT.ERROR_NO_HANDLES);
//		table.put(new Integer(handleSpinner), this);
//		addDisposeListener(new DisposeListener()
//		{
//			public void widgetDisposed(DisposeEvent e)
//			{
//				Spinner.this.widgetDisposed(e);
//			}
//		});
//		addControlListener(new ControlAdapter()
//		{
//			@Override
//			public void controlResized(ControlEvent e)
//			{
//				Spinner.this.controlResized(e);
//			}
//		});
//		addFocusListener(new FocusAdapter()
//		{
//			@Override
//			public void focusGained(FocusEvent e)
//			{
//				Spinner.this.focusGained(e);
//			}
//		});
//		Font font = getFont();
//		setFont(handleSpinner, font.handle);
//	}
//
//	@Override
//	public void setFont(Font font)
//	{
//		super.setFont(font);
//		int hFont = 0;
//		if(font != null)
//			hFont = font.handle;
//		setFont(handleSpinner, hFont);
//	}
//
//	public int getSelection()
//	{
//		checkWidget();
//		return getPosition(handleSpinner);
//	}
//
//	public void setSelection(int selection)
//	{
//		checkWidget();
//		setPosition(handleSpinner, selection);
//	}
//
//	public void setMaximum(int maximum)
//	{
//		checkWidget();
//		setMaximum(handleSpinner, maximum);
//	}
//
//	public int getMaximum()
//	{
//		checkWidget();
//		return getMaximum(handleSpinner);
//	}
//
//	public void setMinimum(int minimum)
//	{
//		checkWidget();
//		setMinimum(handleSpinner, minimum);
//	}
//
//	public int getMinimum()
//	{
//		checkWidget();
//		return getMinimum(handleSpinner);
//	}
//
//	public void widgetDisposed(DisposeEvent e)
//	{
//		table.remove(new Integer(handleSpinner));
//		handleSpinner = 0;
//	}
//
//	public void controlResized(ControlEvent e)
//	{
//		Rectangle rect = getClientArea();
//		resizeControl(handleSpinner, rect.x, rect.y, rect.width, rect.height);
//	}
//
//	public void focusGained(FocusEvent e)
//	{
//		setFocus(handleSpinner);
//	}
//
//	@Override
//	public Point computeSize(int wHint, int hHint, boolean changed)
//	{
//		checkWidget();
//		int[] result = new int[2];
//		computeSize(handleSpinner, result);
//		if(wHint != SWT.DEFAULT)
//			result[0] = wHint;
//		if(hHint != SWT.DEFAULT)
//			result[1] = hHint;
//		int border = getBorderWidth();
//		return new Point(result[0] + border * 2, result[1] + border * 2);
//	}
//
//	public void addSelectionListener(SelectionListener listener)
//	{
//		if(listener == null)
//			throw new SWTError(SWT.ERROR_NULL_ARGUMENT);
//		addListener(SWT.Selection, new TypedListener(listener));
//	}
//
//	static void widgetSelected(int handle)
//	{
//		Spinner spinner = (Spinner)table.get(new Integer(handle));
//		if(spinner == null)
//			return;
//		spinner.notifyListeners(SWT.Selection, new Event());
//	}
//
//	/** ********* JAVA NATIVES *********** */
//
//	static final native int createControl(int handle);
//
//	static final native void computeSize(int handle, int[] result);
//
//	static final native void resizeControl(int handle, int x, int y, int width, int height);
//
//	static final native void setPosition(int handle, int position);
//
//	static final native int getPosition(int handle);
//
//	static final native void setMaximum(int handle, int max);
//
//	static final native int getMaximum(int handle);
//
//	static final native void setMinimum(int handle, int min);
//
//	static final native int getMinimum(int handle);
//
//	static final native void setFont(int handle, int hFont);
//
//	static final native void setFocus(int handle);
//}
