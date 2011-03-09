package cop.swt.widgets.segments.components.numeric;

import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.segments.groups.numeric.ByteSegment;

public class ByteIndicator extends SegmentViewer<Byte>
{
	public ByteIndicator(Composite parent, int style)
	{
		super(parent, style, ByteSegment.create());
	}
}
