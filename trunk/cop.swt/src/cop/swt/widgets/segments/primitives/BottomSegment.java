package cop.swt.widgets.segments.primitives;

import static cop.swt.widgets.segments.ShapeBasics.createTriangle;
import static org.eclipse.swt.SWT.UP;

public final class BottomSegment extends DrawableSegment
{
	public BottomSegment()
	{
		super(UP);
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
