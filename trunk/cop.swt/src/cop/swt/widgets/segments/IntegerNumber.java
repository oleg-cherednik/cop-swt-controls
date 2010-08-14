package cop.swt.widgets.segments;

import static org.eclipse.swt.SWT.DEFAULT;

import org.eclipse.swt.widgets.Shell;

public final class IntegerNumber extends NumberSegmentContainer<Integer>
{
	private static final int TOTAL_SEGMENTS = 11;

	public static IntegerNumber createPositiveIntegerNumber()
	{
		return createPositiveIntegerNumber(null, DEFAULT);
	}

	public static IntegerNumber createPositiveIntegerNumber(Shell shell, int orientation)
	{
		IntegerNumber obj = new IntegerNumber(shell, orientation, TOTAL_SEGMENTS - 1);

		obj.minimum = 0;

		return obj;
	}

	public static IntegerNumber createNegativeIntegerNumber()
	{
		return createNegativeIntegerNumber(null, DEFAULT);
	}

	public static IntegerNumber createNegativeIntegerNumber(Shell shell, int orientation)
	{
		IntegerNumber obj = new IntegerNumber(shell, orientation, TOTAL_SEGMENTS);

		obj.maximum = 0;

		return obj;
	}

	public IntegerNumber()
	{
		super(null, DEFAULT, TOTAL_SEGMENTS);
	}

	public IntegerNumber(Shell shell, int orientation)
	{
		super(shell, orientation, TOTAL_SEGMENTS);
	}

	private IntegerNumber(Shell shell, int orientation, int totalSegments)
	{
		super(shell, orientation, totalSegments);

		minimum = Integer.MIN_VALUE;
		maximum = Integer.MAX_VALUE;
	}
}
