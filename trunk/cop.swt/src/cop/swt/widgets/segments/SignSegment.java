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
	protected void buildVerticalOrientatedIndicator(boolean invert)
	{
		int center = width - 1;

		segments[PLUS].setBounds(x, invert ? (center + 1) : (y + 2), scale);
		segments[MINUS].setBounds(x, invert ? (y + 6) : (center + 5), scale);

		//		segments[PLUS].setBounds(x, invert ? _y : y, scale);
		//		segments[MINUS].setBounds(x, invert ? y : _y, scale);
	}

	@Override
	protected void buildHorizontalOrientatedIndicator(boolean invert)
	{
		segments[PLUS].setBounds(x, 10, scale);
		segments[MINUS].setBounds(x, 0, scale);

		//		segments[PLUS].setBounds(x + 1, invert ? _y : y, scale);
		//		segments[MINUS].setBounds(x + 1, invert ? y : _y, scale);
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
