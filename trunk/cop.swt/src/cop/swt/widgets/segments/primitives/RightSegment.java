package cop.swt.widgets.segments.primitives;

import static cop.swt.widgets.segments.ShapeBasics.createTriangle;
import static org.eclipse.swt.SWT.LEFT;

public final class RightSegment extends DrawableSegment
{
	public RightSegment()
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
