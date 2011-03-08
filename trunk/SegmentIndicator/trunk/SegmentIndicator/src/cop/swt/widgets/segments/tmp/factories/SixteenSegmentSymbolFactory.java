package cop.swt.widgets.segments.tmp.factories;

public class SixteenSegmentSymbolFactory extends FourteenSegmentSymbolFactory
{
	public static final int SEG_TOP_CENTER = 0x4000;
	public static final int SEG_BOTTOM_CENTER = 0x8000;

	static
	{
		symbols.put('A', SEG_BOTTOM_SIDE_LEFT | SEG_TOP_SIDE_LEFT | SEG_TOP_LEFT | SEG_TOP_RIGHT | SEG_TOP_SIDE_RIGHT
		                | SEG_BOTTOM_SIDE_RIGHT | SEG_CENTER_LEFT | SEG_CENTER_RIGHT);
		symbols.put('B', SEG_TOP_LEFT | SEG_TOP_RIGHT | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT | SEG_BOTTOM_RIGHT
		                | SEG_BOTTOM_LEFT | SEG_TOP_CENTER | SEG_BOTTOM_CENTER | SEG_CENTER_RIGHT);
		symbols.put('C', SEG_TOP_RIGHT | SEG_TOP_LEFT | SEG_TOP_SIDE_LEFT | SEG_BOTTOM_SIDE_LEFT | SEG_BOTTOM_LEFT
		                | SEG_BOTTOM_RIGHT);
		symbols.put('D', SEG_TOP_LEFT | SEG_TOP_RIGHT | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SIDE_RIGHT | SEG_BOTTOM_RIGHT
		                | SEG_BOTTOM_LEFT | SEG_TOP_CENTER | SEG_BOTTOM_CENTER);
		symbols.put('E', SEG_TOP_RIGHT | SEG_TOP_LEFT | SEG_TOP_SIDE_LEFT | SEG_BOTTOM_SIDE_LEFT | SEG_BOTTOM_LEFT
		                | SEG_BOTTOM_RIGHT | SEG_CENTER_LEFT | SEG_CENTER_RIGHT);
		symbols.put('f', SEG_TOP_RIGHT | SEG_TOP_LEFT | SEG_TOP_SIDE_LEFT | SEG_BOTTOM_SIDE_LEFT | SEG_CENTER_LEFT
		                | SEG_CENTER_RIGHT);

		symbols.put('a', symbols.get('A'));
		symbols.put('b', symbols.get('B'));
		symbols.put('c', symbols.get('C'));
		symbols.put('d', symbols.get('D'));
		symbols.put('e', symbols.get('E'));
		symbols.put('f', symbols.get('F'));
	}

	/*
	 * BasicSegmentSymbolFactory
	 */

	@Override
	protected int getAll()
	{
		return super.getAll() | SEG_TOP_CENTER | SEG_BOTTOM_CENTER;
	}
}
