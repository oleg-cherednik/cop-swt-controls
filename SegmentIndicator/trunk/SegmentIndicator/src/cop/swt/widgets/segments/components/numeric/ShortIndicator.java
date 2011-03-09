package cop.swt.widgets.segments.components.numeric;

import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.segments.groups.numeric.ShortSegment;

public class ShortIndicator extends SegmentViewer<Short>
{
	public ShortIndicator(Composite parent, int style)
	{
		super(parent, style, ShortSegment.create());
	}
}
