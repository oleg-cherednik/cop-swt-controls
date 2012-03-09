package cop.swt.widgets.segments.primitives.drawable;

import static org.eclipse.swt.SWT.LEFT;
import cop.swt.widgets.segments.primitives.TriangleShape;

public final class RightSegment extends DrawableSegment {
	public static SimpleSegment create() {
		return create(false);
	}

	public static SimpleSegment create(boolean invert) {
		return invert ? new LeftSegment() : new RightSegment();
	}

	RightSegment() {
		super(LEFT);
	}

	/*
	 * SimpleSegment
	 */

	@Override
	protected int[] getPointArray() {
		return TriangleShape.create().getShape(x, y, width, height, getOrientation());
	}
}
