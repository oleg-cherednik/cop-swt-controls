package cop.swt.widgets.segments.seven;

import org.eclipse.swt.widgets.Canvas;

public class DigitalNumericSevenSegment extends NumericSevenSegment
{
	public DigitalNumericSevenSegment()
	{}

	public DigitalNumericSevenSegment(Canvas canvas)
	{
		super(canvas, DEFAULT_ORIENTATION);
	}

	public DigitalNumericSevenSegment(Canvas canvas, int orientation)
	{
		super(canvas, orientation);
	}

	public DigitalNumericSevenSegment(int x, int y, int scale)
	{
		this(null, DEFAULT_ORIENTATION, x, y, scale);
	}

	public DigitalNumericSevenSegment(Canvas canvas, int x, int y, int scale)
	{
		this(canvas, DEFAULT_ORIENTATION, x, y, scale);
	}

	public DigitalNumericSevenSegment(Canvas canvas, int orientation, int x, int y, int scale)
	{
		super(canvas, DEFAULT_ORIENTATION);

		setBounds(x, y, scale);
	}
}
