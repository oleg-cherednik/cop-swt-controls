/**
 * @licence GNU Leser General Public License
 * @author cop
 * 
 * $Id: GraphicsExtension.java 51 2010-08-16 12:25:56Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.swt/cop.swt/src/cop/swt/extensions/GraphicsExtension.java $
 */
package cop.swt.extensions;

import static cop.common.extensions.CommonExtension.isNull;

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
}
