package cop.swt.widgets.segments.primitives.drawable;

import static cop.swt.widgets.segments.ShapeBasics.createTriangle;
import static org.eclipse.swt.SWT.RIGHT;

public final class LeftSegment extends DrawableSegment
{
	public static SimpleSegment createSegment()
	{
		return createSegment(false);
	}

	public static SimpleSegment createSegment(boolean invert)
	{
		return invert ? new RightSegment() : new LeftSegment();
	}

	LeftSegment()
	{
		super(RIGHT);
	}

	/*
	 * SimpleSegment
	 */

	@Override
	protected int[] getPointArray()
	{
		return createTriangle(x, y, width, height, getOrientation());
	}
}
