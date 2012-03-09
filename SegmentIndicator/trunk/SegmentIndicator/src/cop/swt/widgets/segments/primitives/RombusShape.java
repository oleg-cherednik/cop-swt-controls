package cop.swt.widgets.segments.primitives;

import org.eclipse.swt.SWT;

import cop.common.extensions.BitExt;

public final class RombusShape extends AbstractBasicShape {
	private static final RombusShape OBJ = new RombusShape();

	public static RombusShape create() {
		return OBJ;
	}

	private RombusShape() {}

	/*
	 * BasicShape
	 */

	@Override
	public int[] getShape(int x, int y, int width, int height) {
		return getShape(x, y, width, height, SWT.HORIZONTAL);
	}

	@Override
	public int[] getShape(int x, int y, int width, int height, int orientation) {
		if (BitExt.isBitSet(orientation, SWT.HORIZONTAL))
			return getHorizontalShape(x, y, width, height);
		return getVerticalShape(x, y, width, height);
	}

	/*
	 * static
	 */

	private static int[] getVerticalShape(int x, int y, int width, int height) {
		int size = width << 2;
		int[] arr = new int[size];
		int k = (width - 1) >> 1;
		int i = 0;

		for (int j = k, lim = k << 2; i < lim; j--) {
			i = addPoint(arr, i, x - j, y + j);
			i = addPoint(arr, i, x - j, y + height - 1 - j);
		}

		i = addPoint(arr, i, x, y);
		i = addPoint(arr, i, x, y + height - 1);

		for (int j = 1; i < size; j++) {
			i = addPoint(arr, i, x + j, y + j);
			i = addPoint(arr, i, x + j, y + height - 1 - j);
		}

		return arr;
	}

	private static int[] getHorizontalShape(int x, int y, int width, int height) {
		int size = height << 2;
		int[] arr = new int[size];
		int k = (height - 1) >> 1;
		int i = 0;

		for (int j = k, lim = k << 2; i < lim; j--) {
			i = addPoint(arr, i, x + j, y - j);
			i = addPoint(arr, i, x + width - 1 - j, y - j);
		}

		i = addPoint(arr, i, x, y);
		i = addPoint(arr, i, x + width - 1, y);

		for (int j = 1; i < size; j++) {
			i = addPoint(arr, i, x + j, y + j);
			i = addPoint(arr, i, x + width - 1 - j, y + j);
		}

		return arr;
	}
}
