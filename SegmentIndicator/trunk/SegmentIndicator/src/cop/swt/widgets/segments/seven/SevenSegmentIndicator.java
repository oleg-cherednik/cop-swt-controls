package cop.swt.widgets.segments.seven;

import static org.eclipse.swt.SWT.HORIZONTAL;
import static org.eclipse.swt.SWT.VERTICAL;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

import cop.swt.widgets.segments.SegmentIndicator;
import cop.swt.widgets.segments.primitives.drawable.BottomSegment;
import cop.swt.widgets.segments.primitives.drawable.CenterSegment;
import cop.swt.widgets.segments.primitives.drawable.LeftSegment;
import cop.swt.widgets.segments.primitives.drawable.RightSegment;
import cop.swt.widgets.segments.primitives.drawable.SimpleSegment;
import cop.swt.widgets.segments.primitives.drawable.TopSegment;
import cop.swt.widgets.segments.tmp.factories.SevenSegmentSymbolFactory;

public abstract class SevenSegmentIndicator extends SegmentIndicator
{
	protected static final int TOP = 0;
	protected static final int TOP_SIDE_RIGHT = 1;
	protected static final int BOTTOM_SIDE_RIGHT = 2;
	protected static final int BOTTOM = 3;
	protected static final int BOTTOM_SIDE_LEFT = 4;
	protected static final int TOP_SIDE_LEFT = 5;
	protected static final int CENTER = 6;

	protected static final SevenSegmentSymbolFactory factory = SevenSegmentSymbolFactory.getInstance();

	public SevenSegmentIndicator(Canvas canvas, int orientation)
	{
		super(canvas, orientation);
	}

	protected void _setValue(int value)
	{
		drawPart(value, TOP, SevenSegmentSymbolFactory.SEG_TOP);
		drawPart(value, TOP_SIDE_RIGHT, SevenSegmentSymbolFactory.SEG_TOP_SIDE_RIGHT);
		drawPart(value, BOTTOM_SIDE_RIGHT, SevenSegmentSymbolFactory.SEG_BOTTOM_SIDE_RIGHT);
		drawPart(value, BOTTOM, SevenSegmentSymbolFactory.SEG_BOTTOM);
		drawPart(value, BOTTOM_SIDE_LEFT, SevenSegmentSymbolFactory.SEG_BOTTOM_SIDE_LEFT);
		drawPart(value, TOP_SIDE_LEFT, SevenSegmentSymbolFactory.SEG_TOP_SIDE_LEFT);
		drawPart(value, CENTER, SevenSegmentSymbolFactory.SEG_CENTER);
	}

	/*
	 * IShape
	 */

	@Override
	public int[] getShape()
	{
		// TODO implements
		return null;
	}

	@Override
	public int[] getShape(Rectangle rect)
	{
		if(rect == null)
			return new int[0];

		// TODO implements

		return null;
	}

	/*
	 * AbstractSegmentIndicator
	 */

	@Override
	protected void buildVerticalOrientatedIndicator(boolean invert)
	{
		int center = height >> 1;
		int _x = 0, _y = 0, _yy = 0;

		_y = y + height - 1;
		segments[TOP].setBounds(x + 1, invert ? _y : y, getScale());
		segments[BOTTOM].setBounds(x + 1, invert ? y : _y, getScale());

		_x = x + width - 1;
		_y = y + center + 1;
		_yy = y + 1;
		segments[TOP_SIDE_RIGHT].setBounds(invert ? x : _x, invert ? _y : _yy, getScale());
		segments[BOTTOM_SIDE_LEFT].setBounds(invert ? _x : x, invert ? _yy : _y, getScale());
		segments[TOP_SIDE_LEFT].setBounds(invert ? _x : x, invert ? _y : _yy, getScale());
		segments[BOTTOM_SIDE_RIGHT].setBounds(invert ? x : _x, invert ? _yy : _y, getScale());

		segments[CENTER].setBounds(x + 1, y + center, getScale());
	}

	@Override
	protected void buildHorizontalOrientatedIndicator(boolean invert)
	{
		int center = height - 1;
		int _x = 0, _xx = 0, _y = 0;

		_x = x + width - 1;
		segments[TOP].setBounds(invert ? x : _x, y + 1, getScale());
		segments[BOTTOM].setBounds(invert ? _x : x, y + 1, getScale());

		_x = x + center + 1;
		_y = y + height - 1;
		_xx = x + 1;
		segments[TOP_SIDE_RIGHT].setBounds(invert ? _xx : _x, invert ? y : _y, getScale());
		segments[BOTTOM_SIDE_LEFT].setBounds(invert ? _x : _xx, invert ? _y : y, getScale());
		segments[TOP_SIDE_LEFT].setBounds(invert ? _xx : _x, invert ? _y : y, getScale());
		segments[BOTTOM_SIDE_RIGHT].setBounds(invert ? _x : _xx, invert ? y : _y, getScale());

		segments[CENTER].setBounds(x + center, y + 1, getScale());
	}

	@Override
	protected void createHorizontalOrientatedParts(boolean invert)
	{
		segments = new SimpleSegment[7];

		segments[TOP] = RightSegment.createSegment(invert);
		segments[BOTTOM] = LeftSegment.createSegment(invert);
		segments[TOP_SIDE_RIGHT] = BottomSegment.createSegment(invert);
		segments[TOP_SIDE_LEFT] = TopSegment.createSegment(invert);
		segments[BOTTOM_SIDE_RIGHT] = BottomSegment.createSegment(invert);
		segments[BOTTOM_SIDE_LEFT] = TopSegment.createSegment(invert);
		segments[CENTER] = CenterSegment.createSegment(VERTICAL);
	}

	@Override
	protected void createVerticalOrientatedParts(boolean invert)
	{
		segments = new SimpleSegment[7];

		segments[TOP] = TopSegment.createSegment(invert);
		segments[BOTTOM] = BottomSegment.createSegment(invert);
		segments[TOP_SIDE_RIGHT] = RightSegment.createSegment(invert);
		segments[TOP_SIDE_LEFT] = LeftSegment.createSegment(invert);
		segments[BOTTOM_SIDE_RIGHT] = RightSegment.createSegment(invert);
		segments[BOTTOM_SIDE_LEFT] = LeftSegment.createSegment(invert);
		segments[CENTER] = CenterSegment.createSegment(HORIZONTAL);
	}
}
