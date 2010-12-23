package gc;

/*
 * GC example snippet: create an icon (in memory)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
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

//		PaletteData palette = new PaletteData(new RGB[] { new RGB(0, 0, 0), new RGB(0xFF, 0xFF, 0xFF), });
//		ImageData maskData = new ImageData(20, 20, 1, palette);
//		Image mask = new Image(display, maskData);
//		gc = new GC(mask);
//		gc.setBackground(black);
//		gc.fillRectangle(0, 0, 20, 20);
//		gc.setBackground(white);
//		gc.fillRectangle(5, 5, 10, 10);
//		gc.dispose();
//		maskData = mask.getImageData();

		//Image icon = new Image(display, imageData, maskData);
		Shell shell = new Shell(display);
		//Button button = new Button(shell, SWT.PUSH);
		//button.setImage(icon);
		//button.setSize(25, 25);
		
		Region region = createRegion(imageData);
		
		
		shell.setRegion(region);
		shell.setSize(imageData.width, imageData.height);
		
		
		shell.open();
		
		
		
		
		
		
		
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		//icon.dispose();
		image.dispose();
		//mask.dispose();
		display.dispose();
	}
	
	public static Region createRegion(ImageData imageData)
	{
		Region region = new Region();

		if(imageData.alphaData != null)
		{
			for(int y = 0; y < imageData.height; y++)
				for(int x = 0; x < imageData.width; x++)
					if(imageData.getAlpha(x, y) == 255)
						region.add(new Rectangle(x, y, 1, 1));
		}
		else
		{
			ImageData mask = imageData.getTransparencyMask();

			for(int y = 0; y < mask.height; y++)
				for(int x = 0; x < mask.width; x++)
					if(mask.getPixel(x, y) != 0)
						region.add(new Rectangle(x, y, 1, 1));
		}

		return region;
	}
}
