package cop.swt.widgets.segments.seven;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Shell;

public abstract class NumericSevenSegment extends SevenSegmentIndicator
{
	private static final int NUM_0 = SEG_TOP | SEG_TOP_RIGHT | SEG_BOTTOM_RIGHT | SEG_BOTTOM | SEG_BOTTOM_LEFT
	                | SEG_TOP_LEFT;
	private static final int NUM_1 = SEG_TOP_RIGHT | SEG_BOTTOM_RIGHT;
	private static final int NUM_2 = SEG_TOP | SEG_TOP_RIGHT | SEG_CENTER | SEG_BOTTOM_LEFT | SEG_BOTTOM;
	private static final int NUM_3 = SEG_TOP | SEG_TOP_RIGHT | SEG_BOTTOM_RIGHT | SEG_BOTTOM | SEG_CENTER;
	private static final int NUM_4 = SEG_TOP_LEFT | SEG_CENTER | SEG_TOP_RIGHT | SEG_BOTTOM_RIGHT;
	private static final int NUM_5 = SEG_TOP | SEG_TOP_LEFT | SEG_CENTER | SEG_BOTTOM_RIGHT | SEG_BOTTOM;
	private static final int NUM_6 = SEG_TOP | SEG_TOP_LEFT | SEG_BOTTOM_LEFT | SEG_BOTTOM | SEG_BOTTOM_RIGHT
	                | SEG_CENTER;
	private static final int NUM_7 = SEG_TOP | SEG_TOP_RIGHT | SEG_BOTTOM_RIGHT;
	private static final int NUM_8 = SEG_TOP | SEG_TOP_RIGHT | SEG_BOTTOM_RIGHT | SEG_BOTTOM | SEG_BOTTOM_LEFT
	                | SEG_TOP_LEFT | SEG_CENTER;
	private static final int NUM_9 = SEG_CENTER | SEG_TOP_LEFT | SEG_TOP | SEG_TOP_RIGHT | SEG_BOTTOM_RIGHT;
	private static final int MINUS = SEG_CENTER;

	private static final Map<Character, Integer> numbers;

	static
	{
		numbers = new HashMap<Character, Integer>();

		numbers.put('0', NUM_0);
		numbers.put('1', NUM_1);
		numbers.put('2', NUM_2);
		numbers.put('3', NUM_3);
		numbers.put('4', NUM_4);
		numbers.put('5', NUM_5);
		numbers.put('6', NUM_6);
		numbers.put('7', NUM_7);
		numbers.put('8', NUM_8);
		numbers.put('9', NUM_9);
		numbers.put('-', MINUS);
	}

	public NumericSevenSegment()
	{
		super(null, DEF_ORIENTATION);
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
		if(isNotNull(this.value) && this.value.equals(value))
			return;

		this.value = value;
		redraw();
	}

	/*
	 * AbstractSegmentIndicator
	 */

	@Override
	public void redraw()
	{
		if(isNull(value))
			super.clear();
		else
			_setValue(numbers.get(value));
	}
}
