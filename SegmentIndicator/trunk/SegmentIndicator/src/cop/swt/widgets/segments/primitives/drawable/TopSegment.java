package cop.swt.widgets.segments.primitives.drawable;

import static cop.swt.widgets.segments.ShapeBasics.createTriangle;
import static org.eclipse.swt.SWT.DOWN;

public final class TopSegment extends DrawableSegment
{
	public static SimpleSegment create()
	{
		return create(false);
	}

	public static SimpleSegment create(boolean invert)
	{
		return invert ? new BottomSegment() : new TopSegment();
	}

	TopSegment()
	{
		super(DOWN);
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
