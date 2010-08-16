package cop.swt.widgets.segments.primitives;

import static cop.common.extensions.CommonExtension.isNull;

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
		if(isNull(gc) || isNull(color) || gc.isDisposed())
			return;

		gc.setForeground(color);
		gc.drawPolygon(pointArray);
	}
}
