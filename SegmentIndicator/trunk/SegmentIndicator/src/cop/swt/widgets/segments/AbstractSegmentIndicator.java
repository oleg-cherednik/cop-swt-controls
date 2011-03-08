package cop.swt.widgets.segments;

import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isEqual;
import static org.eclipse.swt.SWT.COLOR_DARK_GRAY;
import static org.eclipse.swt.SWT.COLOR_WHITE;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import cop.swt.widgets.segments.interfaces.ISegment;
import cop.swt.widgets.segments.interfaces.ISegmentIndicator;

public abstract class AbstractSegmentIndicator<T extends ISegment, N> extends AbstractSegment implements
                ISegmentIndicator<N>
{
	private static final Color DARK_GRAY;
	private static final Color WHITE;

	protected boolean visible = true;
	protected boolean transparent;
	protected T[] segments;
	protected N value;

	protected Color offColor = DARK_GRAY;
	protected Color onColor = WHITE;

	static
	{
		Display display = Display.getCurrent();

		DARK_GRAY = display.getSystemColor(COLOR_DARK_GRAY);
		WHITE = display.getSystemColor(COLOR_WHITE);
	}

	protected AbstractSegmentIndicator(int orientation)
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

	public void redraw(int x, int y, int width, int height)
	{
		redraw();
	}

	public ISegment[] getParts()
	{
		return (segments != null) ? segments.clone() : null;
	}

	protected void _setValue()
	{};

	protected/* abstract */boolean isValueValid(N value)
	{
		return true;
	}

	/*
	 * static
	 */

	protected static boolean isDisposed(Canvas canvas)
	{
		return (canvas == null) || canvas.isDisposed();
	}

	/*
	 * abstract
	 */

	public abstract void redraw();

	public abstract void setCanvas(Canvas canvas);

	protected abstract boolean isInverted(boolean horizontal);

	protected abstract void createHorizontalOrientatedParts(boolean invert);

	protected abstract void createVerticalOrientatedParts(boolean invert);

	protected abstract void rebuild();

	protected abstract void buildVerticalOrientatedIndicator(boolean invert);

	protected abstract void buildHorizontalOrientatedIndicator(boolean invert);

	/*
	 * AbstractSegment
	 */

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
	 * ISegmentIndicator
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
	public boolean isTransparent()
	{
		return transparent;
	}

	@Override
	public void setTransparent(boolean enabled)
	{
		transparent = enabled;
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
	public void setForeground(Color color)
	{
		onColor = (color != null) ? color : WHITE;
		redraw();
	}

	@Override
	public Color getForeground()
	{
		return onColor;
	}

	/*
	 * IControl
	 */

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
		StringBuilder buf = new StringBuilder(super.toString());

		buf.append(", value=").append(value);

		return buf.toString();
	}
}
