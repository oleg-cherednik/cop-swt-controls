package cop.swt.widgets.segments;

import static cop.common.extensions.CommonExtension.isEqual;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.NumericExtension.isGreaterOrEqual;
import static cop.common.extensions.NumericExtension.isLessOrEqual;
import static cop.common.extensions.NumericExtension.toInvertedCharArray;

import org.eclipse.swt.widgets.Shell;

import cop.swt.widgets.segments.interfaces.ISegmentConfig;
import cop.swt.widgets.segments.seven.DigitalNumericSevenSegment;
import cop.swt.widgets.segments.tmp.SignPositionEnum;

public abstract class NumberSegmentContainer<T extends Number> extends SegmentContainer<T>
{
	protected T minimum;
	protected T maximum;

	public NumberSegmentContainer(Shell shell, int orientation, int totalSegments)
	{
		this(shell, orientation, totalSegments, false);
	}

	public NumberSegmentContainer(Shell shell, int orientation, int totalSegments, boolean signSegment)
	{
		super(shell, orientation, totalSegments, null);
	}

	public NumberSegmentContainer(Shell shell, ISegmentConfig config)
	{
		super(shell, config.getOrientation(), config.getTotalSegments(), config);
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

		segments = new SegmentedIndicator[totalSegments];

		if(signPosition == SignPositionEnum.OUTSIDE)
		{
			switch(config.getSignType())
			{
			case PLUS_MINUS:
			case PLUS:
			case MINUS:
			default:
				segments[i++] = new SignSegment(x, y, scale);
				break;
			}
		}

		// if(signSegment)
		// {
		// segments = new SegmentedIndicator[config.getTotalSegments()totalSegments + 1];
		// segments[i++] = new SignSegment(x, y, scale);
		// }
		// else
		// segments = new SegmentedIndicator[totalSegments];

		for(; i < totalSegments; i++)
			segments[i] = new DigitalNumericSevenSegment(x, y, scale);
	}

	@Override
	protected void createVerticalOrientatedParts(boolean invert)
	{
		segments = new DigitalNumericSevenSegment[totalSegments];

		for(int i = 0; i < totalSegments; i++)
			segments[i] = new DigitalNumericSevenSegment(x, y, scale);
	}

	@Override
	public void setValue(T value)
	{
		if(isEqual(value, this.value))
			return;

		if(isNull(value))
		{
			clear();
			return;
		}

		if(isGreaterOrEqual(value, maximum) || isLessOrEqual(value, minimum))
			return;

		super.setValue(value);

		if(config.getSignPosition() == SignPositionEnum.OUTSIDE)
		{
			if(value == null || value.intValue() == 0)
				segments[0].setValue(null);
			else if(value.intValue() > 0)
				segments[0].setValue('+');
			else
				segments[0].setValue('-');
		}

//		if(isInverted(isHorizontalOrientation()))
//			setInvertedValue();
//		else
			setDirectValue();
	}

	private void setInvertedValue()
	{
		int i = 0;
		boolean negative = false;

		for(char ch : toInvertedCharArray(value))
		{
			if(ch == '-')
				negative = true;
			else
				segments[i++].setValue(ch);
		}

		if(negative)
		{
			if(config.isLeadingZero())
			{
				for(int size = segments.length - 1; i <= size; i++)
					segments[i].setValue('0');

				if(config.getSignPosition() == SignPositionEnum.INSIDE)
					segments[i].setValue('-');
				else
					segments[i].setValue('0');
			}
			else
			{
				if(config.getSignPosition() == SignPositionEnum.INSIDE)
					segments[i--].setValue('-');
				else
					segments[i--].setValue(null);

				for(int size = segments.length; i < size; i++)
					segments[i].setValue(null);
			}
		}
		else
		{
			for(int size = segments.length - 1; i <= size; i++)
				segments[i].setValue((config.isLeadingZero() && (i != size)) ? '0' : null);
		}
	}

	private void setDirectValue()
	{
		int i = segments.length - 1;
		boolean negative = false;

		for(char ch : toInvertedCharArray(value))
		{
			if(ch == '-')
			{
				negative = true;
				break;
			}

			segments[i--].setValue(ch);
		}

		if(negative)
		{
			if(config.isLeadingZero())
			{
				for(; i >= 0; i--)
					segments[i].setValue('0');

				if(config.getSignPosition() == SignPositionEnum.INSIDE)
					segments[i].setValue('-');
				else
					segments[i].setValue('0');

			}
			else
			{
				if(config.getSignPosition() == SignPositionEnum.INSIDE)
					segments[i--].setValue('-');
				else
					segments[i--].setValue(null);

				for(; i >= 0; i--)
					segments[i].setValue(null);
			}
		}
		else
		{
			for(; i >= 0; i--)
				segments[i].setValue((config.isLeadingZero() && (i != 0)) ? '0' : null);
		}
	}
}
