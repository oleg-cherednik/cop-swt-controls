package cop.swt.widgets.segments;

import static cop.common.extensions.BitExtension.isBitSet;
import static org.eclipse.swt.SWT.LEFT;
import static org.eclipse.swt.SWT.RIGHT;
import static org.eclipse.swt.SWT.UP;
import static org.eclipse.swt.SWT.VERTICAL;

public final class ShapeBasics
{
	private ShapeBasics()
	{}

	public static int[] createRectangle(int x, int y, int width, int height)
	{
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

	/**
	 * SWT.LEFT; SWT.RIGHT; SWT.UP; SWT.DOWN;
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param orientation
	 * @return
	 */
	public static int[] createTriangle(int x, int y, int width, int height, int orientation)
	{
		if(isBitSet(orientation, RIGHT))
			return createRightOrientatedTriangle(x, y, width, height);

		if(isBitSet(orientation, LEFT))
			return createLeftOrientatedTriangle(x, y, width, height);

		if(isBitSet(orientation, UP))
			return createUpOrientatedTriangle(x, y, width, height);

		return createDownOrientatedTriangle(x, y, width, height);
	}

	/**
	 * SWT.VERTICAL SWT.HORIZONTAL
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param orientation
	 * @return
	 */
	public static int[] createRhombus(int x, int y, int width, int height, int orientation)
	{
		if(isBitSet(orientation, VERTICAL))
			return createVerticalRhombus(x, y, width, height);

		return createHorizontalRhombus(x, y, width, height);
	}

	public static int[] createPlus(int x, int y, int width, int height, int orientation)
	{
		if(isBitSet(orientation, VERTICAL))
			return createVerticalPlus(x, y, width, height);

		return createHorizontalPlus(x, y, width, height);
	}

	private static int addPoint(int[] arr, int offs, int x, int y)
	{
		arr[offs++] = x;
		arr[offs++] = y;

		return offs;
	}

	private static int[] createRightOrientatedTriangle(int x, int y, int width, int height)
	{
		int size = width << 2;
		int[] arr = new int[size];

		for(int i = 0, j = 0; i < size; j++)
		{
			i = addPoint(arr, i, x + j, y + j);
			i = addPoint(arr, i, x + j, y + height - 1 - j);
		}

		return arr;
	}

	private static int[] createLeftOrientatedTriangle(int x, int y, int width, int height)
	{
		int size = width << 2;
		int[] arr = new int[size];

		for(int i = 0, j = 0; i < size; j++)
		{
			i = addPoint(arr, i, x - j, y + j);
			i = addPoint(arr, i, x - j, y + height - 1 - j);
		}

		return arr;
	}

	private static int[] createDownOrientatedTriangle(int x, int y, int width, int height)
	{
		int size = height << 2;
		int[] arr = new int[size];

		for(int i = 0, j = 0; i < size; j++)
		{
			i = addPoint(arr, i, x + j, y + j);
			i = addPoint(arr, i, x + width - 1 - j, y + j);
		}

		return arr;
	}

	private static int[] createUpOrientatedTriangle(int x, int y, int width, int height)
	{
		int size = height << 2;
		int[] arr = new int[size];

		for(int i = 0, j = 0; i < size; j++)
		{
			i = addPoint(arr, i, x + j, y - j);
			i = addPoint(arr, i, x + width - 1 - j, y - j);
		}

		return arr;
	}

	private static int[] createVerticalRhombus(int x, int y, int width, int height)
	{
		int size = width << 2;
		int[] arr = new int[size];
		int k = (width - 1) >> 1;
		int i = 0;

		for(int j = k, lim = k << 2; i < lim; j--)
		{
			i = addPoint(arr, i, x - j, y + j);
			i = addPoint(arr, i, x - j, y + height - 1 - j);
		}

		i = addPoint(arr, i, x, y);
		i = addPoint(arr, i, x, y + height - 1);

		for(int j = 1; i < size; j++)
		{
			i = addPoint(arr, i, x + j, y + j);
			i = addPoint(arr, i, x + j, y + height - 1 - j);
		}

		return arr;
	}

	private static int[] createHorizontalRhombus(int x, int y, int width, int height)
	{
		int size = height << 2;
		int[] arr = new int[size];
		int k = (height - 1) >> 1;
		int i = 0;

		for(int j = k, lim = k << 2; i < lim; j--)
		{
			i = addPoint(arr, i, x + j, y - j);
			i = addPoint(arr, i, x + width - 1 - j, y - j);
		}

		i = addPoint(arr, i, x, y);
		i = addPoint(arr, i, x + width - 1, y);

		for(int j = 1; i < size; j++)
		{
			i = addPoint(arr, i, x + j, y + j);
			i = addPoint(arr, i, x + width - 1 - j, y + j);
		}

		return arr;
	}

	private static int[] createVerticalPlus(int x, int y, int width, int height)
	{
		int[] arr = new int[24];
		int step = width / 3;

		int i = 0;

		int x1 = x + step;
		int x3 = x + width;
		int x2 = x3 - step;

		int y1 = y + step;
		int y3 = y + height;
		int y2 = y3 - step;

		i = addPoint(arr, i, x, y1);
		i = addPoint(arr, i, x1, y1);
		i = addPoint(arr, i, x1, y);
		i = addPoint(arr, i, x2, y);
		i = addPoint(arr, i, x2, y1);
		i = addPoint(arr, i, x3, y1);
		i = addPoint(arr, i, x3, y2);
		i = addPoint(arr, i, x2, y2);
		i = addPoint(arr, i, x2, y3);
		i = addPoint(arr, i, x1, y3);
		i = addPoint(arr, i, x1, y2);
		i = addPoint(arr, i, x, y2);

		return arr;
	}

	private static int[] createHorizontalPlus(int x, int y, int width, int height)
	{
		int[] arr = new int[24];
		int step = width / 3;

		int i = 0;

		int x1 = x + step;
		int x3 = x + width;
		int x2 = x3 - step;

		int y1 = y + step;
		int y3 = y + height;
		int y2 = y3 - step;

		i = addPoint(arr, i, x, y1);
		i = addPoint(arr, i, x1, y1);
		i = addPoint(arr, i, x1, y);
		i = addPoint(arr, i, x2, y);
		i = addPoint(arr, i, x2, y1);
		i = addPoint(arr, i, x3, y1);
		i = addPoint(arr, i, x3, y2);
		i = addPoint(arr, i, x2, y2);
		i = addPoint(arr, i, x2, y3);
		i = addPoint(arr, i, x1, y3);
		i = addPoint(arr, i, x1, y2);
		i = addPoint(arr, i, x, y2);

		return arr;
	}
}
