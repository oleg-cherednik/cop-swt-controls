/**
 * @licence GNU Leser General Public License
 * @author cop
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.extensions;

import static cop.common.extensions.CommonExtension.isNull;
import static cop.swt.extensions.ColorExtension.*;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;

public class GraphicsExtension
{
	private GraphicsExtension()
	{}

	public static Region createRegion(ImageData imageData)
	{
		Region region = new Region();

		if(isNull(imageData))
			return region;

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
						region.add(new Rectangle(x + 6, y + 5, 1, 1));
		}

		return region;
	}

	public static void drawProgressBar(GC gc, int x, int y, int width, int height, double value)
	{
		Color foreground = gc.getForeground();
		Color background = gc.getBackground();

		gc.setForeground(RED);
		gc.setBackground(YELLOW);

		int len = (int)((width * value) / 100);

		gc.fillGradientRectangle(x, y, len, height, true);
		// gc.fillRectangle(event.x, event.y, len, event.height);
		// gc.fillRoundRectangle(event.x, event.y, len, event.height, 10, 10);
		// gc.drawRectangle(event.x, event.y, width - 1, event.height - 1);
		gc.setForeground(background);
		gc.setBackground(foreground);
	}
}
