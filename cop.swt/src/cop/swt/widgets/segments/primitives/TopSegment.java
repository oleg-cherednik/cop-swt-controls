package cop.swt.widgets.segments.primitives;

import static cop.swt.widgets.segments.ShapeBasics.createTriangle;
import static org.eclipse.swt.SWT.DOWN;

public final class TopSegment extends DrawableSegment
{
	public TopSegment()
	{
		super(DOWN);
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
