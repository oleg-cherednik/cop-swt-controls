package cop.swt.widgets.segments.primitives.drawable;

import static cop.swt.widgets.segments.ShapeBasics.createRhombus;

public final class CenterSegment extends DrawableSegment
{
	public static SimpleSegment createSegment(int orientation)
	{
		return new CenterSegment(orientation);
	}

	CenterSegment(int orientation)
	{
		super(orientation);
	}

	/*
	 * SimpleSegment
	 */

	@Override
	protected int getDefaultHeight()
	{
		return (getScale() < 2) ? 1 : (1 + (getScale() - 1) * 2);
	}

	@Override
	protected int[] getPointArray()
	{
		return createRhombus(x, y, width, height, getOrientation());
	}
}
