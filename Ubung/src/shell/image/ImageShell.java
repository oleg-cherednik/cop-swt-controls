/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id: ImageShell.java 383 2012-03-08 14:54:27Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.swt/src/cop/swt/widgets/ImageShell.java $
 */
package shell.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

/**
 * @author Oleg Cherednik
 * @since 08.03.2012
 */
public class ImageShell extends RegionShell {
	private final Image image;

	public ImageShell(Display display, Region region, int width, int height) {
		super(display, region, width, height);

		this.image = null;
	}

	public ImageShell(Display display, ImageData imageData) {
		super(display, AlphaChannel.createRegion(imageData), imageData.width, imageData.height);

		this.image = new Image(display, imageData);

		addListeners();
	}

	private void addListeners() {
		addListener(SWT.Paint, this);
	}

	protected void onPaint(GC gc) {
		gc.drawImage(image, 0, 0);
	}

	/*
	 * Listener
	 */

	@Override
	public void handleEvent(Event e) {
		if (e.type == SWT.Paint)
			onPaint(e.gc);
		else
			super.handleEvent(e);
	}

	/*
	 * enum
	 */

	public enum AlphaChannel {
		NONE {
			@Override
			protected boolean isPixelVisible(int x, int y, ImageData imageData) {
				return imageData.getPixel(x, y) != 0;
			}
		},
		ALPHA {
			@Override
			protected boolean isPixelVisible(int x, int y, ImageData imageData) {
				return imageData.getAlpha(x, y) == 0xFF;
			}
		};

		protected abstract boolean isPixelVisible(int x, int y, ImageData imageData);

		private final Region _createRegion(ImageData imageData) {
			Region region = new Region();
			ImageData mask = imageData.getTransparencyMask();

			for (int y = 0; y < mask.height; y++)
				for (int x = 0; x < mask.width; x++)
					if (isPixelVisible(x, y, mask))
						region.add(x, y, 1, 1);

			return region;
		}

		public static AlphaChannel parseChannel(ImageData imageData) {
			return (imageData.alphaData != null) ? ALPHA : NONE;
		}

		public static Region createRegion(ImageData imageData) {
			return parseChannel(imageData)._createRegion(imageData);
		}
	}
}
