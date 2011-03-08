package cop.swt.widgets.segments.tmp.factories;

//GOST P 51506-99
public class PostSegmentSymbolFactory extends NineSegmentSymbolFactory
{
	static
	{
		symbols.put('2', SEG_TOP | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SLASH | SEG_BOTTOM);
		symbols.put('3', SEG_TOP | SEG_TOP_SLASH | SEG_CENTER | SEG_BOTTOM_SLASH);
		symbols.put('6', SEG_TOP_SLASH | SEG_CENTER | SEG_BOTTOM_SIDE_RIGHT | SEG_BOTTOM | SEG_BOTTOM_SIDE_LEFT);
		symbols.put('7', SEG_TOP | SEG_TOP_SLASH | SEG_BOTTOM_SIDE_LEFT);
		symbols.put('9', SEG_CENTER | SEG_TOP_SIDE_LEFT | SEG_TOP | SEG_TOP_SIDE_RIGHT | SEG_BOTTOM_SLASH);
	}
}
