package cop.swt.widgets.segments.primitives.drawable;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import cop.swt.widgets.segments.primitives.BasicShape;

public abstract class DrawableSegment extends SimpleSegment {
	public DrawableSegment(BasicShape basicShape, int orientation) {
		super(basicShape, orientation);
	}

	/*
	 * IShape
	 */

	@Override
	public void draw(GC gc, Color color) {
		if (gc == null || color == null || gc.isDisposed())
			return;

		gc.setForeground(color);
		gc.drawPolygon(getPoints());
	}
}
