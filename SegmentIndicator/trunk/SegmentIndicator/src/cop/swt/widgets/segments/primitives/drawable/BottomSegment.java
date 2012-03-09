package cop.swt.widgets.segments.primitives.drawable;

import static org.eclipse.swt.SWT.UP;
import cop.swt.widgets.segments.primitives.TriangleShape;

public final class BottomSegment extends DrawableSegment {
	public static SimpleSegment create() {
		return create(false);
	}

	public static SimpleSegment create(boolean invert) {
		return invert ? new TopSegment() : new BottomSegment();
	}

	BottomSegment() {
		super(UP);
	}

	/*
	 * SimpleSegment
	 */

	@Override
	protected int[] getPointArray() {
		return TriangleShape.create().getShape(x, y, width, height, getOrientation());
	}
}
