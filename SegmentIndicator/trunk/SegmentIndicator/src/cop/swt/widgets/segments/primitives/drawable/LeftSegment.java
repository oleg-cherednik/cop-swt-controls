package cop.swt.widgets.segments.primitives.drawable;

import static org.eclipse.swt.SWT.RIGHT;
import cop.swt.widgets.segments.primitives.TriangleShape;

public final class LeftSegment extends DrawableSegment {
	public static SimpleSegment create() {
		return create(false);
	}

	public static SimpleSegment create(boolean invert) {
		return invert ? new RightSegment() : new LeftSegment();
	}

	LeftSegment() {
		super(RIGHT);
	}

	/*
	 * SimpleSegment
	 */

	@Override
	protected int[] getPointArray() {
		return TriangleShape.create().getShape(x, y, width, height, getOrientation());
	}
}
