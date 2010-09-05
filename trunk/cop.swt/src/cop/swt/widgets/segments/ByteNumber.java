package cop.swt.widgets.segments;

import static org.eclipse.swt.SWT.DEFAULT;

import org.eclipse.swt.widgets.Shell;

public final class ByteNumber extends NumberSegmentContainer<Byte>
{
	private static final int TOTAL_SEGMENTS = 4;

	public static ByteNumber createByteNumber()
	{
		return createByteNumber(null, DEFAULT);
	}

	public static ByteNumber createPositiveByteNumber()
	{
		return createPositiveByteNumber(null, DEFAULT);
	}

	public static ByteNumber createNegativeByteNumber()
	{
		return createNegativeByteNumber(null, DEFAULT);
	}

	public static ByteNumber createByteNumber(Shell shell, int orientation)
	{
		return new ByteNumber(shell, orientation, TOTAL_SEGMENTS);
	}

	public static ByteNumber createPositiveByteNumber(Shell shell, int orientation)
	{
		ByteNumber obj = new ByteNumber(shell, orientation, TOTAL_SEGMENTS - 1);

		obj.minimum = 0;

		return obj;
	}

	public static ByteNumber createNegativeByteNumber(Shell shell, int orientation)
	{
		ByteNumber obj = new ByteNumber(shell, orientation, TOTAL_SEGMENTS);

		obj.maximum = 0;

		return obj;
	}

	private ByteNumber(Shell shell, int orientation, int totalSegments)
	{
		super(shell, orientation, totalSegments);

		minimum = Byte.MIN_VALUE;
		maximum = Byte.MAX_VALUE;
	}
}
