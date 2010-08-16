package cop.swt.widgets.segments.primitives;

import static cop.common.extensions.BitExtension.isAnyBitSet;
import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.common.extensions.CollectionExtension.isNotEmpty;
import static cop.common.extensions.CommonExtension.isNull;
import static org.eclipse.swt.SWT.DOWN;
import static org.eclipse.swt.SWT.HORIZONTAL;
import static org.eclipse.swt.SWT.UP;
import static org.eclipse.swt.SWT.VERTICAL;

import java.util.Arrays;

import org.eclipse.swt.graphics.Rectangle;

import cop.swt.widgets.segments.AbstractSegment;

public abstract class SimpleSegment extends AbstractSegment
{
	protected static final int DEF_ORIENTATION = VERTICAL | UP;
	protected static final int HORIZONTAL_ORIENTATION = UP | DOWN | HORIZONTAL;

	protected int[] pointArray;

	protected SimpleSegment()
	{
		this(DEF_ORIENTATION);
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
		return DEF_ORIENTATION;
	}

	@Override
	protected int getWidth()
	{
		if(isHorizontalOrientation())
			return getDefaultWidth();

		return getDefaultHeight();
	}

	@Override
	protected int getHeight()
	{
		if(isHorizontalOrientation())
			return getDefaultHeight();

		return getDefaultWidth();

	}

	@Override
	protected final void build()
	{
		super.build();
		pointArray = getPointArray();
	}

	/*
	 * IShape
	 */

	@Override
	public int[] getShape()
	{
		return isNotEmpty(pointArray) ? pointArray.clone() : new int[0];
	}

	@Override
	public int[] getShape(Rectangle rect)
	{
		if(isNull(rect) || isEmpty(pointArray))
			return new int[0];

		int[] arr = new int[pointArray.length];
		int len = 0;

		for(int i = 0, size = pointArray.length; i < size; i += 2)
		{
			if(!rect.contains(pointArray[i], pointArray[i + 1]))
				continue;

			arr[len++] = pointArray[i];
			arr[len++] = pointArray[i + 1];
		}

		return Arrays.copyOf(arr, len);
	}
}
