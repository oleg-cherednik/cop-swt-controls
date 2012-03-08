/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets;

/*
 *
 * For a list of all SWT example snippets see
 * http://dev.eclipse.org/viewcvs/index.cgi/%7Echeckout%7E/platform-swt-home/dev.html#snippets
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Oleg Cherednik
 * @since 08.03.2012
 */
public class ImageWindow extends Shell implements Listener {
	private final Image image;

	private int x;
	private int y;

	public ImageWindow(Display display, ImageData imageData) {
		super(display, SWT.NO_TRIM);

		this.image = new Image(display, imageData);

		super.setRegion(AlphaChannel.parseChannel(imageData).createRegion(imageData));
		super.setSize(imageData.width, imageData.height);

		addListeners();
	}

	private void addListeners() {
		addListener(SWT.KeyDown, this);
		addListener(SWT.MouseDown, this);
		addListener(SWT.MouseMove, this);
		addListener(SWT.Paint, this);
	}

	private void onMouseMove(int x, int y) {
		Point point = toDisplay(x, y);

		point.x -= this.x;
		point.y -= this.y;

		setLocation(point);
	}

	private void onMouseDown(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/*
	 * Control
	 */

	@Override
	@Deprecated
	@SuppressWarnings("unused")
	public void setSize(int width, int height) {}

	/*
	 * Shell
	 */

	@Override
	@Deprecated
	@SuppressWarnings("unused")
	public void setRegion(Region region) {}

	/*
	 * Widget
	 */

	@Override
	public void dispose() {
		getRegion().dispose();
		super.dispose();
	}

	/*
	 * Decorations
	 */

	@Override
	protected void checkSubclass() {}

	/*
	 * Listener
	 */

	public void handleEvent(Event e) {
		if (e.type == SWT.KeyDown && e.character == SWT.ESC)
			dispose();
		else if (e.type == SWT.MouseDown && e.button == 1)
			onMouseDown(e.x, e.y);
		else if (e.type == SWT.MouseMove && (e.stateMask & SWT.BUTTON1) != 0)
			onMouseMove(e.x, e.y);
		else if (e.type == SWT.Paint)
			e.gc.drawImage(image, 0, 0);
	}

	/*
	 * enum
	 */

	private enum AlphaChannel {
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

		public final Region createRegion(ImageData imageData) {
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
	}
}
