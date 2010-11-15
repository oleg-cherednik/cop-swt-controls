package image;

/*
 * GC example snippet: create an icon (in memory)
 *
 * For a list of all SWT example snippets see
 * http://dev.eclipse.org/viewcvs/index.cgi/%7Echeckout%7E/platform-swt-home/dev.html#snippets
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Snippet70
{

	public static void main(String[] args)
	{
		Display display = new Display();
		Color red = display.getSystemColor(SWT.COLOR_RED);
		Color white = display.getSystemColor(SWT.COLOR_WHITE);
		Color black = display.getSystemColor(SWT.COLOR_BLACK);

		Image image = new Image(display, 20, 20);
		GC gc = new GC(image);
		gc.setBackground(red);
		gc.fillRectangle(5, 5, 10, 10);
		gc.dispose();
		ImageData imageData = image.getImageData();

		PaletteData palette = new PaletteData(new RGB[] { new RGB(0, 0, 0), new RGB(0xFF, 0xFF, 0xFF), });
		ImageData maskData = new ImageData(20, 20, 1, palette);
		Image mask = new Image(display, maskData);
		gc = new GC(mask);
		gc.setBackground(black);
		gc.fillRectangle(0, 0, 20, 20);
		gc.setBackground(white);
		gc.fillRectangle(5, 5, 10, 10);
		gc.dispose();
		maskData = mask.getImageData();

		Image icon = new Image(display, imageData, maskData);
		Shell shell = new Shell(display);
		Button button = new Button(shell, SWT.PUSH);
		button.setImage(icon);
		button.setSize(60, 60);
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		icon.dispose();
		image.dispose();
		mask.dispose();
		display.dispose();
	}
}
