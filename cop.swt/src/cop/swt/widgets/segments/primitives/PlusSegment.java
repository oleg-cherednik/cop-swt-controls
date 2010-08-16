package cop.swt.widgets.segments.primitives;

import static cop.swt.widgets.segments.ShapeBasics.createPlus;
import static org.eclipse.swt.SWT.HORIZONTAL;

public final class PlusSegment extends FillableSegment
{
	public PlusSegment()
	{
		super(HORIZONTAL);
	}

	/*
	 * AbstractSegment
	 */

	@Override
	@Deprecated
	public void setOrientation(int orientation)
	{}

	@Override
	protected int getDefaultHeight()
	{
		return getDefaultWidth();
	}

	/*
	 * SimpleSegment
	 */
	
	@Override
	protected int[] getPointArray()
	{
		return createPlus(x, y, width);
	}
}
