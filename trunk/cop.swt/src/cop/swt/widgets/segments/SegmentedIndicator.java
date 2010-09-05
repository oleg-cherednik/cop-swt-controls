package cop.swt.widgets.segments;

import static cop.common.extensions.BitExtension.isAnyBitSet;
import static cop.common.extensions.BitExtension.isBitSet;
import static cop.common.extensions.CommonExtension.isNotNull;
import static org.eclipse.swt.SWT.DOWN;
import static org.eclipse.swt.SWT.HORIZONTAL;
import static org.eclipse.swt.SWT.LEFT;
import static org.eclipse.swt.SWT.RIGHT;
import static org.eclipse.swt.SWT.UP;
import static org.eclipse.swt.SWT.VERTICAL;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

import cop.swt.widgets.segments.interfaces.ISegment;
import cop.swt.widgets.segments.primitives.SimpleSegment;

public abstract class SegmentedIndicator extends AbstractSegmentIndicator<SimpleSegment, Character>
{
	protected static final int DEFAULT_ORIENTATION = VERTICAL | UP;
	protected static final int HORIZONTAL_ORIENTATION = LEFT | RIGHT | HORIZONTAL;

	private boolean transparent;
	private GC gc;
	protected Shell shell;

	public SegmentedIndicator(Shell shell, int orientation)
	{
		super(orientation);

		setShell(shell);
		initGC();
	}

	public Shell getShell()
	{
		return shell;
	}

	protected void initGC()
	{
		if(gc != null)
			gc.dispose();

		if(isNotNull(shell))
			gc = new GC(shell);
	}

	private void drawParts(Color color)
	{
		if(isDisposed(shell))
			return;

		for(ISegment segment : segments)
			segment.draw(gc, color);
	}

	protected void drawPart(int mask, int i, int bit)
	{
		if(isDisposed(shell) || !visible)
			return;

		if(isBitSet(mask, bit))
			segments[i].draw(gc, onColor);
		else if(transparent)
			segments[i].draw(gc, shell.getBackground());
		else
			segments[i].draw(gc, offColor);
	}

	/*
	 * ISegment
	 */

	@Override
	public void setPosition(int x, int y)
	{
		if(isDisposed(shell))
			super.setPosition(x, y);
		else
		{
			Rectangle rect = getBounds().union(new Rectangle(x, y, x + this.width, y + this.height));

			super.setPosition(x, y);
			shell.redraw(rect.x, rect.y, rect.width, rect.height, false);
		}
	}

	@Override
	public void setScale(int scale)
	{
		if(isDisposed(shell))
			super.setScale(scale);
		else
		{
			Rectangle rect = null;

			if(scale < this.scale)
				rect = getBounds().union(new Rectangle(x, y, x + this.width, y + this.height));

			super.setScale(scale);

			if(rect == null)
				rect = getBounds().union(new Rectangle(x, y, x + this.width, y + this.height));

			shell.redraw(rect.x, rect.y, rect.width, rect.height, false);
		}
	}

	@Override
	public void setBounds(int x, int y, int scale)
	{
		if(isDisposed(shell))
			super.setBounds(x, y, scale);
		else
		{
			Rectangle rect = getBounds().union(new Rectangle(x, y, x + this.width, y + this.height));

			super.setBounds(x, y, scale);
			shell.redraw(rect.x, rect.y, rect.width, rect.height, false);
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
		return isAnyBitSet(orientation, HORIZONTAL_ORIENTATION);
	}

	@Override
	public void setOrientation(int orientation)
	{
		super.setOrientation(orientation);
		rebuild();
	}

	protected void hide()
	{
		if(!isDisposed(shell))
			drawParts(shell.getBackground());
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
		return horizontal ? isBitSet(orientation, LEFT) : isBitSet(orientation, DOWN);
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
	public void setShell(Shell shell)
	{
		if(isDisposed(shell) || shell.equals(this.shell))
			return;

		this.shell = shell;

		initGC();
		redraw();
	}

	/*
	 * IControl
	 */

	@Override
	public void clear()
	{
		if(!isDisposed(shell) && visible)
			drawParts(transparent ? shell.getBackground() : offColor);
	}

	@Override
	public void dispose()
	{
		if(isNotNull(gc))
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
