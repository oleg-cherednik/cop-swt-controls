package cop.swt.widgets.segments.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.segments.ByteNumber;

public class ByteIndicator extends SegmentViewer<Byte>
{
	public ByteIndicator(Composite parent, int style)
	{
		super(parent, style, ByteNumber.createByteNumber(null, SWT.DEFAULT));
	}
}
