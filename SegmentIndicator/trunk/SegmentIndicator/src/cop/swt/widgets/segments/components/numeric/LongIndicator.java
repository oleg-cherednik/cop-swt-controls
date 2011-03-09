package cop.swt.widgets.segments.components.numeric;

import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.segments.groups.numeric.LongSegment;

public class LongIndicator extends SegmentViewer<Long>
{
	public LongIndicator(Composite parent, int style)
	{
		super(parent, style, LongSegment.create());
	}
}
