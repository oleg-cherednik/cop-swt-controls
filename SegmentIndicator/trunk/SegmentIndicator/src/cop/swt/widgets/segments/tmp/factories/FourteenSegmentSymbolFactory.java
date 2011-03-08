package cop.swt.widgets.segments.tmp.factories;

public class FourteenSegmentSymbolFactory extends BasicSegmentSymbolFactory
{
	public static final int SEG_TOP_LEFT = 0x10;
	public static final int SEG_TOP_RIGHT = 0x20;
	public static final int SEG_TOP_SLASH_LEFT = 0x40;
	public static final int SEG_TOP_SLASH_RIGHT = 0x80;

	public static final int SEG_BOTTOM_LEFT = 0x100;
	public static final int SEG_BOTTOM_RIGHT = 0x200;
	public static final int SEG_BOTTOM_SLASH_LEFT = 0x400;
	public static final int SEG_BOTTOM_SLASH_RIGHT = 0x800;

	public static final int SEG_CENTER_LEFT = 0x1000;
	public static final int SEG_CENTER_RIGHT = 0x2000;

	static
	{
		symbols.put('0', SEG_TOP_LEFT | SEG_TOP_RIGHT | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT | SEG_BOTTOM_RIGHT
		                | SEG_BOTTOM_LEFT | SEG_BOTTOM_SIDE_LEFT | SEG_TOP_SIDE_LEFT | SEG_TOP_SLASH_RIGHT
		                | SEG_BOTTOM_SLASH_LEFT);
		symbols.put('1', SEG_TOP_SLASH_RIGHT | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT);
		symbols.put('2', SEG_TOP_LEFT | SEG_TOP_RIGHT | SEG_TOP_SIDE_RIGHT | SEG_CENTER_RIGHT | SEG_CENTER_LEFT
		                | SEG_BOTTOM_SIDE_LEFT | SEG_BOTTOM_LEFT | SEG_BOTTOM_RIGHT);
		symbols.put('3', SEG_TOP_LEFT | SEG_TOP_RIGHT | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT | SEG_BOTTOM_RIGHT
		                | SEG_BOTTOM_LEFT | SEG_CENTER_RIGHT | SEG_CENTER_LEFT);
		symbols.put('4', SEG_TOP_SIDE_LEFT | SEG_CENTER_LEFT | SEG_CENTER_RIGHT | SEG_TOP_SIDE_RIGHT
		                | SEG_BOTTOM_SIDE_RIGHT);
		symbols.put('5', SEG_TOP_RIGHT | SEG_TOP_LEFT | SEG_TOP_SIDE_LEFT | SEG_CENTER_LEFT | SEG_BOTTOM_SLASH_RIGHT
		                | SEG_BOTTOM_RIGHT | SEG_BOTTOM_LEFT);
		symbols.put('6', SEG_TOP_RIGHT | SEG_TOP_LEFT | SEG_TOP_SIDE_LEFT | SEG_BOTTOM_SIDE_LEFT | SEG_BOTTOM_LEFT
		                | SEG_BOTTOM_RIGHT | SEG_BOTTOM_SIDE_RIGHT | SEG_CENTER_RIGHT | SEG_CENTER_LEFT);
		symbols.put('7', SEG_TOP_LEFT | SEG_TOP_RIGHT | SEG_TOP_SLASH_RIGHT | SEG_BOTTOM_SLASH_LEFT);
		symbols.put('8', SEG_TOP_LEFT | SEG_TOP_RIGHT | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT | SEG_BOTTOM_RIGHT
		                | SEG_BOTTOM_LEFT | SEG_BOTTOM_SIDE_LEFT | SEG_TOP_SIDE_LEFT | SEG_CENTER_LEFT
		                | SEG_CENTER_RIGHT);
		symbols.put('9', SEG_CENTER_RIGHT | SEG_CENTER_LEFT | SEG_TOP_SIDE_LEFT | SEG_TOP_LEFT | SEG_TOP_RIGHT
		                | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT);
		symbols.put('-', SEG_CENTER_LEFT | SEG_CENTER_RIGHT);
	}

	/*
	 * BasicSegmentSymbolFactory
	 */

	@Override
	protected int getAll()
	{
		return super.getAll() | SEG_TOP_LEFT | SEG_TOP_RIGHT | SEG_TOP_SLASH_LEFT | SEG_TOP_SLASH_RIGHT
		                | SEG_BOTTOM_LEFT | SEG_BOTTOM_RIGHT | SEG_BOTTOM_SLASH_LEFT | SEG_BOTTOM_SLASH_RIGHT
		                | SEG_CENTER_LEFT | SEG_CENTER_RIGHT;
	}
}
