/**
 * $Id$ $HeadURL:
 * https://swtdatetimecontrol.googlecode.com/svn/trunk
 * /com.marathon.datetimecontrol/src/com/marathon/controls/EnumText.java $
 */
package cop.swt.widgets;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.numeric.arithmetic.IArithmeticStrategy;

//public class EnumText extends AbstractNumericViewer<Integer>
//{
//	private final List<String> values = new ArrayList<String>();
//	private int currentItem = -1;
//
//	public EnumText(Composite parent, int style)
//	{
//		this(parent, style, null, null);
//	}
//
//	// public EnumText(Composite parent, int style, Object[] values)
//	// {
//	// this(parent, style, values, -1);
//	// }
//
//	public EnumText(Composite parent, int style, List<String> values, Integer maxLen)
//	{
//		super(parent, style | SWT.READ_ONLY, null);
//
//		addValues(values, maxLen);
//		addListeners();
//		// setTextLimit();
//		// setBackground(getColor(COLOR_WHITE));
//		// setColorRuleCheckType(START_CHECK_FROM_FIRST_FOCUS_GAINED);
//
//		// super.setText(this.values[currentItem]);
//	}
//
////	private int addValues(List<String> values, int limit)
////	{
////		int maxStringSize = 0;
////
////		for(int i = 0; i < values.length; i++)
////		{
////			String val = values[i].toString();
////			String valLimit = (limit > 0 && val.length() > limit) ? val.substring(0, limit) : val;
////			int valLimitLength = valLimit.length();
////
////			this.values[i] = valLimit;
////
////			if(maxStringSize >= valLimitLength)
////				continue;
////
////			maxStringSize = valLimitLength;
////			currentItem = i;
////		}
////
////		return maxStringSize;
////	}
////
////	@Override
////	protected int getValidTextLimit()
////	{
////		int textLimit = 0;
////
////		for(String str : values)
////		{
////			if(isEmpty(str))
////				continue;
////			else
////				textLimit = max(textLimit, str.length());
////		}
////
////		return textLimit;
////	}
////
////	public void setText(Object value)
////	{
////		if(value != null)
////			setText("" + value);
////	}
////
////	private void addListeners()
////	{
////	// addKeyListener(getSetNextOnSpaseListener());
////	// addMouseMoveListener(getSetDefaultSelectionOnMouseMoveListener());
////	}
////
////	public void addBreak(String value)
////	{
////		addBreak(binarySearch(values, value));
////		refresh();
////	}
////
////	public void removeBreak(String value)
////	{
////		removeBreak(binarySearch(values, value));
////	}
////
////	public Set<String> getBreakListStr()
////	{
////		Set<String> res = new LinkedHashSet<String>();
////
////		for(String val : values)
////			res.add(val);
////
////		return res;
////	}
////
////	/**
////	 * AbstractNumericText
////	 */
////
////	protected void setDefaultSelection()
////	{
////		setSelection(0);
////	}
////
////	/**
////	 * INumeric
////	 */
////
////	@Override
////	public Integer getValue()
////	{
////		return currentItem;
////	}
////
////	@Override
////	public void setValue(Integer value)
////	{
////		if(currentItem == value || !isInRange(value, 0, values.length))
////			return;
////
////		currentItem = value;
////		super.setText(values[value]);
////	}
////
////	/**
////	 * Text
////	 */
////
////	@Override
////	@Deprecated
////	public void setTextLimit(int limit)
////	{}
////
////	// @Override
////	// public String getText()
////	// {
////	// return values[currentItem];
////	// }
////
////	@Override
////	public void setText(String text)
////	{
////		if(isEmpty(text))
////			return;
////
////		for(int i = 0; i < values.length; i++)
////		{
////			if(!values[i].equals(text))
////				continue;
////
////			currentItem = i;
////			super.setText(values[i]);
////			break;
////		}
////	}
////
////	/**
////	 * Listeners
////	 */
////
////	private MouseMoveListener getSetDefaultSelectionOnMouseMoveListener()
////	{
////		return new MouseMoveListener()
////		{
////			@Override
////			public void mouseMove(MouseEvent e)
////			{
////				setDefaultSelection();
////			}
////		};
////	}
////
////	private KeyListener getSetNextOnSpaseListener()
////	{
////		return new KeyListener()
////		{
////			@Override
////			public void keyReleased(KeyEvent e)
////			{}
////
////			@Override
////			public void keyPressed(KeyEvent e)
////			{
////				if(parseKeyEnum(e.keyCode) == KEY_SPACE)
////					setNext();
////			}
////		};
////	}
////
////	// @Override
////	// protected void makeInRange()
////	// {
////	// }
////
////	@Override
////	protected void setValueInRange(int value)
////	{
////	}
////
////	@Override
////	protected String getCorrectValue(int value)
////	{
////		return null;
////	}
////
////	@Override
////	protected void setDirectValue(int value)
////	{
////	}
////
////	@Override
////	protected IArithmeticStrategy<Integer> initArithmeticStrategy()
////	{
////		return null;
////	}
////
////	@Override
////	protected Integer initIncrement()
////	{
////		return null;
////	}
////
////	@Override
////	protected Integer wrapNext(Integer value)
////	{
////		return null;
////	}
////
////	@Override
////	protected Integer wrapPrevious(Integer value)
////	{
////		return null;
////	}
//}
