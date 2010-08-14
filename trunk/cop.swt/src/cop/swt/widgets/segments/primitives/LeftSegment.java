package cop.swt.widgets.segments.primitives;

import static cop.swt.widgets.segments.ShapeBasics.createTriangle;
import static org.eclipse.swt.SWT.RIGHT;

public final class LeftSegment extends DrawableSegment
{
	public LeftSegment()
	{
		super(RIGHT);
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
