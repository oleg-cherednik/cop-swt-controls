package cop.swt.widgets.segments.primitives.fillable;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import cop.swt.widgets.segments.primitives.drawable.SimpleSegment;

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
		gc.fillPolygon(getPoints());
	}
}
