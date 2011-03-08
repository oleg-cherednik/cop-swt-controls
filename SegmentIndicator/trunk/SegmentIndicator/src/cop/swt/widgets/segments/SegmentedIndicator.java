package cop.swt.widgets.segments;

import static cop.common.extensions.BitExtension.isAnyBitSet;
import static cop.common.extensions.BitExtension.isBitSet;
import static org.eclipse.swt.SWT.DOWN;
import static org.eclipse.swt.SWT.HORIZONTAL;
import static org.eclipse.swt.SWT.LEFT;
import static org.eclipse.swt.SWT.RIGHT;
import static org.eclipse.swt.SWT.UP;
import static org.eclipse.swt.SWT.VERTICAL;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

import cop.swt.widgets.segments.interfaces.ISegment;
import cop.swt.widgets.segments.primitives.drawable.SimpleSegment;

public abstract class SegmentedIndicator extends AbstractSegmentIndicator<SimpleSegment, Character>
{
	protected static final int DEFAULT_ORIENTATION = VERTICAL | UP;
	protected static final int HORIZONTAL_ORIENTATION = LEFT | RIGHT | HORIZONTAL;

	private boolean transparent;
	private GC gc;
	protected Canvas canvas;

	public SegmentedIndicator(Canvas canvas, int orientation)
	{
		super(orientation);

		setCanvas(canvas);
		initGC();
	}

	public Canvas getCanvas()
	{
		return canvas;
	}

	protected void initGC()
	{
		if(gc != null)
			gc.dispose();

		if(canvas != null)
			gc = new GC(canvas);
	}

	private void drawParts(Color color)
	{
		if(isDisposed(canvas))
			return;

		for(ISegment segment : segments)
			segment.draw(gc, color);
	}

	protected void drawPart(int mask, int i, int bit)
	{
		if(isDisposed(canvas) || !visible)
			return;

		if(isBitSet(mask, bit))
			segments[i].draw(gc, onColor);
		else if(transparent)
			segments[i].draw(gc, canvas.getBackground());
		else
			segments[i].draw(gc, offColor);
	}

	/*
	 * ISegment
	 */

	@Override
	public void setPosition(int x, int y)
	{
		if(isDisposed(canvas))
			super.setPosition(x, y);
		else
		{
			Rectangle rect = getBounds().union(new Rectangle(x, y, x + this.width, y + this.height));

			super.setPosition(x, y);
			canvas.redraw(rect.x, rect.y, rect.width, rect.height, false);
		}
	}

	@Override
	public void setScale(int scale)
	{
		if(isDisposed(canvas))
			super.setScale(scale);
		else
		{
			Rectangle rect = null;

			if(scale < getScale())
				rect = getBounds().union(new Rectangle(x, y, x + this.width, y + this.height));

			super.setScale(scale);

			if(rect == null)
				rect = getBounds().union(new Rectangle(x, y, x + this.width, y + this.height));

			canvas.redraw(rect.x, rect.y, rect.width, rect.height, false);
		}
	}

	@Override
	public void setBounds(int x, int y, int scale)
	{
		if(isDisposed(canvas))
			super.setBounds(x, y, scale);
		else
		{
			Rectangle rect = getBounds().union(new Rectangle(x, y, x + this.width, y + this.height));

			super.setBounds(x, y, scale);
			canvas.redraw(rect.x, rect.y, rect.width, rect.height, false);
		}
	}

	/*
	 * AbstractSegment
	 */

	@Override
	protected int getWidth()
	{
		return isHorizontalOrientation() ? getDefaultHeight() : getDefaultWidth();
	}

	@Override
	protected int getHeight()
	{
		return isHorizontalOrientation() ? getDefaultWidth() : getDefaultHeight();
	}

	@Override
	protected boolean isHorizontalOrientation()
	{
		return isAnyBitSet(getOrientation(), HORIZONTAL_ORIENTATION);
	}

	@Override
	public void setOrientation(int orientation)
	{
		super.setOrientation(orientation);
		rebuild();
	}

	protected void hide()
	{
		if(!isDisposed(canvas))
			drawParts(canvas.getBackground());
	}

	@Override
	protected int getDefaultOrientation()
	{
		return DEFAULT_ORIENTATION;
	}

	@Override
	protected int getDefaultWidth()
	{
		return super.getDefaultWidth() + 2;
	}

	@Override
	protected int getDefaultHeight()
	{
		return getDefaultWidth() * 2 - 1;
	}

	/*
	 * AbstractSegmentIndicator
	 */

	@Override
	protected boolean isInverted(boolean horizontal)
	{
		return horizontal ? isBitSet(getOrientation(), LEFT) : isBitSet(getOrientation(), DOWN);
	}

	@Override
	protected void rebuild()
	{
		boolean vis = visible;

		setVisible(false);

		createParts();
		build();

		setVisible(vis);
	}

	@Override
	public void setTransparent(boolean enabled)
	{
		transparent = enabled;
		redraw();
	}

	@Override
	public void setCanvas(Canvas canvas)
	{
		if(isDisposed(canvas) || canvas.equals(this.canvas))
			return;

		this.canvas = canvas;

		initGC();
		redraw();
	}

	/*
	 * IControl
	 */

	@Override
	public void clear()
	{
		if(!isDisposed(canvas) && visible)
			drawParts(transparent ? canvas.getBackground() : offColor);
	}

	@Override
	public void dispose()
	{
		if(gc != null)
			gc.dispose();
	}

	@Override
	public void setVisible(boolean visible)
	{
		if(!(this.visible ^ visible))
			return;

		if(visible)
		{
			this.visible = visible;
			redraw();
		}
		else
		{
			hide();
			this.visible = visible;
		}
	}
}
