package cop.swt.widgets.calendar;

import static cop.common.extensions.BitExtension.isBitSet;
import static cop.common.extensions.CollectionExtension.addNotNull;
import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.swt.widgets.enums.DateTimePartEnum.BACKGROUND_PART;
import static cop.swt.widgets.enums.DateTimePartEnum.ENABLE_BUTTON_PART;
import static cop.swt.widgets.enums.DateTimePartEnum.SEPARATOR_PART;
import static org.eclipse.swt.SWT.CENTER;
import static org.eclipse.swt.SWT.CHECK;
import static org.eclipse.swt.SWT.COLOR_BLACK;
import static org.eclipse.swt.SWT.COLOR_WHITE;
import static org.eclipse.swt.SWT.CTRL;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.FLAT;
import static org.eclipse.swt.SWT.NONE;
import static org.eclipse.swt.SWT.NO_FOCUS;
import static org.eclipse.swt.SWT.READ_ONLY;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import cop.swt.widgets.ControlSettings;
import cop.swt.widgets.PropertiesWrapper;
import cop.swt.widgets.enums.DateTimePartEnum;
import cop.swt.widgets.interfaces.Correctable;
import cop.swt.widgets.numeric.IntegerNumeric;

/*
 * 
 * 	private Point calculatePoint(Rectangle rect)
 {
 Point point = button.getParent().toDisplay(new Point(rect.x, rect.y));

 point.x -= rect.width * 5;
 point.y += rect.height;

 return point;
 }
 */
public abstract class DateTimeControl extends Composite implements ControlSettings
{
	protected static final int DEFAULT_VALUE = -1;
	private Map<DateTimePartEnum, Set<Control>> ctrls = new HashMap<DateTimePartEnum, Set<Control>>();

	private FontData fontData;
	private boolean enabled = true;
	private PropertiesWrapper properties = new PropertiesWrapper();

	public DateTimeControl(Composite parent, int style)
	{
		this(parent, style, null);
	}

	public DateTimeControl(Composite parent, int style, Properties properties)
	{
		super(parent, style);

		this.properties = new PropertiesWrapper(properties);

		checkProperties(this.properties);

		List<String> parts = getParts();

		configureComposite(parts.size(), style);
		createMainControls(parts);
		createButtonControls(style);

		setBackgroundRGB(getColorRGB(COLOR_WHITE));
		setForegroundRGB(getColorRGB(COLOR_BLACK));

		setDate(Calendar.getInstance());
	}

	@Override
	protected void checkSubclass()
	{}

	private void configureComposite(int numColumns, int style)
	{
		GridLayout layout = new GridLayout(getNumColumn(numColumns, style), false);

		layout.horizontalSpacing = getIntegerProperty(keyLayoutHorizontalSpacing, 0);
		layout.verticalSpacing = getIntegerProperty(keyLayoutVerticalSpacing, 0);
		layout.marginTop = getIntegerProperty(keyLayoutMarginTop, 0);
		layout.marginBottom = getIntegerProperty(keyLayoutMarginBottom, 0);
		layout.marginHeight = getIntegerProperty(keyLayoutMarginHeight, 0);
		layout.marginWidth = getIntegerProperty(keyLayoutMarginWidth, 0);
		layout.marginLeft = getIntegerProperty(keyLayoutMarginLeft, 0);
		layout.marginRight = getIntegerProperty(keyLayoutMarginRight, 0);

		super.setLayout(layout);
		super.setLayoutData(createGridData());
	}

	protected int getNumColumn(int numColumns, int style)
	{
		if(isBitSet(style, READ_ONLY))
			return numColumns;

//		if(isBitSet(style, DROP_DOWN))
//			numColumns++;

//		if(style == 0 || isBitSet(style, CTRL))
//			numColumns++;

		return numColumns;
	}

	protected GridData createGridData()
	{
		GridData grid = new GridData();

		grid.horizontalAlignment = getIntegerProperty(keyGridHorizontalAlignment, FILL);
		grid.verticalAlignment = CENTER;
		grid.grabExcessHorizontalSpace = getBooleanProperty(keyGridGrabExcessHorizontalSpace, false);
		grid.grabExcessVerticalSpace = false;

		return grid;
	}

	protected boolean addControl(DateTimePartEnum part, Control ctrl)
	{
		if(ctrl == null)
			return false;

		Set<Control> set = ctrls.get(part);
		boolean res = false;

		if(set == null)
		{
			set = new LinkedHashSet<Control>();

			res = set.add(ctrl);
			ctrls.put(part, set);
		}
		else
			res = set.add(ctrl);

		return res;
	}

