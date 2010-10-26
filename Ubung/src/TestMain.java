import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class TestMain
{
	public static void main(String[] args)
	{
		Display display = Display.getDefault();
		Image image = new Image(display, "c:\\advertisement.gif"); // filename
		Shell shell = new Shell(SWT.NO_TRIM);
		shell.setBounds(10, 10, 200, 200);
		shell.setBackgroundImage(image);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Label label = new Label(shell, SWT.NONE);
		label.setText("abdfghl;sdfjksdfg");
		label.setBounds(10, 10, 100, 100);
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		image.dispose();
		display.dispose();
	}
}
