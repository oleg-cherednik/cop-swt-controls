package cop.swt.widgets.segments;

import static org.eclipse.swt.SWT.DEFAULT;
import static org.eclipse.swt.SWT.NONE;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import cop.swt.widgets.segments.interfaces.ISegment;

public abstract class AbstractSegment implements ISegment
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	private int scale = 1;
	private int orientation;

	public AbstractSegment(int orientation)
	{
		setOrientation(orientation);
		setScale(DEF_SCALE);
	}

	public int getScale()
	{
		return scale;
	}

	public void setScale(int scale)
	{
		if(this.scale == scale)
			return;

		this.scale = (scale <= DEF_SCALE) ? DEF_SCALE : scale;
		build();
	}

	public int getOrientation()
	{
		return orientation;
	}

	public void setOrientation(int orientation)
	{
		if(this.orientation == orientation)
			return;

		if(orientation == NONE || orientation == DEFAULT)
			this.orientation = getDefaultOrientation();
		else
			this.orientation = orientation;
	}

	protected void build()
	{
		width = getWidth();
		height = getHeight();
	}

	protected int getDefaultWidth()
	{
		return (scale <= 1) ? BASE_LENGTH : (BASE_LENGTH * (2 << (scale - 2)));
	}

	protected int getDefaultHeight()
	{
		return (scale <= 1) ? 2 : (3 + (scale - 2) * 2);
	}

	protected int getWidth()
	{
		return isHorizontalOrientation() ? getDefaultWidth() : getDefaultHeight();
	}

	protected int getHeight()
	{
		return isHorizontalOrientation() ? getDefaultHeight() : getDefaultWidth();
	}

	protected abstract int getDefaultOrientation();

	protected abstract boolean isHorizontalOrientation();

	/*
	 * ISegment
	 */

	@Override
	public void setPosition(int x, int y)
	{
		if(this.x == x && this.y == y)
			return;

		this.x = x;
		this.y = y;

		build();
	}

	@Override
	public void setBounds(int x, int y, int scale)
	{
		if(this.x == x && this.y == y && this.scale == scale)
			return;

		this.x = x;
		this.y = y;
		this.scale = scale;

		build();
	}

	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}

	@Override
	public Point getSize()
	{
		return new Point(width, height);
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("height=");
		builder.append(height);
		builder.append(", scale=");
		builder.append(scale);
		builder.append(", width=");
		builder.append(width);
		builder.append(", x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		return builder.toString();
	}
}
