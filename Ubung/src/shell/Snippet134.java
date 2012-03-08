package shell;

/*
 * Shell example snippet: create a non-rectangular window
 *
 * For a list of all SWT example snippets see
 * http://dev.eclipse.org/viewcvs/index.cgi/%7Echeckout%7E/platform-swt-home/dev.html#snippets
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Snippet134 {
	// private static int[] panbet = new int[]
	// {
	//
	// };

	static int[] circle(int r, int offsetX, int offsetY) {
		int[] polygon = new int[8 * r + 4];
		// x^2 + y^2 = r^2
		for (int i = 0; i < 2 * r + 1; i++) {
			int x = i - r;
			int y = (int)Math.sqrt(r * r - x * x);
			polygon[2 * i] = offsetX + x;
			polygon[2 * i + 1] = offsetY + y;
			polygon[8 * r - 2 * i - 2] = offsetX + x;
			polygon[8 * r - 2 * i - 1] = offsetY - y;
		}
		return polygon;
	}

	public static final int X = 67;
	public static final int Y = 67;

	public static void main(String[] args) throws IOException {
		final Display display = new Display();
		// Shell must be created with style SWT.NO_TRIM
		// final Shell shell = new Shell(display, SWT.NO_TRIM | SWT.ON_TOP);
		// shell.setBackground(display.getSystemColor(SWT.COLOR_RED));
		// // define a region that looks like a key hole
		// Region region = new Region();
		//
		// //region.add(panbet);
		//
		ImageData imageData = new ImageData("d:\\Programming\\Eclipse\\ThinkOrSwim1.gif");
		final Image image = new Image(display, imageData);
		// //shell.setBackgroundImage(image);
		//
		// int[] arr = new int[] {200, 100, 500, 100, 600, 200, 500, 300, 200, 300, 100, 200};
		//
		// region.add(arr);
		//
		// //region.add(circle(67, X, Y));
		// //region.subtract(circle(20, X, Y - 17));
		// //region.subtract(new int[] { 67, 50, 55, 105, 79, 105 });
		// // define the shape of the shell using setRegion
		// shell.setRegion(region);
		// Rectangle size = region.getBounds();
		// //Rectangle size = image.getBounds();
		// shell.setSize(size.width, size.height);
		// //shell.setSize(400, 200);
		// // add ability to move shell around

		final Shell shell = new Shell(display, SWT.NO_TRIM);

		final Region region = new Region();
		// final ImageData imageData = image.getImageData();
		if (imageData.alphaData != null) {
			Rectangle pixel = new Rectangle(0, 0, 1, 1);
			for (int y = 0; y < imageData.height; y++) {
				for (int x = 0; x < imageData.width; x++) {
					if (imageData.getAlpha(x, y) == 255) {
						pixel.x = x;
						pixel.y = y;
						region.add(pixel);
					}
				}
			}
		} else {
			ImageData mask = imageData.getTransparencyMask();
			Rectangle pixel = new Rectangle(0, 0, 1, 1);
			for (int y = 0; y < mask.height; y++) {
				for (int x = 0; x < mask.width; x++) {
					if (mask.getPixel(x, y) != 0) {
						pixel.x = x;

						pixel.y = y;

						region.add(pixel);
					}
				}
			}

		}

		shell.setRegion(region);

		Listener l = new Listener() {
			int startX, startY;

			public void handleEvent(Event e) {
				if (e.type == SWT.KeyDown && e.character == SWT.ESC) {
					shell.dispose();
				}
				if (e.type == SWT.MouseDown && e.button == 1) {
					startX = e.x;
					startY = e.y;
				}
				if (e.type == SWT.MouseMove && (e.stateMask & SWT.BUTTON1) != 0) {
					Point p = shell.toDisplay(e.x, e.y);
					p.x -= startX;
					p.y -= startY;
					shell.setLocation(p);
				}
				if (e.type == SWT.Paint) {
					e.gc.drawImage(image, 0, 0);
				}
			}
		};
		shell.addListener(SWT.KeyDown, l);
		shell.addListener(SWT.MouseDown, l);
		shell.addListener(SWT.MouseMove, l);
		shell.addListener(SWT.Paint, l);

		shell.setSize(imageData.width, imageData.height);

		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				region.dispose();
			}

		});

//		Listener lll = new Listener() {
//			Point origin;
//
//			public void handleEvent(Event e) {
//				switch(e.type)
//				{
//				case SWT.MouseDown:
//					origin = new Point(e.x, e.y);
//					break;
//				case SWT.MouseUp:
//					// shell.close();
//					origin = null;
//					break;
//				case SWT.MouseMove:
//					if (origin != null) {
//						Point p = display.map(shell, null, e.x, e.y);
//						shell.setLocation(p.x - origin.x, p.y - origin.y);
//					}
//					break;
//				}
//			}
//		};
//		shell.addListener(SWT.MouseDown, lll);
//		shell.addListener(SWT.MouseUp, lll);
//		shell.addListener(SWT.MouseMove, lll);
		// add ability to close shell
		// Button b = new Button(shell, SWT.PUSH);
		// b.setBackground(shell.getBackground());
		// b.setText("close");
		// b.pack();
		// b.setLocation(10, 10);
		// b.addListener(SWT.Selection, new Listener()
		// {
		// public void handleEvent(Event e)
		// {
		// shell.close();
		// }
		// });
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		region.dispose();
		display.dispose();
	}
}
