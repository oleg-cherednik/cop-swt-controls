package cop.swt.widgets.calendar.viewers;

import static org.eclipse.swt.SWT.DEFAULT;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

class SpecialLabel extends Label
{
	public SpecialLabel(Composite parent, int style)
	{
		super(parent, style);
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed)
	{
		if(wHint == DEFAULT)
		{
			GC gc = new GC(this);
			wHint = gc.textExtent("22").x + 5;
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

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		return getText();
	}
}
