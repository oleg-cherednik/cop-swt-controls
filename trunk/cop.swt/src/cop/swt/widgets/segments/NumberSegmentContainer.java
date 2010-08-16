package cop.swt.widgets.segments;

import static cop.common.extensions.CommonExtension.isEqual;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.NumericExtension.isGreater;
import static cop.common.extensions.NumericExtension.isLess;
import static cop.common.extensions.NumericExtension.toInvertedCharArray;

import org.eclipse.swt.widgets.Shell;

public abstract class NumberSegmentContainer<T extends Number> extends SegmentContainer<T>
{
	private boolean leadingZero = false;
	protected T minimum;
	protected T maximum;

	public NumberSegmentContainer(Shell shell, int orientation, int totalSegments)
	{
		super(shell, orientation, totalSegments);
	}

	/*
	 * AbstractSegmentIndicator
	 */

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

					for(; i >= 0; i--)
						segments[i].setValue(null);
				}
			}
			else
			{
				for(; i >= 0; i--)
					segments[i].setValue((leadingZero && (i != 0)) ? '0' : null);
			}
		}
	}

}
