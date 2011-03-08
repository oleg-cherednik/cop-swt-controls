package cop.swt.widgets.segments;

import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isEqual;
import static org.eclipse.swt.SWT.COLOR_DARK_GRAY;
import static org.eclipse.swt.SWT.COLOR_WHITE;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import cop.swt.widgets.interfaces.Clearable;
import cop.swt.widgets.segments.interfaces.IControl;
import cop.swt.widgets.segments.interfaces.ISegment;

public abstract class AbstractSegmentIndicator<T extends ISegment, N> extends AbstractSegment implements IControl<N>,
                Clearable
{
	protected boolean visible = true;
	protected T[] segments;
	protected N value;

	protected Color offColor = DARK_GRAY;
	protected Color onColor = WHITE;

	private static final Color DARK_GRAY;
	private static final Color WHITE;

	static
	{
		Display display = Display.getCurrent();

		DARK_GRAY = display.getSystemColor(COLOR_DARK_GRAY);
		WHITE = display.getSystemColor(COLOR_WHITE);
	}

	public AbstractSegmentIndicator(int orientation)
	{
		super(orientation);
	}

	protected void createParts()
	{
		if(isHorizontalOrientation())
			createHorizontalOrientatedParts(isInverted(true));
		else
			createVerticalOrientatedParts(isInverted(false));
	}

	protected abstract boolean isInverted(boolean horizontal);

	protected abstract void createHorizontalOrientatedParts(boolean invert);

	protected abstract void createVerticalOrientatedParts(boolean invert);

	public void redraw(int x, int y, int width, int height)
	{
		redraw();
	}

	public ISegment[] getParts()
	{
		return (segments != null) ? segments.clone() : null;
	}

	protected static boolean isDisposed(Canvas canvas)
	{
		return (canvas == null) || canvas.isDisposed();
	}

	public abstract void setCanvas(Canvas canvas);

	protected void _setValue()
	{};

	protected/* abstract */boolean isValueValid(N value)
	{
		return true;
	}

	public abstract void redraw();

	protected abstract void rebuild();

	protected abstract void buildVerticalOrientatedIndicator(boolean invert);

	protected abstract void buildHorizontalOrientatedIndicator(boolean invert);

	/*
	 * AbstractSegment
	 */

	// @Override
	// protected int getDefaultWidth()
	// {
	// return super.getDefaultHeight();
	// }
	//
	// @Override
	// protected int getDefaultHeight()
	// {
	// return super.getDefaultWidth();
	// }

	@Override
	protected void build()
	{
		super.build();

		if(isHorizontalOrientation())
			buildHorizontalOrientatedIndicator(isInverted(true));
		else
			buildVerticalOrientatedIndicator(isInverted(false));

		super.build();

		redraw();
	}

	/*
	 * IControl
	 */

	@Override
	public N getValue()
	{
		return value;
	}

	@Override
	public final void setValue(N value)
	{
		if(isEqual(value, this.value) || !isValueValid(value))
			return;

		this.value = value;
		_setValue();
		redraw();
	}

	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	@Override
	public boolean isVisible()
	{
		return visible;
	}

	@Override
	public void setOffColor(Color color)
	{
		offColor = (color != null) ? color : DARK_GRAY;
		redraw();
	}

	@Override
	public Color getOffColor()
	{
		return offColor;
	}

	@Override
	public void setOnColor(Color color)
	{
		onColor = (color != null) ? color : WHITE;
		redraw();
	}

	@Override
	public Color getOnColor()
	{
		return onColor;
	}

	/*
	 * IShape
	 */

	@Override
	public void draw(GC gc, Color color)
	{
		if(gc == null || color == null || gc.isDisposed() || isEmpty(segments))
			return;

		for(ISegment segment : segments)
			segment.draw(gc, color);
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder(super.toString());

		builder.append(", value=");
		builder.append(value);

		return builder.toString();
	}
}
