package cop.swt.widgets.segments;

import static org.eclipse.swt.SWT.DEFAULT;

import org.eclipse.swt.widgets.Canvas;

import cop.swt.widgets.segments.interfaces.ISegmentConfig;
import cop.swt.widgets.segments.tmp.SignPositionEnum;
import cop.swt.widgets.segments.tmp.SignTypeEnum;

public final class ByteNumber extends NumericSegmentContainer<Byte>
{
	private static final int TOTAL_SEGMENTS = 3;

	public static ByteNumber createByteNumber()
	{
		return createByteNumber(null, DEFAULT);
	}

	public static ByteNumber createPositiveByteNumber()
	{
		return createPositiveByteNumber(null, DEFAULT);
	}

	public static ByteNumber createNegativeByteNumber()
	{
		return createNegativeByteNumber(null, DEFAULT);
	}

	public static ByteNumber createByteNumber(Canvas canvas, int orientation)
	{
		return new ByteNumber(canvas, orientation, TOTAL_SEGMENTS);
	}

	public static ByteNumber createPositiveByteNumber(Canvas canvas, int orientation)
	{
		ByteNumber obj = new ByteNumber(canvas, orientation, TOTAL_SEGMENTS);

		obj.minimum = 0;

		return obj;
	}

	public static ByteNumber createNegativeByteNumber(Canvas canvas, int orientation)
	{
		ByteNumber obj = new ByteNumber(canvas, orientation, TOTAL_SEGMENTS);

		obj.maximum = 0;

		return obj;
	}

	private ByteNumber(Canvas canvas, int orientation, int totalSegmens)
	{
		super(canvas, new ByteNumberConfig(SignTypeEnum.PLUS_MINUS, SignPositionEnum.INSIDE, orientation, totalSegmens));

		minimum = Byte.MIN_VALUE;
		maximum = Byte.MAX_VALUE;
	}
}

class ByteNumberConfig implements ISegmentConfig
{
	private SignTypeEnum signType = SignTypeEnum.PLUS_MINUS;
	private SignPositionEnum signPosition = SignPositionEnum.OUTSIDE;
	private int orientation;
	private int totalSegmens;
	
	ByteNumberConfig(SignTypeEnum signType, SignPositionEnum signPosition, int orientation, int totalSegmens)
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
