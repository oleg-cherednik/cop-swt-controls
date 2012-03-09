package cop.swt.widgets.segments.primitives;

public interface BasicShape {
	int[] getShape(int x, int y, int width, int height);

	int[] getShape(int x, int y, int width, int height, int orientation);
}
