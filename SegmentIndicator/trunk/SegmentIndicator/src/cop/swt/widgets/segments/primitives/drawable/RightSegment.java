package cop.swt.widgets.segments.primitives.drawable;

import org.eclipse.swt.SWT;

import cop.swt.widgets.segments.primitives.TriangleShape;

public final class RightSegment extends DrawableSegment {
	public static SimpleSegment create() {
		return create(false);
	}

	public static SimpleSegment create(boolean invert) {
		return invert ? new LeftSegment() : new RightSegment();
	}

	RightSegment() {
		super(TriangleShape.create(), SWT.LEFT);
	}
}
