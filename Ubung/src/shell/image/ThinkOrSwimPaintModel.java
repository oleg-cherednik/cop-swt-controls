package shell.image;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

public final class ThinkOrSwimPaintModel implements PaintModel {
	public static final ThinkOrSwimPaintModel INCTANCE = new ThinkOrSwimPaintModel();

	private static final Color LIGHT_GREEN = new Color(Display.getCurrent(), 0, 182, 37);
	private static final Color DARK_GREEN = new Color(Display.getCurrent(), 21, 61, 34);

	private static final int X1 = 114;
	private static final int X2 = 163;
	private static final int X3 = 201;
	private static final int Y = 117;

	private ThinkOrSwimPaintModel() {}

	/*
	 * PaintModel
	 */

	@Override
	public void paint(GC gc, int length, int width, int height) {
		gc.setBackground(LIGHT_GREEN);
		gc.fillRectangle(0, 0, length, height);

		if (length > X1) {
			gc.setBackground(DARK_GREEN);
			gc.fillRectangle(X1, 0, length - X1, height);
		}
		if (length > X2) {
			gc.setBackground(DARK_GREEN);
			gc.fillRectangle(X1, 0, length - X2, Y);

			gc.setBackground(LIGHT_GREEN);
			gc.fillRectangle(X2, Y, length - X2, height - Y);
		}
		if (length > X3) {
			gc.setBackground(DARK_GREEN);
			gc.fillRectangle(X3, 0, length - X3, height);
		}
	}
}
