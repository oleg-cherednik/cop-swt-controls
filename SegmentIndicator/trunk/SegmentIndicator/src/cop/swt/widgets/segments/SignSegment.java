package cop.swt.widgets.segments;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

import cop.swt.widgets.segments.primitives.drawable.SimpleSegment;
import cop.swt.widgets.segments.primitives.fillable.MinusSegment;
import cop.swt.widgets.segments.primitives.fillable.PlusSegment;

public class SignSegment extends SegmentIndicator
{
	protected static final int PLUS = 0;
	protected static final int MINUS = 1;

	public SignSegment()
	{
		super(null, DEFAULT_ORIENTATION);
	}

	public SignSegment(Canvas canas)
	{
		super(canas, DEFAULT_ORIENTATION);
	}

	public SignSegment(Canvas canvas, int orientation)
	{
		super(canvas, orientation);
	}

	public SignSegment(int x, int y, int scale)
	{
		super(null, DEFAULT_ORIENTATION);
		setBounds(x, y, scale);
	}

	/*
	 * IShape
	 */

	@Override
	public int[] getShape()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getShape(Rectangle rect)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * AbstractSegmentIndicator
	 */

	@Override
	protected int getDefaultWidth()
	{
		return (getScale() <= 1) ? BASE_LENGTH : (BASE_LENGTH * getScale() - getScale() + 1);
	}

	@Override
	protected int getDefaultHeight()
	{
		return super.getDefaultWidth() * 2 - 1;
	}

	@Override
	protected void buildVerticalOrientatedIndicator(boolean invert)
	{
		int _y = (getScale() <= 1) ? 2 : (3 + (getScale() - 2) * 2);

		segments[PLUS].setBounds(x, invert ? _y : y + _y, getScale());
		segments[MINUS].setBounds(x, invert ? _y : height - _y, getScale());
	}

	@Override
	protected void buildHorizontalOrientatedIndicator(boolean invert)
	{
		int _y = (getScale() <= 1) ? 2 : (3 + (getScale() - 2) * 2);

		segments[PLUS].setBounds(x, invert ? _y : y + _y, getScale());
		segments[MINUS].setBounds(x, invert ? y : height - _y - 1, getScale());
	}

	@Override
	protected void createHorizontalOrientatedParts(boolean invert)
	{
		segments = new SimpleSegment[2];

		segments[PLUS] = new PlusSegment();
		segments[MINUS] = new MinusSegment();
	}

	@Override
	protected void createVerticalOrientatedParts(boolean invert)
	{
		segments = new SimpleSegment[2];

		segments[PLUS] = new PlusSegment();
		segments[MINUS] = new MinusSegment();
	}

	@Override
	protected boolean isValueValid(Character value)
	{
		return value == null || value == '-' || value == '+';
	}

	@Override
	public void redraw()
	{
		if(value == null)
			clear();
		else
		{
			drawPart(0x1, MINUS, (value == '-') ? 0x1 : 0x2);
			drawPart(0x1, PLUS, (value == '+') ? 0x1 : 0x2);
		}
	}
}
