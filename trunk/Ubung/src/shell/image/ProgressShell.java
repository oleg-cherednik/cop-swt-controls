/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id: ImageShell.java 383 2012-03-08 14:54:27Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.swt/src/cop/swt/widgets/ImageShell.java $
 */
package shell.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Display;

/**
 * @author Oleg Cherednik
 * @since 08.03.2012
 */
public class ProgressShell extends ImageShell implements PaintModel {
	private static final Color RED = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
	private static final Color YELLOW = Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);

	private Integer selection;
	private PaintModel paintModel = this;

	public ProgressShell(Display display, Region region, int width, int height) {
		super(display, region, width, height);
	}

	public ProgressShell(Display display, ImageData imageData) {
		super(display, imageData);
	}

	public void setSelection(Integer selection) {
		this.selection = selection;
		redraw();
	}

	public void setPaintModel(PaintModel paintModel) {
		if (this.paintModel != paintModel) {
			this.paintModel = (paintModel != null) ? paintModel : this;
			redraw();
		}
	}

	/*
	 * ImageShell
	 */

	@Override
	protected void onPaint(GC gc) {
		if (selection == null)
			super.onPaint(gc);
		else {
			Color foreground = gc.getForeground();
			Color background = gc.getBackground();

			Rectangle bounds = getBounds();
			int length = getLength(selection, bounds.width);
			paintModel.paint(gc, length, bounds.width, bounds.height);

			gc.setForeground(foreground);
			gc.setBackground(background);
		}
	}

	/*
	 * PaintModel
	 */

	public void paint(GC gc, int length, int width, int height) {
		gc.setForeground(RED);
		gc.setBackground(YELLOW);

		gc.fillGradientRectangle(0, 0, length, height, false);
		// gc.fillRectangle(0, 0, length, height);
	}

	/*
	 * static
	 */

	private static int getLength(int selection, int width) {
		if (selection <= 0)
			return 0;
		if (selection >= 100)
			return width;
		return (width * selection) / 100;
	}
}
