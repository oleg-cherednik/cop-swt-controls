package cop.swt.widgets.segments.components;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.segments.SegmentContainer;
import cop.swt.widgets.segments.interfaces.ISegmentIndicator;

public class SegmentViewer<T extends Number> extends Canvas implements PaintListener, DisposeListener,
                ISegmentIndicator<T>
{
	private final SegmentContainer<T> obj;

	// public static <T> SegmentIndicator createNumeric;

	protected SegmentViewer(Composite parent, int style, SegmentContainer<T> obj)
	{
		super(parent, style);
		// obj.setOnColor(color)setBackgroundColor(null);

		this.obj = obj;
		obj.setCanvas(this);

		addListeners();
	}

	private void addListeners()
	{
		addPaintListener(this);
		addDisposeListener(this);
	}

	public void setOrientation(int orientation)
	{
		obj.setOrientation(orientation);
		getParent().layout(true, true);
	}

	public int getOrientation()
	{
		return obj.getOrientation();
	}

	public void setPosition(int x, int y)
	{
		obj.setPosition(x, y);
	}

	/*
	 * Control
	 */

	@Override
	public Rectangle getBounds()
	{
		return obj.getBounds();
	}

	@Override
	public Point getSize()
	{
		return obj.getSize();
	}

	/*
	 * Composite
	 */

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed)
	{
		checkWidget();
		return obj.getSize();
	}

	/*
	 * PaintListener
	 */

	public void paintControl(PaintEvent e)
	{
		obj.redraw(e.x, e.y, e.width, e.height);
	}

	/*
	 * DisposeListener
	 */

	public void widgetDisposed(DisposeEvent e)
	{
		obj.dispose();
	}

	/*
	 * ISegmentIndicator
	 */

	@Override
	public T getValue()
	{
		return obj.getValue();
	}

	@Override
	public void setValue(T value)
	{
		obj.setValue(value);
	}

	@Override
	public void setScale(int scale)
	{
		obj.setScale(scale);
		getParent().layout(true, true);
	}

	@Override
	public int getScale()
	{
		return obj.getScale();
	}

	@Override
	public void setTransparent(boolean enabled)
	{
		obj.setTransparent(enabled);
	}

	@Override
	public boolean isTransparent()
	{
		return obj.isTransparent();
	}

	@Override
	public void setOffColor(Color color)
	{
		obj.setOffColor(color);
	}

	@Override
	public Color getOffColor()
	{
		return obj.getOffColor();
	}

	/*
	 * IControl
	 */

	@Override
	public void setForeground(Color color)
	{
		obj.setForeground(color);
	}

	@Override
	public Color getForeground()
	{
		return obj.getForeground();
	}

	/*
	 * Clearable
	 */

	@Override
	public void clear()
	{
		obj.clear();
	}
}
