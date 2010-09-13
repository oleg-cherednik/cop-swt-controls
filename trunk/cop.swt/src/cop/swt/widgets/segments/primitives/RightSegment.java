package cop.swt.widgets.segments.primitives;

import static cop.swt.widgets.segments.ShapeBasics.createTriangle;
import static org.eclipse.swt.SWT.LEFT;

public final class RightSegment extends DrawableSegment
{
	public static SimpleSegment createSegment()
	{
		return createSegment(false);
	}

	public static SimpleSegment createSegment(boolean invert)
	{
		return invert ? new LeftSegment() : new RightSegment();
	}

	RightSegment()
	{
		super(LEFT);
	}

	/*
	 * SimpleSegment
	 */

	@Override
	protected int[] getPointArray()
	{
		return createTriangle(x, y, width, height, orientation);
	}
}
