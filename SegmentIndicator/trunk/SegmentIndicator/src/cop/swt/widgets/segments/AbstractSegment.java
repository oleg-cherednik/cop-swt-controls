package cop.swt.widgets.segments;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import cop.swt.widgets.segments.interfaces.ISegment;
import cop.swt.widgets.segments.interfaces.Scaleable;

public abstract class AbstractSegment implements ISegment, Scaleable {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int orientation;

	private int scale = 1;

	protected AbstractSegment(int orientation) {
		setOrientation(orientation);
		setScale(DEF_SCALE);
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		if (this.orientation == orientation)
			return;

		if (orientation == SWT.NONE || orientation == SWT.DEFAULT)
			this.orientation = getDefaultOrientation();
		else
			this.orientation = orientation;
	}

	protected void build() {
		width = getWidth();
		height = getHeight();
	}

	protected int getDefaultWidth() {
		return (scale <= 1) ? BASE_LENGTH : (BASE_LENGTH * (2 << (scale - 2)));
	}

	protected int getDefaultHeight() {
		return (scale <= 1) ? 2 : (3 + (scale - 2) * 2);
	}

	protected int getWidth() {
		return isHorizontalOrientation() ? getDefaultWidth() : getDefaultHeight();
	}

	protected int getHeight() {
		return isHorizontalOrientation() ? getDefaultHeight() : getDefaultWidth();
	}

	/*
	 * abstract
	 */

	protected abstract int getDefaultOrientation();

	protected abstract boolean isHorizontalOrientation();

	/*
	 * Scaleable
	 */

	@Override
	public int getScale() {
		return scale;
	}

	@Override
	public void setScale(int scale) {
		if (this.scale == scale)
			return;

		this.scale = (scale <= DEF_SCALE) ? DEF_SCALE : scale;
		build();
	}

	/*
	 * ISegment
	 */

	@Override
	public void setPosition(int x, int y) {
		if (this.x == x && this.y == y)
			return;

		this.x = x;
		this.y = y;

		build();
	}

	@Override
	public void setBounds(int x, int y, int scale) {
		if (this.x == x && this.y == y && this.scale == scale)
			return;

		this.x = x;
		this.y = y;
		this.scale = scale;

		build();
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	@Override
	public Point getSize() {
		return new Point(width, height);
	}

	/*
	 * Object
	 */

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();

		buf.append("height=").append(height);
		buf.append(", scale=").append(scale);
		buf.append(", width=").append(width);
		buf.append(", x=").append(x);
		buf.append(", y=").append(y);

		return buf.toString();
	}
}
