package cop.swt.widgets.segments.seven;

import static org.eclipse.swt.SWT.VERTICAL;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

import cop.swt.widgets.segments.SegmentedIndicator;
import cop.swt.widgets.segments.primitives.BottomSegment;
import cop.swt.widgets.segments.primitives.CenterSegment;
import cop.swt.widgets.segments.primitives.LeftSegment;
import cop.swt.widgets.segments.primitives.RightSegment;
import cop.swt.widgets.segments.primitives.SimpleSegment;
import cop.swt.widgets.segments.primitives.TopSegment;

public abstract class SevenSegmentIndicator extends SegmentedIndicator
{
	protected static final int SEG_NONE = 0;
	protected static final int SEG_ALL = 0x7F;

	protected static final int SEG_TOP = 0x1;
	protected static final int SEG_TOP_RIGHT = 0x2;
	protected static final int SEG_BOTTOM_RIGHT = 0x4;
	protected static final int SEG_BOTTOM = 0x8;
	protected static final int SEG_BOTTOM_LEFT = 0x10;
	protected static final int SEG_TOP_LEFT = 0x20;
	protected static final int SEG_CENTER = 0x40;

	public SevenSegmentIndicator(Shell shell, int orientation)
	{
		super(shell, orientation);
	}

	protected void _setValue(int value)
	{
		drawPart(value, TOP, SEG_TOP);
		drawPart(value, TOP_RIGHT, SEG_TOP_RIGHT);
		drawPart(value, BOTTOM_RIGHT, SEG_BOTTOM_RIGHT);
		drawPart(value, BOTTOM, SEG_BOTTOM);
		drawPart(value, BOTTOM_LEFT, SEG_BOTTOM_LEFT);
		drawPart(value, TOP_LEFT, SEG_TOP_LEFT);
		drawPart(value, CENTER, SEG_CENTER);
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
		int center = width - 1;
		int _x = 0, _y = 0, _yy = 0;

		_y = y + height - 1;
		segments[TOP].setBounds(x + 1, invert ? _y : y, scale);
		segments[BOTTOM].setBounds(x + 1, invert ? y : _y, scale);

		_x = x + width - 1;
		_y = y + center + 1;
		_yy = y + 1;
		segments[TOP_RIGHT].setBounds(invert ? x : _x, invert ? _y : _yy, scale);
		segments[BOTTOM_LEFT].setBounds(invert ? _x : x, invert ? _yy : _y, scale);
		segments[TOP_LEFT].setBounds(invert ? _x : x, invert ? _y : _yy, scale);
		segments[BOTTOM_RIGHT].setBounds(invert ? x : _x, invert ? _yy : _y, scale);

		segments[CENTER].setBounds(x + 1, y + center, scale);
	}

	@Override
	protected void buildHorizontalOrientatedIndicator(boolean invert)
	{
		int center = height - 1;
		int _x = 0, _xx = 0, _y = 0;

		_x = x + width - 1;
		segments[TOP].setBounds(invert ? x : _x, y + 1, scale);
		segments[BOTTOM].setBounds(invert ? _x : x, y + 1, scale);

		_x = x + center + 1;
		_y = y + height - 1;
		_xx = x + 1;
		segments[TOP_RIGHT].setBounds(invert ? _xx : _x, invert ? y : _y, scale);
		segments[BOTTOM_LEFT].setBounds(invert ? _x : _xx, invert ? _y : y, scale);
		segments[TOP_LEFT].setBounds(invert ? _xx : _x, invert ? _y : y, scale);
		segments[BOTTOM_RIGHT].setBounds(invert ? _x : _xx, invert ? y : _y, scale);

		segments[CENTER].setBounds(x + center, y + 1, scale);
	}

	@Override
	protected void createHorizontalOrientatedParts(boolean invert)
	{
		segments = new SimpleSegment[7];

		segments[TOP] = invert ? new LeftSegment() : new RightSegment();
		segments[BOTTOM] = invert ? new RightSegment() : new LeftSegment();
		segments[TOP_RIGHT] = invert ? new TopSegment() : new BottomSegment();
		segments[TOP_LEFT] = invert ? new BottomSegment() : new TopSegment();
		segments[BOTTOM_RIGHT] = invert ? new TopSegment() : new BottomSegment();
		segments[BOTTOM_LEFT] = invert ? new BottomSegment() : new TopSegment();
		segments[CENTER] = new CenterSegment(VERTICAL);
	}

	@Override
	protected void createVerticalOrientatedParts(boolean invert)
	{
		segments = new SimpleSegment[7];

		segments[TOP] = invert ? new BottomSegment() : new TopSegment();
		segments[BOTTOM] = invert ? new TopSegment() : new BottomSegment();
		segments[TOP_RIGHT] = invert ? new LeftSegment() : new RightSegment();
		segments[TOP_LEFT] = invert ? new RightSegment() : new LeftSegment();
		segments[BOTTOM_RIGHT] = invert ? new LeftSegment() : new RightSegment();
		segments[BOTTOM_LEFT] = invert ? new RightSegment() : new LeftSegment();
		segments[CENTER] = new CenterSegment();
	}
}
