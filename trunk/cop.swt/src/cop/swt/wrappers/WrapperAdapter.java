package cop.swt.wrappers;

import static org.eclipse.swt.SWT.NONE;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import cop.swt.widgets.interfaces.Creatable;

public abstract class WrapperAdapter<T extends Control> implements IWrapper<T>
{
	private ExpandableComposite wrapper;
	private String title;
	protected boolean expandable = true;
	private Layout layout;// = new GridLayout();
	private Object layoutData;// = new GridData(FILL, BOTTOM, true, true);
	private Creatable<T> createMethod;
	private boolean expanded = true;
	protected int style = NONE;
	private Color activeToggleColor;
	private Color toggleColor;

	public WrapperAdapter(Creatable<T> createMethod)
	{
		this(null, createMethod, NONE);
	}

	public WrapperAdapter(String title, Creatable<T> createMethod)
	{
		this(title, createMethod, NONE);
	}

	public WrapperAdapter(String title, Creatable<T> createMethod, int style)
	{
		this.title = title;
		this.createMethod = createMethod;
		this.style = style;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setExpandable(boolean expandable)
	{
		this.expandable = expandable;
	}

	public void setLayout(Layout layout)
	{
		if(wrapper == null)
			this.layout = layout;
		else
			wrapper.setLayout(layout);
	}

	public void setLayoutData(Object layoutData)
	{
		if(wrapper == null)
			this.layoutData = layoutData;
		else
			wrapper.setLayoutData(layoutData);
	}

	public void setExpanded(boolean expanded)
	{
		this.expanded = expanded;
	}

	public void setStyle(int style)
	{
		this.style = style;
	}

	public void setActiveToggleColor(Color activeToggleColor)
	{
		this.activeToggleColor = activeToggleColor;
	}

	public void setToggleColor(Color toggleColor)
	{
		this.toggleColor = toggleColor;
	}

	protected void setActiveToggleColor()
	{
		if(wrapper != null && activeToggleColor != null)
			wrapper.setActiveToggleColor(activeToggleColor);
	}

	public void setToggleColor()
	{
		if(wrapper != null && toggleColor != null)
			wrapper.setToggleColor(toggleColor);
	}

	protected void createLayout()
	{
		if(wrapper != null && layoutData != null)
			wrapper.setLayoutData(layoutData);
	}

	protected void createLayoutData()
	{
		if(wrapper != null && layout != null)
			wrapper.setLayout(layout);
	}

	/**
	 * Creatable
	 */

	@Override
	public T create(Composite parent) throws NullPointerException
	{
		if(parent == null || createMethod == null)
			throw new NullPointerException("parent == null || createMethod == null");

		FormToolkit toolkit = new FormToolkit(parent.getDisplay());

		wrapper = createExpandableComposite(toolkit, parent);
		wrapper.setText(title);

		setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));

		setActiveToggleColor();
		setToggleColor();
		createLayout();
		createLayoutData();

		T control = createMethod.create(wrapper);

		wrapper.setClient(control);
		wrapper.setExpanded(expandable && expanded);

		return control;
	}

	abstract ExpandableComposite createExpandableComposite(FormToolkit toolkit, Composite parent);
}
