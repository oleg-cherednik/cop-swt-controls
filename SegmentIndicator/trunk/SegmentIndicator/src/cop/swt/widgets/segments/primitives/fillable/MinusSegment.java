package cop.swt.widgets.segments.primitives.fillable;

import static org.eclipse.swt.SWT.HORIZONTAL;
import cop.swt.widgets.segments.primitives.RectangleShape;

public final class MinusSegment extends FillableSegment {
	public MinusSegment() {
		super(HORIZONTAL);
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

	/*
	 * SimpleSegment
	 */

	@Override
	protected int[] getPointArray() {
		return RectangleShape.create().getShape(x, y, width, height);
	}
}
