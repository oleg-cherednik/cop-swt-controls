/**
 * $Id: DoubleNumeric.java 51 2010-08-16 12:25:56Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.swt/cop.swt/src/cop/swt/widgets/numeric/DoubleNumeric.java $
 */
package cop.swt.widgets.numeric;

import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.NumericExtension.countDigits;
import static cop.common.extensions.NumericExtension.isGreaterOrEqual;
import static cop.common.extensions.NumericExtension.isInRangeMinMax;
import static cop.common.extensions.NumericExtension.isLess;
import static cop.common.extensions.NumericExtension.isLessOrEqual;
import static cop.common.extensions.StringExtension.replace;
import static cop.swt.widgets.keys.listeners.KeyListenerSet.isDecimalSeparator;
import static java.lang.Math.abs;
import static java.text.NumberFormat.getInstance;
import static org.eclipse.swt.SWT.NONE;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import cop.common.extensions.StringExtension;
import cop.swt.widgets.keys.enums.KeyEnum;
import cop.swt.widgets.numeric.arithmetic.DoubleArithmeticStrategy;
import cop.swt.widgets.numeric.arithmetic.IArithmeticStrategy;

/**
 * @author cop (Cherednik, Oleg)
 */
public class DoubleNumeric extends AbstractNumeric<Double>
{
	public DoubleNumeric(Composite parent) throws IllegalArgumentException
	{
		this(parent, NONE, Locale.getDefault());
	}

	public DoubleNumeric(Composite parent, int style) throws IllegalArgumentException
	{
		this(parent, style, Locale.getDefault());
	}

	public DoubleNumeric(Composite parent, Locale locale) throws IllegalArgumentException
	{
		this(parent, NONE, locale);
	}

	public DoubleNumeric(Composite parent, DecimalFormat df) throws IllegalArgumentException
	{
		this(parent, NONE, df);
	}

	public DoubleNumeric(Composite parent, int style, Locale locale) throws IllegalArgumentException
	{
		this(parent, style, (DecimalFormat)getInstance(locale));
	}

	public DoubleNumeric(Composite parent, int style, DecimalFormat df) throws IllegalArgumentException
	{
		super(parent, style, df);

		addListeners(df);
	}

	private void addListeners(DecimalFormat df)
	{
		addKeyListener(isDecimalSeparator(df));
		addKeyListener(changeGrayDotToDecimal(df));
		addVerifyListener(isValidDouble);
	}

	/*
	 * AbstractNumeric
	 */

	@Override
	protected Double initIncrement()
	{
		return 0.1;
	}

	@Override
	protected IArithmeticStrategy<Double> initArithmeticStrategy()
	{
		return new DoubleArithmeticStrategy();
	}

	@Override
	protected Double wrapNext(Double value)
	{
		return getMinimum() - getInc() + abs(value - getMaximum());
	}

	@Override
	protected Double wrapPrevious(Double value)
	{
		return getMaximum() + getInc() - abs(value - getMinimum());
	}

	/*
	 * Listeners
	 */

	protected static KeyListener changeGrayDotToDecimal()
	{
		return changeGrayDotToDecimal(Locale.getDefault());
	}

	protected static KeyListener changeGrayDotToDecimal(Locale locale)
	{
		return changeGrayDotToDecimal((DecimalFormat)getInstance(locale));
	}

