package shell;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import cop.swt.widgets.ImageWindow;

public class ImageWindowExample {
	public static void main(String[] args) {
		Display display = new Display();
		ImageData imageData = new ImageData("d:\\Programming\\Eclipse\\ThinkOrSwim1.gif");

		ImageWindow imageWindow = new ImageWindow(display, imageData);

		imageWindow.setLocation(67, 67);
		imageWindow.open();

		while (!imageWindow.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
	}
}
