package cop.swt.widgets.segments.tmp.factories;

import java.util.HashMap;
import java.util.Map;

public abstract class BasicSegmentSymbolFactory implements SegmentSymbolFactory
{
	protected static final int SEG_NONE = 0x0;

	protected static final int SEG_TOP_SIDE_LEFT = 0x1;
	protected static final int SEG_TOP_SIDE_RIGHT = 0x2;
	protected static final int SEG_BOTTOM_SIDE_LEFT = 0x4;
	protected static final int SEG_BOTTOM_SIDE_RIGHT = 0x8;

	// symbols is what we see on the segment indicator
	protected static final Map<Character, Integer> symbols = new HashMap<Character, Integer>();

	static
	{
		symbols.put('1', SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT);
	}

	protected int getAll()
	{
		return SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT | SEG_BOTTOM_SIDE_LEFT | SEG_TOP_SIDE_LEFT;
	}

	/*
	 * SegmentSymbolFactory
	 */

	// return bit mask
	@Override
	public final int getSegments(Character value)
	{
		return symbols.containsKey(value) ? symbols.get(value) : SEG_NONE;
	}
}
