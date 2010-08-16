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
		int _y = y + width;

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

	public static int[] createPlus(int x, int y, int width)
	{
		int[] arr = new int[24];
		int step = width / 4;

		if(width <= 4)
		{
			width = 3;
			step = 1;
		}

		
		int i = 0;

		int x1 = x + step;
		int x2 = width - step;
		int x3 = x + width;

		
		
		int y1 = x1;
		int y2 = x2;
		int y3 = x3;
		
		




		i = addPoint(arr, i, x, y1); // A
		i = addPoint(arr, i, x1, y1); // B
		i = addPoint(arr, i, x1, y); // C
		i = addPoint(arr, i, x2, y); // D
		i = addPoint(arr, i, x2, y1); // E
		i = addPoint(arr, i, x3, y1); // F
		i = addPoint(arr, i, x3, y2); // G
		i = addPoint(arr, i, x2, y2); // H
		i = addPoint(arr, i, x2, y3); // I
		i = addPoint(arr, i, x1, y3); // J
		i = addPoint(arr, i, x1, y2); // K
		i = addPoint(arr, i, x, y2); // L

		return arr;
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
			arr[i++] = x + j;
			arr[i++] = y + j;
			arr[i++] = x + j;
			arr[i++] = y + height - 1 - j;
		}

		return arr;
	}

	private static int[] createLeftOrientatedTriangle(int x, int y, int width, int height)
	{
		int size = width << 2;
		int[] arr = new int[size];

		for(int i = 0, j = 0; i < size; j++)
		{
			arr[i++] = x - j;
			arr[i++] = y + j;
			arr[i++] = x - j;
			arr[i++] = y + height - 1 - j;
		}

		return arr;
	}

	private static int[] createDownOrientatedTriangle(int x, int y, int width, int height)
	{
		int size = height << 2;
		int[] arr = new int[size];

		for(int i = 0, j = 0; i < size; j++)
		{
			arr[i++] = x + j;
			arr[i++] = y + j;
			arr[i++] = x + width - 1 - j;
			arr[i++] = y + j;
		}

		return arr;
	}

	private static int[] createUpOrientatedTriangle(int x, int y, int width, int height)
	{
		int size = height << 2;
		int[] arr = new int[size];

		for(int i = 0, j = 0; i < size; j++)
		{
			arr[i++] = x + j;
			arr[i++] = y - j;
			arr[i++] = x + width - 1 - j;
			arr[i++] = y - j;
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
			arr[i++] = x - j;
			arr[i++] = y + j;
			arr[i++] = x - j;
			arr[i++] = y + height - 1 - j;
		}

		arr[i++] = x;
		arr[i++] = y;
		arr[i++] = x;
		arr[i++] = y + height - 1;

		for(int j = 1; i < size; j++)
		{
			arr[i++] = x + j;
			arr[i++] = y + j;
			arr[i++] = x + j;
			arr[i++] = y + height - 1 - j;
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
			arr[i++] = x + j;
			arr[i++] = y - j;
			arr[i++] = x + width - 1 - j;
			arr[i++] = y - j;
		}

		arr[i++] = x;
		arr[i++] = y;
		arr[i++] = x + width - 1;
		arr[i++] = y;

		for(int j = 1; i < size; j++)
		{
			arr[i++] = x + j;
			arr[i++] = y + j;
			arr[i++] = x + width - 1 - j;
			arr[i++] = y + j;
		}

		return arr;
	}
}