	protected Set<Control> removeControl(DateTimePartEnum part)
	{
		return ctrls.remove(part);
	}

	protected boolean removeControl(DateTimePartEnum part, Control ctrl)
	{
		Set<Control> set = ctrls.get(part);

		return (set != null) ? set.remove(ctrl) : false;
	}

	protected Set<Control> getControl(DateTimePartEnum part)
	{
		Set<Control> set = ctrls.get(part);

		return isEmpty(set) ? new HashSet<Control>() : set;
	}

	private void createEnableButton()
	{
		Button button = new Button(this, CHECK | FLAT | NO_FOCUS);

		button.addSelectionListener(setEnableOnSelection);
		button.setSelection(true);

		addControl(ENABLE_BUTTON_PART, button);
	}

	protected void createSeparator(String str)
	{
		Label separator = new Label(this, NONE);

		separator.setLayoutData(createGridData());
		separator.setText(str);

		addControl(SEPARATOR_PART, separator);
	}

	protected IntegerNumeric createTextControl(Composite parent, boolean leadZero, int minumum, int maximum)
	{
		return createTextControl(parent, leadZero, minumum, maximum, -1, null);
	}

	protected IntegerNumeric createTextControl(Composite parent, boolean leadZero, int minumum, int maximum,
	                int digitLimit, Correctable correctionMethod)
	{
		// NumericText ctrl = new NumericText(parent, NONE, minumum, maximum, digitLimit, leadZero, false);

		IntegerNumeric ctrl = new IntegerNumeric(parent, NONE);

		ctrl.setLayoutData(createGridData());
		//ctrl.setCorrectionMethod(correctionMethod);

		return ctrl;
	}

	protected boolean isControlEnable(List<? extends Control> controls)
	{
		Set<Control> set = getControl(ENABLE_BUTTON_PART);

		for(Control ctrl : set)
			return ((Button)ctrl).getSelection();

		return false;
	}

	protected Set<Control> getAllControls()
	{
		Set<Control> set = new LinkedHashSet<Control>();

		for(Set<Control> ctrl : ctrls.values())
			set.addAll(ctrl);

		return set;
	}

	protected Set<Control> getEnableControls()
	{
		Set<Control> set = new LinkedHashSet<Control>();

		addNotNull(set, ctrls.get(SEPARATOR_PART));

		return set;
	}

	protected Set<Control> getButtonControls()
	{
		Set<Control> set = new LinkedHashSet<Control>();

		addNotNull(set, ctrls.get(ENABLE_BUTTON_PART));

		return set;
	}

	public void setBackgroundRGB(RGB rgb)
	{
		setBackgroundRGB(BACKGROUND_PART, rgb);
	}

	public void setBackgroundRGB(DateTimePartEnum part, RGB rgb)
	{
		if(part == null || rgb == null)
			return;

		Color color = new Color(getDisplay(), rgb);

		switch(part)
		{
		case BACKGROUND_PART:
			super.setBackground(color);

			for(Control ctrl : getAllControls())
				ctrl.setBackground(color);
			break;
		case COMPOSITE_PART:
		case ENABLE_BUTTON_PART:
			super.setBackground(color);

			for(Control ctrl : getControl(ENABLE_BUTTON_PART))
				ctrl.setBackground(color);
			break;
		case SEPARATOR_PART:
			for(Control ctrl : getControl(SEPARATOR_PART))
				ctrl.setBackground(color);
			break;
		default:
			break;
		}
	}

	@Override
	@Deprecated
	public void setBackground(Color color)
	{}

	public void setForegroundRGB(RGB rgb)
	{
		setForegroundRGB(BACKGROUND_PART, rgb);
	}

	public void setForegroundRGB(DateTimePartEnum part, RGB rgb)
	{
		if(part == null || rgb == null)
			return;

		Color color = new Color(getDisplay(), rgb);

		switch(part)
		{
		case BACKGROUND_PART:
			super.setForeground(color);

			for(Control ctrl : getAllControls())
				ctrl.setForeground(color);
			break;
		case COMPOSITE_PART:
		case ENABLE_BUTTON_PART:
			super.setForeground(color);

			for(Control ctrl : getControl(ENABLE_BUTTON_PART))
				ctrl.setForeground(color);
			break;
		case SEPARATOR_PART:
			for(Control ctrl : getControl(SEPARATOR_PART))
				ctrl.setForeground(color);
			break;
		default:
			break;
		}
	}