	protected static KeyListener changeGrayDotToDecimal(DecimalFormat df)
	{
		final char decimalSeparator = df.getDecimalFormatSymbols().getDecimalSeparator();

		KeyListener changeGrayDotToDecimal = new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				KeyEnum key = KeyEnum.parseKeyEnum(e.keyCode);

				if(key != KeyEnum.KEY_GREY_DOT)
					return;

				e.doit = false;

				DoubleNumeric obj = (DoubleNumeric)e.widget;

				String text = obj.getText();
				Point sel = obj.getSelection();
				String str = replace(text, "" + decimalSeparator, sel.x, sel.y);

				obj.setSuperText(str);
				obj.setSelection(sel.x + 1);
			}
		};

		return changeGrayDotToDecimal;
	}

	private VerifyListener isValidDouble = new VerifyListener()
	{
		@Override
		public void verifyText(VerifyEvent e)
		{
			try
			{
				DecimalFormat df = (DecimalFormat)nf;
				DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();

				char decimalSeparator = symbols.getDecimalSeparator();
				char minusSign = symbols.getMinusSign();

				String text = ((Text)e.widget).getText();
				String str = replace(text, e.text, e.start, e.end);
				Double minimum = getMinimum();

				if(StringExtension.isEmpty(str))
					e.doit = true;
				else if(str.equals("" + decimalSeparator))
					e.doit = true;
				else if(str.equals("" + decimalSeparator))
					e.doit = true;
				else if(str.equals("" + minusSign))
					e.doit = isNull(minimum) || isLess(minimum, 0);
				else if(str.equals("" + minusSign + decimalSeparator))
					e.doit = true;
				else
					e.doit = basicCheck(str, df);
			}
			catch(Exception ex)
			{
				e.doit = false;
				ex.printStackTrace();
			}
		}

		private boolean basicCheck(String str, DecimalFormat df) throws ParseException
		{
			DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();
			char decimalSeparator = symbols.getDecimalSeparator();
			char minusSign = symbols.getMinusSign();

			if(!checkDotNumber(str, decimalSeparator))
				return false;

			double value = df.parse(str).doubleValue();

			if(!checkMaximumFractionDigits(str, decimalSeparator, df.getMaximumFractionDigits()))
				return false;

			String _str = removeGrouping(value, df);

			int strLen = str.length();
			int dotPos = _str.lastIndexOf(decimalSeparator);

			if(!checkMaximumIntegerDigits(strLen, dotPos, df))
				return false;

			if(_str.charAt(0) == minusSign)
				return negativeValue(_str, value);

			return positiveValue(_str, value);
		}

		private String removeGrouping(double value, DecimalFormat df)
		{
			DecimalFormat ddf = (DecimalFormat)df.clone();

			ddf.setGroupingUsed(false);

			return ddf.format(value);
		}

		private boolean checkDotNumber(String str, char decimalSeparator)
		{
			return str.indexOf(decimalSeparator) == str.lastIndexOf(decimalSeparator);
		}

		private boolean checkMaximumFractionDigits(String str, char decimalSeparator, int max)
		{
			int dotPos = str.lastIndexOf(decimalSeparator);

			if(dotPos < 0)
				return true;

			return str.length() - dotPos - 1 <= max;
		}

		private boolean checkMaximumIntegerDigits(int strLen, int dotPos, DecimalFormat df)
		{
			int _dotPos = (dotPos < 0) ? strLen : dotPos;

			return _dotPos - 1 <= df.getMaximumIntegerDigits();
		}

		private boolean negativeValue(String str, double value)
		{
			Double minimum = getMinimum();
			int val = (int)value;

			if(isNull(minimum))
				return true;

			if(isGreaterOrEqual(minimum, 0))
				return false;

			if(countDigits(val) >= countDigits(minimum.intValue()) && !isInRangeMinMax(value, minimum, getMaximum()))
				return false;

			if(val == minimum.intValue() && str.charAt(str.length() - 1) == '.')
				return false;

			if(isBreakValue(value))
				return false;

			return true;
		}

		private boolean positiveValue(String str, double value)
		{
			Double maximum = getMaximum();
			int val = (int)value;

			if(isNull(maximum))
				return true;

			if(isLessOrEqual(maximum, 0))
				return false;

			if(countDigits(val) >= countDigits(maximum.intValue()) && !isInRangeMinMax(value, getMinimum(), maximum))
				return false;

			if(val == maximum.intValue() && str.charAt(str.length() - 1) == '.')
				return false;

			if(isBreakValue(value))
				return false;

			return true;
		}
	};
}
