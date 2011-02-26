package cop.swt.widgets.segments.primitives.fillable;

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
		return (getScale() <= 1) ? BASE_LENGTH : (BASE_LENGTH * getScale() - getScale() + 1);
	}

	@Override
	protected int getDefaultHeight()
	{
		return (getScale() <= 1) ? (BASE_LENGTH - 1) : ((BASE_LENGTH - 1) * getScale());
	}

	/*
	 * SimpleSegment
	 */

	@Override
	protected int[] getPointArray()
	{
		return createPlus(x, y, width, height, getOrientation());
	}
}
