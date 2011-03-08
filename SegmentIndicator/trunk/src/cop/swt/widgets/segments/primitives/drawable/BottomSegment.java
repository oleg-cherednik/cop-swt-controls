package cop.swt.widgets.segments.primitives.drawable;

import static cop.swt.widgets.segments.ShapeBasics.createTriangle;
import static org.eclipse.swt.SWT.UP;

public final class BottomSegment extends DrawableSegment
{
	public static SimpleSegment createSegment()
	{
		return createSegment(false);
	}

	public static SimpleSegment createSegment(boolean invert)
	{
		return invert ? new TopSegment() : new BottomSegment();
	}

	BottomSegment()
	{
		super(UP);
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
