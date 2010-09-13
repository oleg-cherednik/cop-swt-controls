package cop.swt.widgets.segments;

import static cop.common.extensions.CommonExtension.isEqual;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.NumericExtension.isGreater;
import static cop.common.extensions.NumericExtension.isLess;
import static cop.common.extensions.NumericExtension.toInvertedCharArray;

import org.eclipse.swt.widgets.Shell;

import cop.swt.widgets.segments.seven.DigitalNumericSevenSegment;

public abstract class NumberSegmentContainer<T extends Number> extends SegmentContainer<T>
{
	private boolean leadingZero = false;
	protected T minimum;
	protected T maximum;
	protected final boolean signSegment = true; // separate sign segment

	public NumberSegmentContainer(Shell shell, int orientation, int totalSegments)
	{
		this(shell, orientation, totalSegments, false);
	}
	
	public NumberSegmentContainer(Shell shell, int orientation, int totalSegments, boolean signSegment)
	{
		super(shell, orientation, totalSegments);
	}

	/*
	 * AbstractSegmentIndicator
	 */

	@Override
	protected void createHorizontalOrientatedParts(boolean invert)
	{
		int i = 0;

		if(signSegment)
		{
			segments = new SegmentedIndicator[totalSegments + 1];
			segments[i++] = new SignSegment(x, y, scale);
		}
		else
			segments = new SegmentedIndicator[totalSegments];

		for(; i <= totalSegments; i++)
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

		if(isGreater(value, maximum))
		{
			if(isEqual(this.value, maximum))
				return;

			value = maximum;
		}

		if(isLess(value, minimum))
		{
			if(isEqual(this.value, minimum))
				return;

			value = minimum;
		}

		super.setValue(value);

		boolean negative = false;
		
		segments[0].setValue('-');

		if(isInverted(isHorizontalOrientation()))
		{
			int i = 0;

			for(char ch : toInvertedCharArray(value))
			{
				if(ch == '-')
				{
					negative = true;
					break;
				}

				segments[i++].setValue(ch);
			}

			if(negative)
			{
				if(leadingZero)
				{
					for(int size = segments.length - 1; i < size; i++)
						segments[i].setValue('0');

					segments[i].setValue('-');
				}
				else
				{
					segments[i++].setValue('-');

					for(int size = segments.length; i < size; i++)
						segments[i].setValue(null);
				}
			}
			else
			{
				for(int size = segments.length - 1; i <= size; i++)
					segments[i].setValue((leadingZero && (i != size)) ? '0' : null);
			}
		}
		else
		{
			int i = segments.length - 1;

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
				if(leadingZero)
				{
					for(; i > 0; i--)
						segments[i].setValue('0');

					segments[i].setValue('-');
				}
				else
				{
					segments[i--].setValue('-');

					for(; i > 0; i--)
						segments[i].setValue(null);
				}
			}
			else
			{
				for(; i > 0; i--)
					segments[i].setValue((leadingZero && (i != 0)) ? '0' : null);
			}
		}
	}

}
