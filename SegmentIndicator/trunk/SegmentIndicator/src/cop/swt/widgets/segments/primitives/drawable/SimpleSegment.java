/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.segments.primitives.drawable;

import static cop.common.extensions.ArrayExtension.EMPTY_INT_ARR;
import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.BitExt.isAnyBitSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;

import cop.swt.widgets.segments.AbstractSegment;
import cop.swt.widgets.segments.primitives.BasicShape;

/**
 * Basic class for simple that can't be divided into more smaller segments.
 * 
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 22.10.2010
 */
public abstract class SimpleSegment extends AbstractSegment {
	protected static final int DEFAULT_ORIENTATION = SWT.HORIZONTAL | SWT.UP;
	protected static final int HORIZONTAL_ORIENTATION = SWT.UP | SWT.DOWN | SWT.HORIZONTAL;

	private final BasicShape basicShape;

	private int[] points;

	protected SimpleSegment(BasicShape basicShape) {
		this(basicShape, DEFAULT_ORIENTATION);
	}

	protected SimpleSegment(BasicShape basicShape, int orientation) {
		super(orientation);

		this.basicShape = basicShape;
	}

	protected int[] getPoints() {
		return points;
	}

	protected int[] getPointArray() {
		return basicShape.getShape(x, y, width, height, getOrientation());
	}

	/*
	 * AbstractSegment
	 */

	@Override
	protected boolean isHorizontalOrientation() {
		return isAnyBitSet(getOrientation(), HORIZONTAL_ORIENTATION);
	}

	@Override
	protected int getDefaultOrientation() {
		return DEFAULT_ORIENTATION;
	}

	@Override
	protected final void build() {
		super.build();
		points = getPointArray();
	}

	/*
	 * IShape
	 */

	@Override
	public int[] getShape() {
		return isEmpty(points) ? EMPTY_INT_ARR : points.clone();
	}

	@Override
	public int[] getShape(Rectangle rect) {
		if (rect == null || isEmpty(points))
			return EMPTY_INT_ARR;

		int[] arr = new int[points.length];
		int len = 0;

		for (int i = 0, size = points.length; i < size; i += 2) {
			if (!rect.contains(points[i], points[i + 1]))
				continue;

			arr[len++] = points[i];
			arr[len++] = points[i + 1];
		}

		return arr;
	}
}
