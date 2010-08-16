package cop.swt.widgets.interfaces;

import org.eclipse.swt.graphics.Color;

/**
 * @author cop (Cherednik, Oleg)
 * @version 1.0.0 (05.01.2010)
 */
public interface IColorChangeable
{
	Color getBackgroundColor(String str);

	Color getForegroundColor(String str);
}
