/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.numeric;

import static cop.extensions.CommonExt.isEqual;
import static cop.extensions.CommonExt.isNotNull;
import static cop.extensions.CommonExt.isNull;
import static cop.extensions.NumericExt.countDigits;
import static cop.extensions.NumericExt.getRange;
import static cop.extensions.NumericExt.isGreater;
import static cop.extensions.NumericExt.isGreaterOrEqual;
import static cop.extensions.NumericExt.isInRange;
import static cop.extensions.NumericExt.isInRangeMinMax;
import static cop.extensions.NumericExt.isLess;
import static cop.extensions.NumericExt.isLessOrEqual;
import static cop.extensions.StringExt.isNotEmpty;
import static cop.swt.widgets.keys.enums.KeyEnum.parseKeyEnum;
import static cop.swt.widgets.keys.listeners.KeyListenerSet.doClearKey;
import static cop.swt.widgets.keys.listeners.KeyListenerSet.isArrow;
import static cop.swt.widgets.keys.listeners.KeyListenerSet.isBackspase;
import static cop.swt.widgets.keys.listeners.KeyListenerSet.isDelete;
import static cop.swt.widgets.keys.listeners.KeyListenerSet.isDigit;
import static cop.swt.widgets.keys.listeners.KeyListenerSet.isNegative;
import static cop.swt.widgets.listeners.TraverseListenerSet.allowTabKey;
import static cop.swt.widgets.numeric.enums.BoundTypeEnum.BOUND_BOTH;
import static cop.swt.widgets.numeric.enums.BoundTypeEnum.BOUND_MAXIMUM;
import static cop.swt.widgets.numeric.enums.BoundTypeEnum.BOUND_MINIMUM;
import static cop.swt.widgets.numeric.enums.BoundTypeEnum.BOUND_NONE;
import static java.lang.Math.max;
import static org.eclipse.swt.SWT.MouseWheel;
import static org.eclipse.swt.SWT.NONE;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cop.extensions.CollectionExt;
import cop.extensions.StringExt;
import cop.swt.widgets.ColorText;
import cop.swt.widgets.dirty.DirtyObserver;
import cop.swt.widgets.dirty.NumericDirtyObserver;
import cop.swt.widgets.numeric.arithmetic.IArithmeticStrategy;
import cop.swt.widgets.numeric.enums.BoundTypeEnum;
import cop.swt.widgets.numeric.interfaces.IBoundNumeric;
import cop.swt.widgets.numeric.interfaces.IBreakNumeric;
import cop.swt.widgets.numeric.interfaces.IIterableNumeric;

/**
 * @author cop (Cherednik, Oleg)
 */
