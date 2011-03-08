package cop.swt.widgets.segments;

import static org.eclipse.swt.SWT.*;

import org.eclipse.swt.widgets.Shell;

public final class ShortNumber extends NumericSegmentContainer<Short>
{
	private static final int TOTAL_SEGMENTS = 6;

	public static ShortNumber createPositiveIntegerNumber()
	{
		return createPositiveShortNumber(null, DEFAULT);
	}

	public static ShortNumber createPositiveShortNumber(Shell shell, int orientation)
	{
		ShortNumber obj = new ShortNumber(shell, orientation, TOTAL_SEGMENTS - 1);

		obj.minimum = 0;

		return obj;
	}

	public static ShortNumber createNegativeShortNumber()
	{
		return createNegativeShortNumber(null, DEFAULT);
	}

	public static ShortNumber createNegativeShortNumber(Shell shell, int orientation)
	{
		ShortNumber obj = new ShortNumber(shell, orientation, TOTAL_SEGMENTS);

		obj.maximum = 0;

		return obj;
	}

	public ShortNumber()
	{
		super(null, DEFAULT, TOTAL_SEGMENTS);
	}

	public ShortNumber(Shell shell, int orientation)
	{
		this(shell, orientation, TOTAL_SEGMENTS);
	}

	private ShortNumber(Shell shell, int orientation, int totalSegments)
	{
		super(shell, orientation, totalSegments);

		minimum = Short.MIN_VALUE;
		maximum = Short.MAX_VALUE;
	}
}
