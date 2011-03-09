package cop.swt.widgets.segments.groups.numeric;

import static org.eclipse.swt.SWT.DEFAULT;

import org.eclipse.swt.widgets.Canvas;

import cop.swt.widgets.segments.interfaces.ISegmentConfig;
import cop.swt.widgets.segments.tmp.SignPositionEnum;
import cop.swt.widgets.segments.tmp.SignTypeEnum;

public final class ShortSegment extends NumericSegmentContainer<Short>
{
	private static final int TOTAL_SEGMENTS = 5;

	public static ShortSegment create()
	{
		return new ShortSegment(null, DEFAULT);
	}

	public static ShortSegment create(Canvas canvas, int orientation)
	{
		return new ShortSegment(canvas, orientation);
	}

	public static ShortSegment createPositive(Canvas canvas, int orientation)
	{
		ShortSegment obj = new ShortSegment(canvas, orientation);

		obj.minimum = 0;

		return obj;
	}

	public static ShortSegment createNegative(Canvas canvas, int orientation)
	{
		ShortSegment obj = new ShortSegment(canvas, orientation);

		obj.maximum = 0;

		return obj;
	}

	private ShortSegment(Canvas canvas, int orientation)
	{
		super(canvas, new ShortNumberConfig(SignTypeEnum.PLUS_MINUS, SignPositionEnum.INSIDE, orientation,
		                TOTAL_SEGMENTS));

		minimum = Short.MIN_VALUE;
		maximum = Short.MAX_VALUE;
	}
}

class ShortNumberConfig implements ISegmentConfig
{
	private SignTypeEnum signType = SignTypeEnum.PLUS_MINUS;
	private SignPositionEnum signPosition = SignPositionEnum.OUTSIDE;
	private int orientation;
	private int totalSegmens;

	ShortNumberConfig(SignTypeEnum signType, SignPositionEnum signPosition, int orientation, int totalSegmens)
	{
		this.signType = signType;
		this.orientation = orientation;
		this.totalSegmens = totalSegmens;
		this.signPosition = signPosition;
	}

	@Override
	public SignTypeEnum getSignType()
	{
		return signType;
	}

	@Override
	public SignPositionEnum getSignPosition()
	{
		return signPosition;
	}

	@Override
	public int getOrientation()
	{
		return orientation;
	}

	@Override
	public int getTotalSegments()
	{
		return totalSegmens;
	}

	@Override
	public boolean isLeadingZero()
	{
		return false;
	}
}
