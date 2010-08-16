package cop.swt.wrappers;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import cop.swt.widgets.interfaces.Creatable;

public final class ExpandableCompositeWrapper<T extends Control> extends WrapperAdapter<T>
{
//	public static <T extends Control> T createExpandableCompositeWrapper(Composite parent, String title,
//	                Creatable<T> createMethod, Layout layout, Object layoutData, int style)
//	{
//		ExpandableCompositeWrapper<T> wrapper = new ExpandableCompositeWrapper<T>(title, createMethod);
//
//		wrapper.setStyle(style);
//		wrapper.setLayout(layout);
//		wrapper.setLayoutData(layoutData);
//
//		return wrapper.create(parent);
//	}

	public ExpandableCompositeWrapper(Creatable<T> createMethod)
	{
		super(createMethod);
	}

	public ExpandableCompositeWrapper(String title, Creatable<T> createMethod)
	{
		super(title, createMethod);
	}

	/**
	 * WrapperAdapter
	 */

	@Override
	protected ExpandableComposite createExpandableComposite(FormToolkit toolkit, final Composite parent)
	{
		ExpandableComposite composite = toolkit.createExpandableComposite(parent, style);

		composite.addExpansionListener(new ExpansionAdapter()
		{
			@Override
			public void expansionStateChanged(ExpansionEvent e)
			{
				parent.getParent().layout(true, true);
			}
		});

		return composite;
	}
}
