package cop.swt.widgets.segments;

import static cop.common.extensions.ArrayExtension.EMPTY_INT_ARR;
import static cop.common.extensions.ArrayExtension.invertArray;
import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.ArrayExtension.toCollection;
import static cop.common.extensions.BitExt.isAnyBitSet;
import static cop.common.extensions.BitExt.isBitSet;
import static cop.common.extensions.CollectionExtension.convertToIntArray;
import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

import cop.swt.widgets.interfaces.Clearable;
import cop.swt.widgets.segments.interfaces.IControl;
import cop.swt.widgets.segments.interfaces.ISegment;
import cop.swt.widgets.segments.interfaces.ISegmentConfig;

public abstract class SegmentContainer<T> extends AbstractSegmentIndicator<SegmentIndicator, T> {
	protected static final int DEFAULT_ORIENTATION = SWT.HORIZONTAL | SWT.UP;
	protected static final int HORIZONTAL_ORIENTATION = SWT.UP | SWT.DOWN | SWT.HORIZONTAL;

	private static final int BETWEEN_SEGMENT = 1;

	protected final int totalSegments;
	private int space = 1;

	protected final ISegmentConfig config;

	protected SegmentContainer(Canvas canvas, int orientation, int totalSegments, ISegmentConfig config) {
		super(orientation);

		this.config = config;
		this.totalSegments = (totalSegments < 1) ? 1 : totalSegments;

		createParts();
		build();
		setCanvas(canvas);
	}

	public int getSpace() {
		return space;
	}

	public void setSpace(int space) {
		this.space = (space < 1) ? 1 : space;
	}

	private static int getSegmentOrientation(int orientation) {
		int mask = SWT.UP | SWT.DOWN | SWT.RIGHT | SWT.LEFT;
		int res = orientation & mask;

		if (isBitSet(orientation, SWT.HORIZONTAL))
			res |= SWT.HORIZONTAL;
		if (isBitSet(orientation, SWT.VERTICAL))
			res |= SWT.VERTICAL;

		return res;
	}

	/*
	 * AbstractSegmentIndicator
	 */

	@Override
	protected boolean isInverted(boolean horizontal) {
		return horizontal ? isBitSet(orientation, SWT.DOWN) : isBitSet(orientation, SWT.LEFT);
	}

	@Override
	protected void rebuild() {
		if (isEmpty(segments))
			return;

		for (SegmentIndicator segment : segments)
			segment.rebuild();
	}

	@Override
	public void setCanvas(Canvas canvas) {
		if (isEmpty(segments))
			return;

		for (SegmentIndicator segment : segments)
			segment.setCanvas(canvas);
	}

	@Override
	protected void buildVerticalOrientatedIndicator(boolean invert) {
		if (isEmpty(segments))
			return;

		int offs = 0;

		for (ISegment segment : invert ? invertArray(segments) : segments) {
			segment.setBounds(x, y + offs, getScale());
			offs += segment.getBounds().height + space;
		}
	}

	@Override
	protected void buildHorizontalOrientatedIndicator(boolean invert) {
		if (isEmpty(segments))
			return;

		int offs = 0;

		for (ISegment segment : invert ? invertArray(segments) : segments) {
			segment.setBounds(x + offs, y, getScale());
			offs += segment.getBounds().width + BETWEEN_SEGMENT;
		}
	}

	@Override
	public void redraw() {
		if (isEmpty(segments))
			return;

		for (SegmentIndicator segment : segments)
			segment.redraw();
	}

	/*
	 * AbstractSegment
	 */

	@Override
	protected int getDefaultWidth() {
		if (isEmpty(segments))
			return 0;

		int width = 0;

		for (ISegment segment : segments)
			width += segment.getBounds().width;

		width += space * (segments.length - 1);

		return width;
	}

	@Override
	protected int getDefaultHeight() {
		if (isEmpty(segments))
			return 0;

		int height = 0;

		for (ISegment segment : segments)
			height = max(height, segment.getBounds().height);

		return height;
	}

	@Override
	protected boolean isHorizontalOrientation() {
		return isAnyBitSet(orientation, HORIZONTAL_ORIENTATION);
	}

	@Override
	public void setOrientation(int orientation) {
		super.setOrientation(orientation);

		if (isEmpty(segments))
			return;

		boolean vis = visible;
		orientation = getSegmentOrientation(orientation);

		setVisible(false);

		for (ISegment segment : segments)
			if (segment instanceof AbstractSegment)
				((AbstractSegment)segment).setOrientation(orientation);

		build();
		setValue(value);
		setVisible(vis);
	}

	@Override
	protected int getDefaultOrientation() {
		return DEFAULT_ORIENTATION;
	}

	@Override
	public int[] getShape() {
		if (isEmpty(segments))
			return EMPTY_INT_ARR;

		List<Integer> shape = new ArrayList<Integer>();

		for (ISegment segment : segments)
			shape.addAll(toCollection(segment.getShape(), ArrayList.class));

		return convertToIntArray(shape);
	}

	@Override
	public int[] getShape(Rectangle rect) {
		if (isEmpty(segments))
			return EMPTY_INT_ARR;

		List<Integer> shape = new ArrayList<Integer>();

		for (ISegment segment : segments)
			shape.addAll(toCollection(segment.getShape(rect), ArrayList.class));

		return convertToIntArray(shape);
	}

	/*
	 * IControl
	 */

	@Override
	public void dispose() {
		if (isEmpty(segments))
			return;

		for (ISegment segment : segments)
			if (segment instanceof IControl)
				((IControl)segment).dispose();
	}

	@Override
	public void setVisible(boolean visible) {
		if (!(this.visible ^ visible))
			return;

		super.setVisible(visible);

		for (ISegment segment : segments)
			if (segment instanceof IControl)
				((IControl)segment).setVisible(visible);
	}

	/*
	 * ISegmentIndicator
	 */

	@Override
	public void setTransparent(boolean enabled) {
		if (isEmpty(segments))
			return;

		super.setTransparent(enabled);

		for (SegmentIndicator segment : segments)
			segment.setTransparent(enabled);

		redraw();
	}

	/*
	 * Clearable
	 */

	@Override
	public void clear() {
		if (isEmpty(segments))
			return;

		value = null;

		for (ISegment segment : segments)
			if (segment instanceof Clearable)
				((Clearable)segment).clear();
	}
}
