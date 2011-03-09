package cop.swt.widgets.segments.groups.numeric;

import static org.eclipse.swt.SWT.DEFAULT;

import org.eclipse.swt.widgets.Canvas;

public final class IntegerSegment extends NumericSegmentContainer<Integer>
{
	private static final int TOTAL_SEGMENTS = 11;

	public static IntegerSegment create()
	{
		return new IntegerSegment(null, DEFAULT);
	}

	public static IntegerSegment create(Canvas canvas, int orientation)
	{
		return new IntegerSegment(canvas, orientation);
	}

	public static IntegerSegment createPositive(Canvas canvas, int orientation)
	{
		IntegerSegment obj = new IntegerSegment(canvas, orientation);

		obj.minimum = 0;

		return obj;
	}

	public static IntegerSegment createNegative(Canvas canvas, int orientation)
	{
		IntegerSegment obj = new IntegerSegment(canvas, orientation);

		obj.maximum = 0;

		return obj;
	}

	private IntegerSegment(Canvas canvas, int orientation)
	{
		super(canvas, orientation, TOTAL_SEGMENTS);

		minimum = Integer.MIN_VALUE;
		maximum = Integer.MAX_VALUE;
	}
}
