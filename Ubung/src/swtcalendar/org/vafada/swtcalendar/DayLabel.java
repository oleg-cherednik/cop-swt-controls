package swtcalendar.org.vafada.swtcalendar;

import static org.eclipse.swt.SWT.DEFAULT;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

class DayLabel extends Label
{
	public DayLabel(Composite parent, int style)
	{
		super(parent, style);
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed)
	{
		if(wHint == DEFAULT)
		{
			GC gc = new GC(this);

			wHint = gc.textExtent("22").x;
			gc.dispose();
		}

		return super.computeSize(wHint, hHint, changed);
	}

	/*
	 * Widget
	 */

	@Override
    protected void checkSubclass()
	{}
}
