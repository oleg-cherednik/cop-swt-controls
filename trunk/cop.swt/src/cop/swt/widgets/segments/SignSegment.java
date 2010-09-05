package cop.swt.widgets.segments;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

import cop.swt.widgets.segments.primitives.MinusSegment;
import cop.swt.widgets.segments.primitives.PlusSegment;
import cop.swt.widgets.segments.primitives.SimpleSegment;

public class SignSegment extends SegmentedIndicator
{
	protected static final int PLUS = 0;
	protected static final int MINUS = 1;

	public SignSegment()
	{
		super(null, DEFAULT_ORIENTATION);
	}

	public SignSegment(Shell shell)
	{
		super(shell, DEFAULT_ORIENTATION);
	}

	public SignSegment(Shell shell, int orientation)
	{
		super(shell, orientation);
	}

	public SignSegment(int x, int y, int scale)
	{
		this(null, DEFAULT_ORIENTATION, x, y, scale);
	}

	public SignSegment(Shell shell, int x, int y, int scale)
	{
		this(shell, DEFAULT_ORIENTATION, x, y, scale);
	}

	public SignSegment(Shell shell, int orientation, int x, int y, int scale)
	{
		super(shell, DEFAULT_ORIENTATION);

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
		return (scale <= 1) ? BASE_LENGTH : (BASE_LENGTH * scale - scale + 1);
	}

	@Override
	protected int getDefaultHeight()
	{
		return super.getDefaultWidth() * 2 - 1;
	}

	@Override
	protected void buildVerticalOrientatedIndicator(boolean invert)
	{
		int _y = (scale <= 1) ? 2 : (3 + (scale - 2) * 2);

		segments[PLUS].setBounds(x, invert ? _y : y + _y, scale);
		segments[MINUS].setBounds(x, invert ? _y : height - _y, scale);
	}

	@Override
	protected void buildHorizontalOrientatedIndicator(boolean invert)
	{
		int _y = (scale <= 1) ? 2 : (3 + (scale - 2) * 2);

		segments[PLUS].setBounds(x, invert ? _y : y + _y, scale);
		segments[MINUS].setBounds(x, invert ? y : height - _y - 1, scale);
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

	/*
	 * AbstractSegmentIndicator
	 */

	@Override
	public void redraw()
	{
		if(value == null)
			super.clear();
		//else
		//_setValue(factory.getSegments(value));
	}

}
