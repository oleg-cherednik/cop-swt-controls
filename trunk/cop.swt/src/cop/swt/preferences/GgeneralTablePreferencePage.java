package cop.swt.preferences;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class GgeneralTablePreferencePage extends AbstractTablePreferencePage
{
	public GgeneralTablePreferencePage()
	{
		super(null);
	}
	
	/*
	 * AbstractTabPreferencePage
	 */

	@Override
	protected void createPartBefore(Composite parent)
	{
		Composite composite = new Composite(parent, NONE);
		composite.setLayout(new GridLayout());

		Label infoLabel = new Label(composite, NONE);
		infoLabel.setText("Table preferences allow you to configure different table settings");
	}

	// private void createEditModePart(Composite parent)
	// {
	// Assert.isNotNull(parent);
	//
	// Group group = createcreateEditModePartGroup(parent);
	// Composite composite = createComposite(group, true);
	//
	// createDoubleClickButton(composite);
	// createSingleClickButton(composite);
	// }
	//
	// private Group createcreateEditModePartGroup(Composite parent)
	// {
	// Assert.isNotNull(parent);
	//
	// Group group = new Group(parent, NONE);
	//
	// group.setText("Edite mode");
	// group.setBackground(parent.getBackground());
	// group.setLayout(new GridLayout());
	//
	// return group;
	// }
	//
	//
	//
	// /*
	// * PreferencePage
	// */
	//
	// @Override
	// protected Control createContents(Composite parent)
	// {
	// Composite composite = createComposite(parent, false);
	//
	// createReadOnlyButton(composite);
	// createEditModePart(composite);
	//
	// return composite;
	// }
	//
	// /*
	// * IWorkbenchPreferencePage
	// */
	//
	// @Override
	// public void init(IWorkbench workbench)
	// {}
}
