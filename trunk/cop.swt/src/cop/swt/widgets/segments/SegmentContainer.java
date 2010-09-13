package cop.swt.widgets.segments;

import static cop.common.extensions.BitExtension.isAnyBitSet;
import static cop.common.extensions.BitExtension.isBitSet;
import static cop.common.extensions.CollectionExtension.convertToIntArray;
import static cop.common.extensions.CollectionExtension.invertArray;
import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.common.extensions.CollectionExtension.toCollection;
import static java.lang.Math.max;
import static org.eclipse.swt.SWT.DOWN;
import static org.eclipse.swt.SWT.HORIZONTAL;
import static org.eclipse.swt.SWT.LEFT;
import static org.eclipse.swt.SWT.RIGHT;
import static org.eclipse.swt.SWT.UP;
import static org.eclipse.swt.SWT.VERTICAL;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

import cop.swt.widgets.interfaces.Clearable;
import cop.swt.widgets.segments.interfaces.IControl;
import cop.swt.widgets.segments.interfaces.ISegment;

public abstract class SegmentContainer<T> extends AbstractSegmentIndicator<SegmentedIndicator, T>
{
	protected static final int DEFAULT_ORIENTATION = HORIZONTAL | UP;
	protected static final int HORIZONTAL_ORIENTATION = UP | DOWN | HORIZONTAL;

	private static final int BETWEEN_SEGMENT = 1;

	protected final int totalSegments;
	private int space = 1;

	public SegmentContainer(Shell shell, int orientation, int totalSegments)
	{
		super(orientation);

		this.totalSegments = (totalSegments < 1) ? 1 : totalSegments;

		createParts();
		build();
		setShell(shell);
	}

	public int getSpace()
	{
		return space;
	}

	public void setSpace(int space)
	{
		this.space = (space < 1) ? 1 : space;
	}

	private int getSegmentOrientation(int orientation)
	{
		int mask = UP | DOWN | RIGHT | LEFT;
		int res = orientation & mask;

		if(isBitSet(orientation, HORIZONTAL))
			res |= HORIZONTAL;
		if(isBitSet(orientation, VERTICAL))
			res |= VERTICAL;

		return res;
	}

	/*
	 * AbstractSegmentIndicator
	 */

	@Override
	protected boolean isInverted(boolean horizontal)
	{
		return horizontal ? isBitSet(orientation, DOWN) : isBitSet(orientation, LEFT);
	}

	@Override
	protected void rebuild()
	{
		if(isEmpty(segments))
			return;

		for(SegmentedIndicator segment : segments)
			segment.rebuild();
	}

	@Override
	public void setTransparent(boolean enabled)
	{
		if(isEmpty(segments))
			return;

		for(SegmentedIndicator segment : segments)
			segment.setTransparent(enabled);
	}

	@Override
	public void setShell(Shell shell)
	{
		if(isEmpty(segments))
			return;

		for(SegmentedIndicator segment : segments)
			segment.setShell(shell);
	}

	@Override
	protected void buildVerticalOrientatedIndicator(boolean invert)
	{
		if(isEmpty(segments))
			return;

		int offs = 0;

		for(ISegment segment : invert ? invertArray(segments) : segments)
		{
			segment.setBounds(x, y + offs, scale);
			offs += segment.getBounds().height + space;
		}
	}

	@Override
	protected void buildHorizontalOrientatedIndicator(boolean invert)
	{
		if(isEmpty(segments))
			return;

		int offs = 0;

		for(ISegment segment : invert ? invertArray(segments) : segments)
		{
			segment.setBounds(x + offs, y, scale);
			offs += segment.getBounds().width + BETWEEN_SEGMENT;
		}
	}

	@Override
	public void redraw()
	{
		if(isEmpty(segments))
			return;

		for(SegmentedIndicator segment : segments)
			segment.redraw();
	}

	/*
	 * AbstractSegment
	 */

	// @Override
	// protected int getWidth()
	// {
	// return isHorizontalOrientation() ? getDefaultHeight() : getDefaultWidth();
	// }
	//
	// @Override
	// protected int getHeight()
	// {
	// return isHorizontalOrientation() ? getDefaultWidth() : getDefaultHeight();
	// }

	@Override
	protected int getDefaultWidth()
	{
		if(isEmpty(segments))
			return 0;

		int width = 0;

		for(ISegment segment : segments)
			width += segment.getBounds().width;

		width += space * (segments.length - 1);

		return width;
	}

	@Override
	protected int getDefaultHeight()
	{
		if(isEmpty(segments))
			return 0;

		int height = 0;

		for(ISegment segment : segments)
			height = max(height, segment.getBounds().height);

		return height;
	}

	@Override
	protected boolean isHorizontalOrientation()
	{
		return isAnyBitSet(orientation, HORIZONTAL_ORIENTATION);
	}

	@Override
	public void setOrientation(int orientation)
	{
		super.setOrientation(orientation);

		if(isEmpty(segments))
			return;

		boolean vis = visible;
		orientation = getSegmentOrientation(orientation);

		setVisible(false);

		for(ISegment segment : segments)
			if(segment instanceof AbstractSegment)
				((AbstractSegment)segment).setOrientation(orientation);

		build();
		setValue(value);
		setVisible(vis);
	}

	@Override
	protected int getDefaultOrientation()
	{
		return DEFAULT_ORIENTATION;
	}

	@Override
	public int[] getShape()
	{
		if(isEmpty(segments))
			return new int[0];

		List<Integer> shape = new ArrayList<Integer>();

		for(ISegment segment : segments)
			shape.addAll(toCollection(segment.getShape(), ArrayList.class));

		return convertToIntArray(shape);
	}

	@Override
	public int[] getShape(Rectangle rect)
	{
		if(isEmpty(segments))
			return new int[0];

		List<Integer> shape = new ArrayList<Integer>();

		for(ISegment segment : segments)
			shape.addAll(toCollection(segment.getShape(rect), ArrayList.class));

		return convertToIntArray(shape);
	}

	/*
	 * IControl
	 */

	@Override
	public void dispose()
	{
		if(isEmpty(segments))
			return;

		for(ISegment segment : segments)
			if(segment instanceof IControl)
				((IControl)segment).dispose();
	}

	@Override
	public void setVisible(boolean visible)
	{
		if(!(this.visible ^ visible))
			return;

		super.setVisible(visible);

		for(ISegment segment : segments)
			if(segment instanceof IControl)
				((IControl)segment).setVisible(visible);
	}

	/*
	 * Clearable
	 */

	@Override
	public void clear()
	{
		if(isEmpty(segments))
			return;

		value = null;

		for(ISegment segment : segments)
			if(segment instanceof Clearable)
				((Clearable)segment).clear();
	}
}
