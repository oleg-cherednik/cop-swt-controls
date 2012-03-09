package cop.swt.widgets.segments.primitives;

abstract class AbstractBasicShape implements BasicShape {
	/*
	 * static
	 */

	protected static int addPoint(int[] arr, int offs, int x, int y) {
		arr[offs++] = x;
		arr[offs++] = y;

		return offs;
	}
}
