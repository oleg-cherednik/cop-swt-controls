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
	protected int getDefaultWidth()
	{
		return (scale <= 1) ? BASE_LENGTH : (BASE_LENGTH * scale - scale + 1);
	}

	@Override
	protected int getDefaultHeight()
	{
		return (scale <= 1) ? (BASE_LENGTH - 1) : ((BASE_LENGTH - 1) * scale);
	}

	/*
	 * SimpleSegment
	 */

	@Override
	protected int[] getPointArray()
	{
		return createPlus(x, y, width, height, orientation);
	}
}
