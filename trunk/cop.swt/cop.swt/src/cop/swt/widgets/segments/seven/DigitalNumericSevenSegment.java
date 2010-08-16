package cop.swt.widgets.segments.seven;

import org.eclipse.swt.widgets.Shell;

public class DigitalNumericSevenSegment extends NumericSevenSegment
{
	public DigitalNumericSevenSegment()
	{}

	public DigitalNumericSevenSegment(Shell shell)
	{
		super(shell, DEF_ORIENTATION);
	}

	public DigitalNumericSevenSegment(Shell shell, int orientation)
	{
		super(shell, orientation);
	}

	public DigitalNumericSevenSegment(int x, int y, int scale)
	{
		this(null, DEF_ORIENTATION, x, y, scale);
	}
	
	public DigitalNumericSevenSegment(Shell shell, int x, int y, int scale)
	{
		this(shell, DEF_ORIENTATION, x, y, scale);
	}

	public DigitalNumericSevenSegment(Shell shell, int orientation, int x, int y, int scale)
	{
		super(shell, DEF_ORIENTATION);

		setBounds(x, y, scale);
	}
}
