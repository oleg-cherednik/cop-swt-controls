/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.numeric;

import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.NumericExtension.countLength;
import static cop.common.extensions.NumericExtension.isInRangeMinMax;
import static cop.common.extensions.NumericExtension.isLess;
import static cop.common.extensions.StringExtension.isEmpty;
import static cop.common.extensions.StringExtension.replace;
import static java.lang.Math.abs;
import static java.text.NumberFormat.getIntegerInstance;
import static org.eclipse.swt.SWT.NONE;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import cop.swt.widgets.numeric.arithmetic.IArithmeticStrategy;
import cop.swt.widgets.numeric.arithmetic.IntegerArithmeticStrategy;

/**
 * @author cop (Cherednik, Oleg)
 */
public class IntegerNumeric extends AbstractNumeric<Integer>
{
	public IntegerNumeric(Composite parent) throws IllegalArgumentException
	{
		this(parent, NONE, Locale.getDefault());
	}

	public IntegerNumeric(Composite parent, int style) throws IllegalArgumentException
	{
		this(parent, style, Locale.getDefault());
	}

	public IntegerNumeric(Composite parent, Locale locale) throws IllegalArgumentException
	{
		this(parent, NONE, locale);
	}

	public IntegerNumeric(Composite parent, DecimalFormat df) throws IllegalArgumentException
	{
		this(parent, NONE, df);
	}

	public IntegerNumeric(Composite parent, int style, Locale locale) throws IllegalArgumentException
	{
		this(parent, style, (DecimalFormat)getIntegerInstance(locale));
	}

	public IntegerNumeric(Composite parent, int style, DecimalFormat df) throws IllegalArgumentException
	{
		super(parent, style, df);

		addListeners();
	}

	private void addListeners()
	{
		addVerifyListener(isValidInteger);
	}

	/*
	 * AbstractNumeric
	 */

	@Override
	protected Integer initIncrement()
	{
		return 1;
	}

	@Override
	protected IArithmeticStrategy<Integer> initArithmeticStrategy()
	{
		return new IntegerArithmeticStrategy();
	}

	@Override
	protected Integer wrapNext(Integer value)
	{
		return getMinimum() - getInc() + abs(value - getMaximum());
	}

	@Override
	protected Integer wrapPrevious(Integer value)
	{
		return getMaximum() + getInc() - abs(value - getMinimum());
	}

	/*
	 * Listeners
	 */

	// TODO refactoring see NumberExtension.checkValue
	private VerifyListener isValidInteger = new VerifyListener()
	{
		@Override
		public void verifyText(VerifyEvent e)
		{
			try
			{
				DecimalFormat df = (DecimalFormat)nf;
				DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();

				char minusSign = symbols.getMinusSign();

				String text = ((Text)e.widget).getText();
				String str = replace(text, e.text, e.start, e.end);
				Integer minimum = getMinimum();

				if(isEmpty(str))
					e.doit = true;
				else if(str.equals("" + minusSign))
					e.doit = isNull(minimum) || isLess(minimum, 0);
				else
					e.doit = basicCheck(str, (DecimalFormat)nf);
			}
			catch(Exception ex)
			{
				e.doit = false;
			}
		}

		private boolean basicCheck(String str, DecimalFormat df) throws ParseException
		{
			Integer value = df.parse(str).intValue();

			if(isBreakValue(value))
				return false;

			if(str.charAt(0) == '-')
				return negativeValue(str, value);

			return positiveValue(str, value);
		}

		private boolean negativeValue(String str, Integer value)
		{
			Integer minimum = getMinimum();

			if(isNull(minimum))
				return true;

			if(str.length() >= countLength(minimum) && !isInRangeMinMax(value, minimum, getMaximum()))
				return false;

			return true;
		}

		private boolean positiveValue(String str, Integer value)
		{
			Integer maximum = getMaximum();

			if(isNull(maximum))
				return true;

			if(str.length() >= countLength(maximum) && !isInRangeMinMax(value, getMinimum(), maximum))
				return false;

			return true;
		}
	};
}
