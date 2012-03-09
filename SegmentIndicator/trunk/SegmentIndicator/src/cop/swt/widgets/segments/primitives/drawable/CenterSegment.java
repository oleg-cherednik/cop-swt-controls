package cop.swt.widgets.segments.primitives.drawable;

import cop.swt.widgets.segments.primitives.RombusShape;

public final class CenterSegment extends DrawableSegment {
	public static SimpleSegment create(int orientation) {
		return new CenterSegment(orientation);
	}

	CenterSegment(int orientation) {
		super(orientation);
	}

	/*
	 * SimpleSegment
	 */

	@Override
	protected int getDefaultHeight() {
		return (getScale() < 2) ? 1 : (1 + (getScale() - 1) * 2);
	}

	@Override
	protected int[] getPointArray() {
		return RombusShape.create().getShape(x, y, width, height, getOrientation());
	}
}
