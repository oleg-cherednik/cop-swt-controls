package cop.swt.widgets.segments.primitives.drawable;

import static org.eclipse.swt.SWT.DOWN;
import cop.swt.widgets.segments.primitives.TriangleShape;

public final class TopSegment extends DrawableSegment {
	public static SimpleSegment create() {
		return create(false);
	}

	public static SimpleSegment create(boolean invert) {
		return invert ? new BottomSegment() : new TopSegment();
	}

	TopSegment() {
		super(TriangleShape.create(), DOWN);
	}
}
