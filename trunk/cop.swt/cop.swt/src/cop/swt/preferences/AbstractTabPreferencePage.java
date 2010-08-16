package cop.swt.preferences;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.StringExtension.isNotEmpty;
import static org.eclipse.swt.SWT.FILL;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

public abstract class AbstractTabPreferencePage extends AbstractPreferencePage
{
	protected TabFolder tabFolder;
	protected FormToolkit toolkit;

	protected Composite createComposite(Composite parent)
	{
		Composite composite = new Composite(parent, NONE);
		composite.setLayout(new GridLayout());

		return composite;
	}

	protected TabFolder createTabFolder(Composite parent)
	{
		Assert.isNotNull(parent);

		TabFolder tabFolder = new TabFolder(parent, NONE);

		tabFolder.setLayoutData(new GridData(FILL, FILL, true, true));

		return tabFolder;
	}

	protected static TabItem addTabItem(TabFolder tabFolder, String str, Control control)
	{
		return addTabItem(tabFolder, str, control, null, null);
	}

	protected static TabItem addTabItem(TabFolder tabFolder, String str, Control control, String tooltip)
	{
		return addTabItem(tabFolder, str, control, tooltip, null);
	}

	protected static TabItem addTabItem(TabFolder tabFolder, String str, Control control, String tooltip, Image image)
	{
		TabItem tabItem = new TabItem(tabFolder, NONE);

		tabItem.setText(str);
		tabItem.setControl(control);

		if(isNotEmpty(tooltip))
			tabItem.setToolTipText(tooltip);

		if(isNotNull(image))
			tabItem.setImage(image);

		return tabItem;
	}
	
	protected void createPartBefore(Composite parent)
	{}

	protected void createPartAfter(Composite parent)
	{}
	
	protected void postCreateContents()
	{}

	/*
	 * PreferencePage
	 */

	@Override
	protected final Control createContents(Composite parent)
	{
		toolkit = new FormToolkit(parent.getDisplay());

		Composite composite = createComposite(parent);

		createPartBefore(composite);
		tabFolder = createTabFolder(composite);
		createPartAfter(composite);
		postCreateContents();

		return composite;
	}
}
