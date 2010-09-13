package cop.swt.widgets.segments.seven;

import org.eclipse.swt.widgets.Shell;

import cop.common.extensions.NumericExtension;
import cop.common.extensions.StringExtension;

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
	 * SegmentedIndicator
	 */

	@Override
	public void clear()
	{
		value = null;
		super.clear();
	}

	@Override
	public void setValue(Character value)
	{
//		if(value == null)
//		{
//			if(this.value == null)
//		}
		
		if(this.value != null && this.value.equals(value))
			return;

		//if(this.vStringExtension.isNumber(ch))

		this.value = value;
		redraw();
	}

	/*
	 * AbstractSegmentIndicator
	 */

	@Override
	public void redraw()
	{
		if(value == null)
			super.clear();
		else
			_setValue(factory.getSegments(value));
	}
}
