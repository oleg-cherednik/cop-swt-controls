package cop.swt.widgets.segments.groups.numeric;

import static cop.common.extensions.CollectionExtension.replaceAll;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.NumericExtension.isInRangeMinMax;
import static cop.common.extensions.NumericExtension.toCharArray;

import org.eclipse.swt.widgets.Canvas;

import cop.swt.widgets.segments.SegmentContainer;
import cop.swt.widgets.segments.SegmentIndicator;
import cop.swt.widgets.segments.SignSegment;
import cop.swt.widgets.segments.interfaces.ISegmentConfig;
import cop.swt.widgets.segments.seven.DigitalNumericSevenSegment;
import cop.swt.widgets.segments.tmp.SignPositionEnum;

public abstract class NumericSegmentContainer<T extends Number> extends SegmentContainer<T>
{
	protected T minimum;
	protected T maximum;

	protected NumericSegmentContainer(Canvas canvas, int orientation, int totalSegments)
	{
		this(canvas, orientation, totalSegments, false);
	}

	protected NumericSegmentContainer(Canvas canvas, int orientation, int totalSegments, boolean signSegment)
	{
		super(canvas, orientation, totalSegments, null);
	}

	protected NumericSegmentContainer(Canvas canvas, ISegmentConfig config)
	{
		super(canvas, config.getOrientation(), config.getTotalSegments(), config);
	}

	private void fillSegments()
	{
		for(SegmentIndicator segment : segments)
		{
			if(!config.isLeadingZero() || (segment instanceof SignSegment))
				segment.setValue(null);
			else
				segment.setValue('0');
		}
	}

	private void setSignMarker(char[] arr)
	{
		boolean negative = arr[0] == '-';

		if(isZero(arr))
			segments[0].setValue(null);
		else if(!negative)
			segments[0].setValue((segments[0] instanceof SignSegment) ? '+' : null);
		else if(config.getSignPosition() == SignPositionEnum.OUTSIDE || config.isLeadingZero())
		{
			segments[0].setValue('-');
			replaceAll(arr, '-', '\0');
		}
		else
			segments[0].setValue(null);
	}

	private static boolean isZero(char[] arr)
	{
		return arr[0] == '0' && arr.length == 1;
	}

	/*
	 * AbstractSegmentIndicator
	 */

	@Override
	protected void createHorizontalOrientatedParts(boolean invert)
	{
		int i = 0;
		int totalSegments = config.getTotalSegments();
		SignPositionEnum signPosition = config.getSignPosition();

		if(signPosition != SignPositionEnum.NONE)
			totalSegments++;

		segments = new SegmentIndicator[totalSegments];

		if(signPosition == SignPositionEnum.OUTSIDE)
		{
			switch(config.getSignType())
			{
			case PLUS_MINUS:
			case PLUS:
			case MINUS:
			default:
				segments[i++] = new SignSegment(x, y, getScale());
				break;
			}
		}

		for(; i < totalSegments; i++)
			segments[i] = new DigitalNumericSevenSegment(x, y, getScale());
	}

	@Override
	protected void createVerticalOrientatedParts(boolean invert)
	{
		segments = new DigitalNumericSevenSegment[totalSegments];

		for(int i = 0; i < totalSegments; i++)
			segments[i] = new DigitalNumericSevenSegment(x, y, getScale());
	}

	@Override
	protected void _setValue()
	{
		if(isNull(value))
			clear();
		else if(isInRangeMinMax(value, minimum, maximum))
		{
			int j = segments.length - 1;
			char[] arr = toCharArray(value);

			fillSegments();
			setSignMarker(arr);

			for(int i = arr.length - 1; i >= 0; i--, j--)
				if(arr[i] != '\0')
					segments[j].setValue(arr[i]);
		}
	}
}
