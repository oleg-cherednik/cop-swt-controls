package cop.swt.widgets.segments.groups.numeric;

import static org.eclipse.swt.SWT.DEFAULT;

import org.eclipse.swt.widgets.Canvas;

public final class LongSegment extends NumericSegmentContainer<Long>
{
	private static final int TOTAL_SEGMENTS = 20;

	public static LongSegment create()
	{
		return new LongSegment(null, DEFAULT);
	}

	public static LongSegment create(Canvas canvas, int orientation)
	{
		return new LongSegment(canvas, orientation);
	}

	public static LongSegment createPositive(Canvas canvas, int orientation)
	{
		LongSegment obj = new LongSegment(canvas, orientation);

		obj.minimum = 0L;

		return obj;
	}

	public static LongSegment createNegative(Canvas canvas, int orientation)
	{
		LongSegment obj = new LongSegment(canvas, orientation);

		obj.maximum = 0L;

		return obj;
	}

	private LongSegment(Canvas canvas, int orientation)
	{
		super(canvas, orientation, TOTAL_SEGMENTS);

		minimum = Long.MIN_VALUE;
		maximum = Long.MAX_VALUE;
	}
}
