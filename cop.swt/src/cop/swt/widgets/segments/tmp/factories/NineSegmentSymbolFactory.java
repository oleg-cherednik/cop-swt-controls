package cop.swt.widgets.segments.tmp.factories;

public class NineSegmentSymbolFactory extends SevenSegmentSymbolFactory
{
	protected static final int SEG_TOP_SLASH = 0x80;
	protected static final int SEG_BOTTOM_SLASH = 0x100;

	static
	{
		symbols.put('1', symbols.get('1') | SEG_TOP_SLASH);
		symbols.put('3', SEG_TOP | SEG_TOP_SLASH | SEG_CENTER | SEG_BOTTOM_SIDE_RIGHT | SEG_BOTTOM);
		symbols.put('7', SEG_TOP | SEG_TOP_SLASH | SEG_BOTTOM_SLASH);
	}

	/*
	 * BasicSegmentSymbolFactory
	 */

	@Override
	protected int getAll()
	{
		return super.getAll() | SEG_TOP_SLASH | SEG_BOTTOM_SLASH;
	}
}
