package cop.swt.wrappers;

import static org.eclipse.ui.forms.widgets.ExpandableComposite.TWISTIE;
import static org.eclipse.ui.forms.widgets.Section.DESCRIPTION;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import cop.swt.widgets.interfaces.Creatable;

public final class SectionWrapper<T extends Control> extends WrapperAdapter<T>
{
	private String description;
	private boolean separator = true;

	// public static <T extends Control> T createSectionWrapper(Composite parent, String title,
	// Creatable<T> createMethod, Layout layout, Object layoutData, int style)
	// {
	// SectionWrapper<T> wrapper = new SectionWrapper<T>(title, createMethod);
	//
	// wrapper.setStyle(style);
	// wrapper.setLayout(layout);
	// wrapper.setLayoutData(layoutData);
	//
	// return wrapper.create(parent);
	// }

	public SectionWrapper(Creatable<T> createMethod)
	{
		super(createMethod);
	}

	public SectionWrapper(String title, Creatable<T> createMethod)
	{
		super(title, createMethod);
	}

	public SectionWrapper(String title, Creatable<T> createMethod, int style)
	{
		super(title, createMethod, style);
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setSeparator(boolean separator)
	{
		this.separator = separator;
	}

	/**
	 * WrapperAdapter
	 */

	@Override
	protected Section createExpandableComposite(FormToolkit toolkit, Composite parent)
	{
		if(description != null)
			style |= DESCRIPTION;

		if(expandable)
			style |= TWISTIE;

		Section section = toolkit.createSection(parent, style);

		if(description != null)
			section.setDescription(description);

		if(separator)
			toolkit.createCompositeSeparator(section);

		return section;
	}
}
