package cop.swt.widgets;

import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

public class MyDialog extends AbstractDialog
{
	public MyDialog(Shell parentShell)
	{
		super(parentShell);
//		
//		isCancel = false;
//		
//		setShellStyle(getShellStyle() & SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.MAX | SWT.MIN);
	}
}
