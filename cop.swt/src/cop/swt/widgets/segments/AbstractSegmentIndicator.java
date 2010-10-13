package cop.swt.widgets.segments;

import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isEqual;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.swt.extensions.ColorExtension.DARK_GRAY;
import static cop.swt.extensions.ColorExtension.WHITE;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

import cop.swt.widgets.interfaces.Clearable;
import cop.swt.widgets.segments.interfaces.IControl;
import cop.swt.widgets.segments.interfaces.ISegment;

public abstract class AbstractSegmentIndicator<T extends ISegment, N> extends AbstractSegment implements IControl,
                Clearable
{
	protected boolean visible = true;
	protected T[] segments;
	protected N value;

	protected Color offColor = DARK_GRAY;
	protected Color onColor = WHITE;

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

	public void setOffColor(Color color)
	{
		offColor = isNotNull(color) ? color : DARK_GRAY;
		redraw();
	}

	public Color getOffColor()
	{
		return offColor;
	}

	public void setOnColor(Color color)
	{
		onColor = isNotNull(color) ? color : WHITE;
		redraw();
	}

	public Color getOnColor()
	{
		return onColor;
	}

	public void redraw(int x, int y, int width, int height)
	{
		redraw();
	}

	public ISegment[] getParts()
	{
		return isNotNull(segments) ? segments.clone() : null;
	}

	protected static boolean isDisposed(Widget widget)
	{
		return isNull(widget) || widget.isDisposed();
	}

	public abstract void setTransparent(boolean enabled);

	public abstract void setShell(Shell shell);

	public N getValue()
	{
		return value;
	}

	public final void setValue(N value)
	{
		if(isEqual(value, this.value) || !isValueValid(value))
			return;

		this.value = value;
		_setValue();
		redraw();
	}

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

		redraw();
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
		StringBuilder builder = new StringBuilder(super.toString());

		builder.append(", value=");
		builder.append(value);

		return builder.toString();
	}
}
