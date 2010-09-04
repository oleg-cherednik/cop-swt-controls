package cop.swt.widgets.segments.tmp.factories;

public class SevenSegmentSymbolFactory extends BasicSegmentSymbolFactory
{
	protected static final int SEG_TOP = 0x10;
	protected static final int SEG_BOTTOM = 0x20;
	protected static final int SEG_CENTER = 0x40;

	static
	{
		symbols.put('0', SEG_TOP | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT | SEG_BOTTOM | SEG_BOTTOM_SIDE_LEFT
		                | SEG_TOP_SIDE_LEFT);
		symbols.put('1', SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT);
		symbols.put('2', SEG_TOP | SEG_TOP_SIDE_RIGHT | SEG_CENTER | SEG_BOTTOM_SIDE_LEFT | SEG_BOTTOM);
		symbols.put('3', SEG_TOP | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT | SEG_BOTTOM | SEG_CENTER);
		symbols.put('4', SEG_TOP_SIDE_LEFT | SEG_CENTER | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT);
		symbols.put('5', SEG_TOP | SEG_TOP_SIDE_LEFT | SEG_CENTER | SEG_BOTTOM_SIDE_RIGHT | SEG_BOTTOM);
		symbols.put('6', SEG_TOP | SEG_TOP_SIDE_LEFT | SEG_BOTTOM_SIDE_LEFT | SEG_BOTTOM | SEG_BOTTOM_SIDE_RIGHT
		                | SEG_CENTER);
		symbols.put('7', SEG_TOP | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT);
		symbols.put('8', SEG_TOP | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT | SEG_BOTTOM | SEG_BOTTOM_SIDE_LEFT
		                | SEG_TOP_SIDE_LEFT | SEG_CENTER);
		symbols.put('9', SEG_CENTER | SEG_TOP_SIDE_LEFT | SEG_TOP | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT);
		symbols.put('-', SEG_CENTER);
	}

	/*
	 * BasicSegmentSymbolFactory
	 */

	@Override
	protected int getAll()
	{
		return super.getAll() | SEG_TOP | SEG_BOTTOM | SEG_CENTER;
	}
}
