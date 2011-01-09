package cop.swt.widgets.segments.primitives;

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
		return (scale < 2) ? 1 : (1 + (scale - 1) * 2);
	}

	@Override
	protected int[] getPointArray()
	{
		return createRhombus(x, y, width, height, orientation);
	}
}
