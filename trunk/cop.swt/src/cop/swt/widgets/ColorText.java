/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets;

import static cop.common.extensions.CommonExtension.getValue;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.swt.widgets.enums.ColorRuleCheckTypeEnum.START_CHECK_FROM_FIRST_KEY_PRESSED;
import static cop.swt.widgets.enums.ColorRuleCheckTypeEnum.START_CHECK_FROM_INIT;
import static org.eclipse.swt.SWT.NONE;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import cop.swt.widgets.dirty.DirtyObserver;
import cop.swt.widgets.dirty.TextDirtyObserver;
import cop.swt.widgets.dirty.interfaces.IDirtyListener;
import cop.swt.widgets.dirty.interfaces.IDirtyObserver;
import cop.swt.widgets.interfaces.IColorChangeable;

import cop.swt.widgets.enums.ColorRuleCheckTypeEnum;

/**
 * @author cop (Cherednik, Oleg)
 */
public class ColorText extends Text implements IDirtyObserver
{
	private DirtyObserver dirtyObserver;
	private ColorRuleCheckTypeEnum colorRuleCheckType = START_CHECK_FROM_FIRST_KEY_PRESSED;
	private IColorChangeable colorRule;
	private Color defaultBackgroundColor;
	private Color defaultForegroundColor;
	private boolean allowColorRuleCheck;

	public ColorText(Composite parent)
	{
		this(parent, NONE, null, null);
	}

	public ColorText(Composite parent, int style)
	{
		this(parent, style, null, null);
	}

	public ColorText(Composite parent, int style, Color defaultBackgroundColor, Color defaultForegroundColor)
	{
		super(parent, style);

		this.defaultBackgroundColor = defaultBackgroundColor;
		this.defaultForegroundColor = defaultForegroundColor;

		initColorRuleCheckType();
		addListeners();
	}

	private final void createDirtyObserver()
	{
		if(isNotNull(dirtyObserver))
			return;

		dirtyObserver = getDirtyObserver();
		setDirty(false);
	}

	protected DirtyObserver getDirtyObserver()
	{
		return new TextDirtyObserver(this);
	}

	public final ColorRuleCheckTypeEnum getColorRuleCheckType()
	{
		return colorRuleCheckType;
	}

	public final void setColorRuleCheckType(ColorRuleCheckTypeEnum colorRuleCheckType)
	{
		this.colorRuleCheckType = colorRuleCheckType;
		initColorRuleCheckType();
	}

	private void initColorRuleCheckType()
	{
		if(!allowColorRuleCheck && colorRuleCheckType == START_CHECK_FROM_INIT)
			allowColorRuleCheck = true;

		checkColorRule();
	}

	private void addListeners()
	{
		addFocusListener(refreshOnFocusLost);
		addFocusListener(checkColorOnFocusLost);
		addModifyListener(setColorOnModify);
		addKeyListener(turnOnColorCheckFromFirstKeyPressed);
	}

	protected void setText(String string, boolean keepSelection)
	{
		boolean allSelect = keepSelection && super.getSelectionText().equals(super.getText());

		setText(string);

		if(allSelect)
			selectAll();
	}

	public final IColorChangeable getColorRule()
	{
		return colorRule;
	}

	public final void setColorRule(IColorChangeable colorRule)
	{
		this.colorRule = colorRule;
	}

	protected void refresh()
	{
		setText(getText(), true);
	}

	private void setSuperColor(Color backgroundColor, Color foregroundColor)
	{
		super.setBackground(backgroundColor);
		super.setForeground(foregroundColor);

		update();
	}

	/*
	 * Text
	 */

	@Override
	public void setText(String string)
	{
		super.setText(string);
		checkColorRule();
	}

	/*
	 * Control
	 */

	@Override
	public void setBackground(Color color)
	{
		super.setBackground(color);
		defaultBackgroundColor = color;
		refresh();
	}

	@Override
	public void setForeground(Color color)
	{
		super.setForeground(color);
		defaultForegroundColor = color;
		refresh();
	}

	private void checkColorRule()
	{
		if(isNull(colorRule) || !allowColorRuleCheck)
			return;

		String str = getText().trim();
		Color backgroundColor = getValue(colorRule.getBackgroundColor(str), defaultBackgroundColor);
		Color foregroundColor = getValue(colorRule.getForegroundColor(str), defaultForegroundColor);

		setSuperColor(backgroundColor, foregroundColor);
	}

	/*
	 * Widget
	 */

	@Override
	protected void checkSubclass()
	{}

	/*
	 * IDirtyObserver
	 */

	@Override
	public void addDirtyListener(IDirtyListener listener)
	{
		createDirtyObserver();

		if(isNotNull(dirtyObserver))
			dirtyObserver.addDirtyListener(listener);
	}

	@Override
	public void removeDirtyListener(IDirtyListener listener)
	{
		if(isNotNull(dirtyObserver))
			dirtyObserver.removeDirtyListener(listener);
	}

	@Override
	public void removeAllDirtyListener()
	{
		if(isNotNull(dirtyObserver))
			dirtyObserver.removeAllDirtyListener();
	}

	/*
	 * IDirty
	 */

	@Override
	public boolean isDirty()
	{
		return isNotNull(dirtyObserver) ? dirtyObserver.isDirty() : false;
	}

	@Override
	public void setDirty(boolean dirty)
	{
		if(isNotNull(dirtyObserver))
			dirtyObserver.setDirty(dirty);
	}

	/*
	 * Listeners
	 */

	private KeyListener turnOnColorCheckFromFirstKeyPressed = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if(!allowColorRuleCheck && colorRuleCheckType == START_CHECK_FROM_FIRST_KEY_PRESSED)
				allowColorRuleCheck = true;
		}
	};

	private FocusListener refreshOnFocusLost = new FocusAdapter()
	{
		@Override
		public void focusLost(FocusEvent e)
		{
			refresh();
		}
	};

	private ModifyListener setColorOnModify = new ModifyListener()
	{
		@Override
		public void modifyText(ModifyEvent e)
		{
			checkColorRule();
		}
	};

	private FocusListener checkColorOnFocusLost = new FocusListener()
	{
		@Override
		public void focusLost(FocusEvent e)
		{
			switch(colorRuleCheckType)
			{
			case CHECK_ONLY_IN_FOCUS:
				allowColorRuleCheck = false;
				setSuperColor(defaultBackgroundColor, defaultForegroundColor);
				break;
			case START_CHECK_FROM_FIRST_FOCUS_LOST:
				allowColorRuleCheck = true;
				break;
			default:
				return;
			}

			checkColorRule();
		}

		@Override
		public void focusGained(FocusEvent e)
		{
			if(!allowColorRuleCheck)
			{
				switch(colorRuleCheckType)
				{
				case CHECK_ONLY_IN_FOCUS:
				case START_CHECK_FROM_FIRST_FOCUS_GAINED:
					allowColorRuleCheck = true;
					break;
				default:
					return;
				}
			}

			checkColorRule();
		}
	};
}
