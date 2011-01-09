package cop.swt.widgets.segments.seven;

import static cop.common.extensions.StringExtension.isNumber;

import org.eclipse.swt.widgets.Shell;

public abstract class NumericSevenSegment extends SevenSegmentIndicator
{
	public NumericSevenSegment()
	{
		super(null, DEFAULT_ORIENTATION);
	}

	public NumericSevenSegment(Shell shell, int orientation)
	{
		super(shell, orientation);
	}

	/*
	 * IControl
	 */

	@Override
	public void clear()
	{
		value = null;
		super.clear();
	}

	/*
	 * AbstractSegmentIndicator
	 */

	@Override
	public boolean isValueValid(Character value)
	{
		return value == null || isNumber(value.charValue()) || value == '-';
	}

	@Override
	public void redraw()
	{
		if(value == null)
			super.clear();
		else
			_setValue(factory.getSegments(value));
	}
}
