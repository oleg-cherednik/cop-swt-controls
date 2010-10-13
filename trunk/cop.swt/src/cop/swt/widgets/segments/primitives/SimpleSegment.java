package cop.swt.widgets.segments.primitives;

import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.ArrayExtension.isNotEmpty;
import static cop.common.extensions.BitExtension.isAnyBitSet;
import static cop.common.extensions.CommonExtension.isNull;
import static org.eclipse.swt.SWT.DOWN;
import static org.eclipse.swt.SWT.HORIZONTAL;
import static org.eclipse.swt.SWT.UP;

import java.util.Arrays;

import org.eclipse.swt.graphics.Rectangle;

import cop.swt.widgets.segments.AbstractSegment;

public abstract class SimpleSegment extends AbstractSegment
{
	protected static final int DEFAULT_ORIENTATION = HORIZONTAL | UP;
	protected static final int HORIZONTAL_ORIENTATION = UP | DOWN | HORIZONTAL;

	protected int[] points;

	protected SimpleSegment()
	{
		this(DEFAULT_ORIENTATION);
	}

	public SimpleSegment(int orientation)
	{
		super(orientation);
	}

	abstract protected int[] getPointArray();

	/*
	 * AbstractSegment
	 */

	@Override
	protected boolean isHorizontalOrientation()
	{
		return isAnyBitSet(orientation, HORIZONTAL_ORIENTATION);
	}

	@Override
	protected int getDefaultOrientation()
	{
		return DEFAULT_ORIENTATION;
	}

	@Override
	protected final void build()
	{
		super.build();
		points = getPointArray();
	}

	/*
	 * IShape
	 */

	@Override
	public int[] getShape()
	{
		return isNotEmpty(points) ? points.clone() : new int[0];
	}

	@Override
	public int[] getShape(Rectangle rect)
	{
		if(isNull(rect) || isEmpty(points))
			return new int[0];

		int[] arr = new int[points.length];
		int len = 0;

		for(int i = 0, size = points.length; i < size; i += 2)
		{
			if(!rect.contains(points[i], points[i + 1]))
				continue;

			arr[len++] = points[i];
			arr[len++] = points[i + 1];
		}

		return Arrays.copyOf(arr, len);
	}
}
