package cop.swt.widgets.segments.primitives.drawable;

import static cop.swt.widgets.segments.ShapeBasics.createTriangle;
import static org.eclipse.swt.SWT.LEFT;

public final class RightSegment extends DrawableSegment
{
	public static SimpleSegment create()
	{
		return create(false);
	}

	public static SimpleSegment create(boolean invert)
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
		return createTriangle(x, y, width, height, getOrientation());
	}
}
