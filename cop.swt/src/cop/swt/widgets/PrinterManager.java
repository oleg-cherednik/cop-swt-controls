package cop.swt.widgets;

import static org.eclipse.swt.SWT.NONE;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Shell;

import static cop.extensions.CommonExt.*;

public class PrinterManager
{
	private Printer printer;
	
	public PrinterData showDialog(Shell shell)
	{
		Assert.isNotNull(shell);
		
		PrintDialog dialog = new PrintDialog(shell, NONE);
		PrinterData data = dialog.open();
		
		printer = isNotNull(data) ? new Printer(data) : null;
		
		return data;
	}
	
	public void print()
	{
		new Thread("Printing")
		{
			@Override
			public void run()
			{
				_print();
				printer.dispose();
				printer = null;
			}
		}.start();
	}
	
	private void _print()
	{
		
	}
}
