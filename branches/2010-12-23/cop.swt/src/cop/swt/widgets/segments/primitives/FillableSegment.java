package cop.swt.widgets.segments.primitives;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public abstract class FillableSegment extends SimpleSegment
{
	public FillableSegment(int orientation)
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

		gc.setBackground(color);
		gc.fillPolygon(points);
	}

}
