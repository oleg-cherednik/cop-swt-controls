package cop.swt.widgets.segments.primitives;

import static cop.swt.widgets.segments.ShapeBasics.createRectangle;
import static org.eclipse.swt.SWT.HORIZONTAL;

public final class MinusSegment extends FillableSegment
{
	public MinusSegment()
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
		return scale;
	}

	/*
	 * SimpleSegment
	 */

	@Override
	protected int[] getPointArray()
	{
		return createRectangle(x, y, width, height);
	}
}
