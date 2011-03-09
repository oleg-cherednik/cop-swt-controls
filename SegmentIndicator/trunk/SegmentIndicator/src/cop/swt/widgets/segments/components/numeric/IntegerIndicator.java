package cop.swt.widgets.segments.components.numeric;

import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.segments.groups.numeric.IntegerSegment;

public class IntegerIndicator extends SegmentViewer<Integer>
{
	public IntegerIndicator(Composite parent, int style)
	{
		super(parent, style, IntegerSegment.create());
	}
}
