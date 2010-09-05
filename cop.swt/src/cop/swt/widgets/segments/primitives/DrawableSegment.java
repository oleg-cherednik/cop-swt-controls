package cop.swt.widgets.segments.primitives;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public abstract class DrawableSegment extends SimpleSegment
{
	public DrawableSegment(int orientation)
	{
		super(orientation);
	}

	/*
	 * IShape
	 */

	@Override
	public void draw(GC gc, Color color)
	{
		if(gc == null || color == null || gc.isDisposed())
			return;

		gc.setForeground(color);
		gc.drawPolygon(pointArray);
	}
}
