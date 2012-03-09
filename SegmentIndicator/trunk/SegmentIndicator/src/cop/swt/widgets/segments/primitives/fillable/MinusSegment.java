package cop.swt.widgets.segments.primitives.fillable;

import org.eclipse.swt.SWT;

import cop.swt.widgets.segments.primitives.RectangleShape;

public final class MinusSegment extends FillableSegment {
	public MinusSegment() {
		super(RectangleShape.create(), SWT.HORIZONTAL);
	}

	/*
	 * AbstractSegment
	 */

	@Override
	protected int getDefaultWidth() {
		return (getScale() <= 1) ? BASE_LENGTH : (BASE_LENGTH * getScale() - getScale() + 1);
	}

	@Override
	protected int getDefaultHeight() {
		return getScale();
	}
}