	@Override
	@Deprecated
	public void setForeground(Color color)
	{}

	public RGB getBackgroundRGB()
	{
		return super.getBackground().getRGB();
	}

	public RGB getBackgroundRGB(DateTimePartEnum part)
	{
		switch(part)
		{
		case BACKGROUND_PART:
		case COMPOSITE_PART:
			return super.getBackground().getRGB();
		case ENABLE_BUTTON_PART:
			for(Control ctrl : getControl(ENABLE_BUTTON_PART))
				return ctrl.getBackground().getRGB();
			break;
		case SEPARATOR_PART:
			for(Control ctrl : getControl(SEPARATOR_PART))
				return ctrl.getBackground().getRGB();
			break;
		default:
			break;
		}

		return null;
	}

	public RGB getForegroundRGB()
	{
		return super.getForeground().getRGB();
	}

	public RGB getForegroundRGB(DateTimePartEnum part)
	{
		switch(part)
		{
		case BACKGROUND_PART:
		case COMPOSITE_PART:
			return super.getForeground().getRGB();
		case ENABLE_BUTTON_PART:
			for(Control ctrl : getControl(ENABLE_BUTTON_PART))
				return ctrl.getForeground().getRGB();
			break;
		case SEPARATOR_PART:
			for(Control ctrl : getControl(SEPARATOR_PART))
				return ctrl.getForeground().getRGB();
			break;
		default:
			break;
		}

		return null;
	}

	@Override
	public boolean getEnabled()
	{
		return isEnabled();
	}

	@Override
	public boolean isEnabled()
	{
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;

		for(Control ctrl : getEnableControls())
			ctrl.setEnabled(enabled);
	}

	protected abstract PropertiesWrapper checkProperties(PropertiesWrapper properties);

	protected abstract Set<Control> getDataControls();

	protected abstract void createMainControls(List<String> parts);

	protected void createButtonControls(int style)
	{
		if(isBitSet(style, READ_ONLY))
			return;

		if(style == NONE || isBitSet(style, CTRL))
			createEnableButton();
	}

	protected abstract List<String> getParts();

	public void replaceExistingProperty(String key, String value)
	{
		if(properties != null)
			properties.replaceExistingProperty(key, value);
	}

	public void setOnlyNewProperty(String key, String value)
	{
		if(properties != null)
			properties.setOnlyNewProperty(key, value);
	}

	public boolean isExists(String key)
	{
		return properties.isExists(key);
	}

	public Integer getIntegerProperty(String key) throws NumberFormatException
	{
		return getIntegerProperty(key, 0);
	}

	public Integer getIntegerProperty(String key, Integer defaultValue) throws NumberFormatException
	{
		return properties.getIntegerProperty(key, defaultValue);
	}

	public Boolean getBooleanProperty(String key)
	{
		return properties.getBooleanProperty(key);
	}

	public Boolean getBooleanProperty(String key, Boolean defaultValue)
	{
		return properties.getBooleanProperty(key, defaultValue);
	}

	public String getProperty(String key)
	{
		return properties.getProperty(key);
	}

	public String getProperty(String key, String defaultValue)
	{
		return properties.getProperty(key, defaultValue);
	}

	public FontData getFontData()
	{
		return fontData;
	}

	public abstract Calendar getDate();

	public abstract void setDate(Calendar date);

	public void setFontData(FontData fontData)
	{
		if(fontData == null)
			return;

		this.fontData = fontData;

		Font font = new Font(getDisplay(), fontData);

		for(Control ctrl : getAllControls())
			ctrl.setFont(font);

		pack();
	}

	public abstract void set(int field, int value);

	public abstract int get(int field);

	@Override
	// @Deprecated
	public void setLayoutData(Object layoutData)
	{
	// for(Control ctrl : getAllControls())
	// ctrl.setLayoutData(layoutData);
	}

	@Override
	public void dispose()
	{
		for(Control ctrl : getAllControls())
			ctrl.dispose();
	}

	public static RGB getColorRGB(int id)
	{
		return Display.getCurrent().getSystemColor(id).getRGB();
	}

	private SelectionListener setEnableOnSelection = new SelectionListener()
	{
		@Override
		public void widgetDefaultSelected(SelectionEvent e)
		{}

		@Override
		public void widgetSelected(SelectionEvent e)
		{
			boolean enabled = ((Button)e.widget).getSelection();

			for(Control ctrl : getEnableControls())
			{
				if(ctrl != null)
					ctrl.setEnabled(enabled);
			}
		}
	};
}
