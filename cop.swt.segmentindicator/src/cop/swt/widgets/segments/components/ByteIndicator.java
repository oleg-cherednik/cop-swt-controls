package cop.swt.widgets.segments.components;

import static org.eclipse.swt.SWT.DEFAULT;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.segments.ByteNumber;

public class ByteIndicator extends SegmentIndicator
{
	public ByteIndicator(Composite parent, int style)
	{
		super(parent, style);
	}
	
	
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

	public static ByteNumber createByteNumber(Canvas canvas, int orientation)
	{
		return new ByteNumber(canvas, orientation, TOTAL_SEGMENTS);
	}

	public static ByteNumber createPositiveByteNumber(Canvas canvas, int orientation)
	{
		ByteNumber obj = new ByteNumber(canvas, orientation, TOTAL_SEGMENTS);

		obj.minimum = 0;

		return obj;
	}

	public static ByteNumber createNegativeByteNumber(Canvas canvas, int orientation)
	{
		ByteNumber obj = new ByteNumber(canvas, orientation, TOTAL_SEGMENTS);

		obj.maximum = 0;

		return obj;
	}
}
