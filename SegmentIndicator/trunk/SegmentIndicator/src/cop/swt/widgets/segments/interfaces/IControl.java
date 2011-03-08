package cop.swt.widgets.segments.interfaces;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public interface IControl
{
	Rectangle getBounds();

	Point getSize();

	boolean isVisible();

	void setVisible(boolean visible);

	void dispose();

	void setForeground(Color color);

	Color getForeground();
}
