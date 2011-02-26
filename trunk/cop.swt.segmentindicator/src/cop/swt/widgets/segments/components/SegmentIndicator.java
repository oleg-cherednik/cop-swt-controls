package cop.swt.widgets.segments.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.segments.ByteNumber;
import cop.swt.widgets.segments.interfaces.IControl;

public class SegmentIndicator extends Canvas implements PaintListener, DisposeListener, IControl<Byte>
{
	private final ByteNumber indicator;

	public SegmentIndicator(Composite parent, int style)
	{
		super(parent, style);

		indicator = ByteNumber.createByteNumber(this, SWT.DEFAULT);

		addListeners();
	}

	private void addListeners()
	{
		addPaintListener(this);
		addDisposeListener(this);
	}

	public void setScale(int scale)
	{
		indicator.setScale(scale);
	}

	public int getScale()
	{
		return indicator.getScale();
	}

	/*
	 * Composite
	 */

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed)
	{
		checkWidget();
		return indicator.getSize();
	}

	/*
	 * PaintListener
	 */

	public void paintControl(PaintEvent e)
	{
		indicator.redraw(e.x, e.y, e.width, e.height);
	}

	/*
	 * DisposeListener
	 */

	public void widgetDisposed(DisposeEvent e)
	{
		indicator.dispose();
	}

	/*
	 * IControl
	 */

	@Override
	public Byte getValue()
	{
		return indicator.getValue();
	}

	@Override
	public void setValue(Byte value)
	{
		indicator.setValue(value);
	}
}
