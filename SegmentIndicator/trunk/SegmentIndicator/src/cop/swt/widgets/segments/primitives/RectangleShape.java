package cop.swt.widgets.segments.primitives;

public final class RectangleShape extends AbstractBasicShape {
	private static final RectangleShape OBJ = new RectangleShape();

	public static RectangleShape create() {
		return OBJ;
	}

	private RectangleShape() {}

	/*
	 * BasicShape
	 */

	@Override
	public int[] getShape(int x, int y, int width, int height) {
		return getVerticalShape(x, y, width, height);
	}

	@Override
	public int[] getShape(int x, int y, int width, int height, int orientation) {
		return getShape(x, y, width, height);
	}

	/*
	 * static
	 */

	private static int[] getVerticalShape(int x, int y, int width, int height) {
		int[] arr = new int[8];
		int i = 0;
		int _x = x + width;
		int _y = y + height;

		i = addPoint(arr, i, x, y); // A
		i = addPoint(arr, i, _x, y); // B
		i = addPoint(arr, i, _x, _y); // C
		i = addPoint(arr, i, x, _y); // D

		return arr;
	}
}
