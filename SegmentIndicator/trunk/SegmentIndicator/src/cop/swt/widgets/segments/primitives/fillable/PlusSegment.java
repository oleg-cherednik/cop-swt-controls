package cop.swt.widgets.segments.primitives.fillable;

import org.eclipse.swt.SWT;

import cop.swt.widgets.segments.primitives.PlusShape;

public final class PlusSegment extends FillableSegment {
	public PlusSegment() {
		super(PlusShape.create(), SWT.HORIZONTAL);
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
		return (getScale() <= 1) ? (BASE_LENGTH - 1) : ((BASE_LENGTH - 1) * getScale());
	}
}
