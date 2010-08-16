package cop.swt.widgets.segments;

import static org.eclipse.swt.SWT.DEFAULT;

import org.eclipse.swt.widgets.Shell;

public final class LongNumber extends NumberSegmentContainer<Long>
{
	private static final int TOTAL_SEGMENTS = 20;

	public static LongNumber createPositiveLongNumber()
	{
		return createPositiveLongNumber(null, DEFAULT);
	}

	public static LongNumber createPositiveLongNumber(Shell shell, int orientation)
	{
		LongNumber obj = new LongNumber(shell, orientation, TOTAL_SEGMENTS - 1);

		obj.minimum = 0L;

		return obj;
	}

	public static LongNumber createNegativeLongNumber()
	{
		return createNegativeLongNumber(null, DEFAULT);
	}

	public static LongNumber createNegativeLongNumber(Shell shell, int orientation)
	{
		LongNumber obj = new LongNumber(shell, orientation, TOTAL_SEGMENTS);

		obj.maximum = 0L;

		return obj;
	}

	public LongNumber()
	{
		super(null, DEFAULT, TOTAL_SEGMENTS);
	}

	public LongNumber(Shell shell, int orientation)
	{
		super(shell, orientation, TOTAL_SEGMENTS);
	}

	private LongNumber(Shell shell, int orientation, int totalSegments)
	{
		super(shell, orientation, totalSegments);

		minimum = Long.MIN_VALUE;
		maximum = Long.MAX_VALUE;
	}
}