public abstract class AbstractNumeric<T extends Number> extends ColorText implements IIterableNumeric<T>,
                IBreakNumeric<T>, IBoundNumeric<T>
{
	private T inc;
	private T minimum;
	private T maximum;
	private BoundTypeEnum boundType;
	private boolean wrap;
	private Set<T> breakList = new TreeSet<T>();
	private boolean nullValueEnabled;
	private IArithmeticStrategy<T> arithmeticStrategy;
	public final NumberFormat nf;

	protected AbstractNumeric(Composite parent, NumberFormat nf)
	{
		this(parent, NONE, nf);
	}

	protected AbstractNumeric(Composite parent, int style, NumberFormat nf)
	{
		super(parent, style);

		this.arithmeticStrategy = initArithmeticStrategy();
		this.inc = initIncrement();
		this.nf = nf;

		setDefaultValue();
		addListeners();
	}

	public final boolean isNullValueEnabled()
	{
		return nullValueEnabled;
	}

	public final void setNullValueEnabled(boolean nullValueEnabled)
	{
		this.nullValueEnabled = nullValueEnabled;
	}

	protected final IArithmeticStrategy<T> getArithmeticStrategy()
	{
		return arithmeticStrategy;
	}

	private void setDefaultValue()
	{
		if(isNull(minimum))
		{
			if(isNull(maximum) || isGreaterOrEqual(maximum, 0))
				setValue(arithmeticStrategy.getValue(0));
			else
				setValue(maximum);
		}
		else
		{
			if(isNull(maximum))
			{
				if(isLessOrEqual(minimum, 0))
					setValue(arithmeticStrategy.getValue(0));
				else
					setValue(minimum);
			}
			else
				setValue(getMiddle(minimum, maximum));
		}
	}

	protected void setValueInRange(T value)
	{
		boolean isValid = isBreakValue(value);

		if(isValid)
			setValue(value);
		if(isLess(value, minimum))
			setValue(minimum);
		else if(isGreater(value, maximum))
			setValue(maximum);

		assert (false);
	}

	protected void setDirectValue(T value)
	{
		if(isNotNull(value))
			super.setText(nf.format(value));
	}

	protected boolean isBreakValue(T value)
	{
		return (isNull(value) || CollectionExt.isEmpty(breakList)) ? false : !breakList.contains(value);
	}

	protected void refreshGridLayout()
	{
		try
		{
			GridData layoutData = (GridData)super.getLayoutData();
			double textLimit = getValidTextLimit();
			int fontSize = getFontSize();

			if(isLess(minimum, 0))
				textLimit -= 0.5;

			layoutData.widthHint = (int)(textLimit * fontSize);

			super.pack(true);
		}
		catch(Exception e)
		{}
	}

	protected int getFontSize()
	{
		return getFont().getFontData()[0].getHeight();
	}

	protected int getValidTextLimit() throws IllegalStateException
	{
		switch(boundType)
		{
		case BOUND_NONE:
			return -1;
		case BOUND_MINIMUM:
			return countDigits(minimum);
		case BOUND_MAXIMUM:
			return countDigits(maximum);
		case BOUND_BOTH:
		{
			int minDigit = countDigits(minimum);
			int maxDigit = countDigits(maximum);

			if(isLess(minimum, 0) && isGreaterOrEqual(maximum, 0))
				minDigit++;

			return max(minDigit, maxDigit);
		}
		default:
			throw new IllegalStateException("Unknown boundType");
		}
	}

	private void addListeners()
	{
		addFocusListener(selectOnFocusGained);
		addTraverseListener(allowTabKey);
		addListener(MouseWheel, changeValueOnMouseWheel);
		addKeyListener();
	}

	private void addKeyListener()
	{
		addKeyListener(doClearKey);
		addKeyListener(isNegative);
		addKeyListener(isArrow);
		addKeyListener(isDigit);
		addKeyListener(isDelete);
		addKeyListener(isBackspase);
		addKeyListener(changValueOnArrow);
	}

	private void refreshBoundType()
	{
		if(isNull(minimum))
			boundType = isNull(maximum) ? BOUND_NONE : BOUND_MAXIMUM;
		else
			boundType = isNull(maximum) ? BOUND_MINIMUM : BOUND_BOTH;
	}

	private void refreshBreakList()
	{
		if(CollectionExt.isEmpty(breakList))
			return;

		Set<T> values = new TreeSet<T>();

		for(T value : breakList)
			if(!isInRange(value, minimum, maximum))
				values.add(value);

		breakList.removeAll(values);
	}

	protected int getDigitNum()
	{
		int textLimit = getValidTextLimit();

		return (isLess(minimum, 0)) ? textLimit - 1 : textLimit;
	}

	public T getMiddle(T minimum, T maximum)
	{
		T zero = arithmeticStrategy.getValue(0);

		if(isNull(minimum))
		{
			if(isNull(maximum) || isGreaterOrEqual(maximum, 0))
				return zero;

			return maximum;
		}

		if(isNull(maximum))
			return isLessOrEqual(minimum, 0) ? zero : minimum;

		double min = minimum.doubleValue();
		double max = maximum.doubleValue();

		if(isGreaterOrEqual(minimum, 0) && isGreaterOrEqual(maximum, 0))
			return arithmeticStrategy.getValue(min + (max - min) / 2);
		if(isLess(minimum, 0) && isLess(maximum, 0))
			return arithmeticStrategy.getValue(max - (max - min) / 2);

		return zero;
	}

	protected final String getText(String defaultText)
	{
		String str = super.getText();

		return StringExt.isEmpty(str) ? defaultText : str;
	}

	protected final void setSuperText(String text)
	{
		super.setText(text);
	}

	protected abstract T wrapNext(T value);

	protected abstract T wrapPrevious(T value);

	protected abstract T initIncrement();

	protected abstract IArithmeticStrategy<T> initArithmeticStrategy();

	/*
	 * INumeric
	 */

	@Override
	public T getValue()
	{
		try
		{
			String str = getText();

			if(StringExt.isEmpty(str) && nullValueEnabled)
				return null;

			return arithmeticStrategy.getValue(nf.parse(str).doubleValue());
		}
		catch(ParseException e)
		{
			return arithmeticStrategy.getValue(.0);
		}
	}

	/**
	 * Method set new value.
	 * 
	 * @param value
	 *            new value
	 */

	@Override
	public void setValue(T value)
	{
		if(isNull(value))
		{
			if(nullValueEnabled)
				super.setText("");

			return;
		}

		if(!isInRangeMinMax(value, minimum, maximum))
			return;

		if(isBreakValue(value))
			setNext();
		else
		{
			String str = nf.format(value);

			if(isNotEmpty(str))
				super.setText(str);
		}
	}

	/*
	 * ColorText
	 */

	@Override
	protected DirtyObserver getDirtyObserver()
	{
		return new NumericDirtyObserver<T>(this);
	}

	@Override
	protected void refresh()
	{
		T value = getValue();

		// if(!isInRangeMinMax(value, minimum, maximum))
		// setDefaultValue();

		setValue(value);
	}

	/*
	 * IBoundNumeric
	 */

	@Override
	public boolean isWrap()
	{
		return wrap;
	}

	@Override
	public void setWrap(boolean wrap)
	{
		this.wrap = wrap;
	}

	@Override
	public final BoundTypeEnum getBoundType()
	{
		return boundType;
	}

	@Override
	public final T getMinimum()
	{
		return minimum;
	}

	@Override
	public final T getMaximum()
	{
		return maximum;
	}

	@Override
	public final void setMinimum(T minimum) throws IllegalArgumentException
	{
		setBounds(minimum, this.maximum);
	}

	@Override
	public final void setMaximum(T maximum) throws IllegalArgumentException
	{
		setBounds(this.minimum, maximum);
	}

	@Override
	public final void setBounds(T minimum, T maximum) throws IllegalArgumentException
	{
		if(isGreater(minimum, maximum))
			throw new IllegalArgumentException("Can't set bounds (minimum > maximum)");

		this.minimum = minimum;
		this.maximum = maximum;

		refreshBoundType();
		refreshBreakList();

		refresh();
	}

	/*
	 * IIterableNumeric
	 */

	@Override
	public final T getInc()
	{
		return inc;
	}

	@Override
	public final void setInc(T inc) throws IllegalArgumentException, NullPointerException
	{
		if(isNull(inc))
			throw new NullPointerException("inc == null");
		if((getBoundType() == BOUND_BOTH && inc.doubleValue() > getRange(minimum, maximum)))
			throw new IllegalArgumentException("inc is out of range");

		this.inc = inc;
	}

	@Override
	public void setNext()
	{
		T current = getValue();

		do
		{
			if(isEqual(current, maximum) && (!wrap || isNull(minimum)))
				return;

			current = arithmeticStrategy.add(current, inc);

			if(isNull(maximum) || isLessOrEqual(current, maximum))
				continue;

			if(!wrap || isNull(minimum))
				current = maximum;
			else
				current = wrapNext(current);
		}
		while(breakList.contains(current));

		setValue(current);
	}

	@Override
	public void setPrevious()
	{
		T current = getValue();

		do
		{
			if(isEqual(current, minimum) && (!wrap || isNull(maximum)))
				return;

			current = arithmeticStrategy.sub(current, inc);

			if(isNull(minimum) || isGreaterOrEqual(current, minimum))
				continue;

			if(!wrap || isNull(maximum))
				current = minimum;
			else
				current = wrapPrevious(current);
		}
		while(breakList.contains(current));

		setValue(current);
	}

	/*
	 * BreakNumeric
	 */

	@Override
	public final void addBreak(T value) throws NullPointerException
	{
		if(isNull(value))
			throw new NullPointerException("Can't add 'null' as new break value");
		if(getBoundType() == BOUND_BOTH && breakList.size() + 1 >= getRange(minimum, maximum))
			return;

		breakList.add(value);
		refresh();
	}

	@Override
	public final void removeBreak(T value) throws NullPointerException
	{
		if(isNull(value))
			throw new NullPointerException("Can't remove break value 'null'");

		breakList.remove(value);
	}

	@Override
	public final Set<T> getBreakList()
	{
		return isNull(breakList) ? new TreeSet<T>() : new TreeSet<T>(breakList);
	}

	@Override
	public void clearBreakList()
	{
		breakList.clear();
	}

	/*
	 * Control
	 */

	@Override
	public void setFont(Font font)
	{
		super.setFont(font);
		refreshGridLayout();
	}

	@Override
	public void setLayoutData(Object layoutData)
	{
		super.setLayoutData(layoutData);
		refreshGridLayout();
	}

	/*
	 * Text
	 */

	@Override
	@Deprecated
	public final void setTextLimit(int limit)
	{}

	@Override
	public final String getText()
	{
		return super.getText();
	}

	@Override
	public void setText(String text)
	{
		try
		{
			T value = arithmeticStrategy.parse(text);

			if(!isBreakValue(value))
				setValue(value);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Listeners
	 */

	private FocusListener selectOnFocusGained = new FocusListener()
	{
		@Override
		public void focusGained(FocusEvent e)
		{
			clearSelection();
			T val = getValue();
			setValueInRange(val);
		}

		@Override
		public void focusLost(FocusEvent e)
		{
			selectAll();
		}
	};

	private KeyListener changValueOnArrow = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch(parseKeyEnum(e.keyCode))
			{
			case KEY_UP:
				setNext();
				break;
			case KEY_DOWN:
				setPrevious();
				break;
			default:
				break;
			}
		}
	};

	private Listener changeValueOnMouseWheel = new Listener()
	{
		@Override
		public void handleEvent(Event event)
		{
			if(event.type != MouseWheel || event.count == 0)
				return;

			if(event.count > 0)
				setNext();
			else
				setPrevious();
		}
	};
}
