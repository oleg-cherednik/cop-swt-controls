package cop.swt.preferences;

import static cop.common.extensions.CommonExtension.isNotNull;
import static org.eclipse.swt.SWT.CHECK;
import static org.eclipse.swt.SWT.RADIO;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import cop.swt.preferences.detailpages.ColumnMasterDetailsPage;

public abstract class AbstractTablePreferencePage<T> extends AbstractTabPreferencePage
{
	protected Button defaultButton;
	protected TabItem generalTab;
	protected TabItem menuTab;
	private Button readOnlyButton;
	private Button enableMenuButton;
	private Button showPropertiesButton;
	private Button doubleClickModeButton;
	private Button singleClickModeButton;

	private ColumnMasterDetailsPage<T> columnTabItem;

	private T obj;

	// private ColumnDetailPage columnDetailsPage;

	public AbstractTablePreferencePage(T obj)
	{
		this.obj = obj;
	}

	private void createReadOnlyButton(Composite parent)
	{
		Assert.isNotNull(parent);

		readOnlyButton = toolkit.createButton(parent, "read only", CHECK);
		readOnlyButton.setBackground(parent.getBackground());
	}

	private void createEditModePart(Composite parent)
	{
		Assert.isNotNull(parent);

		Group group = new Group(parent, NONE);
		group.setText("Edit mode");
		group.setLayout(new GridLayout());
		group.setBackground(parent.getBackground());

		singleClickModeButton = toolkit.createButton(group, "single click", RADIO);
		doubleClickModeButton = toolkit.createButton(group, "double click", RADIO);

		singleClickModeButton.setBackground(parent.getBackground());
		doubleClickModeButton.setBackground(parent.getBackground());
	}

	private void createEnableMenuButton(Composite parent)
	{
		enableMenuButton = toolkit.createButton(parent, "enable", CHECK);
		enableMenuButton.setBackground(parent.getBackground());
	}

	private void createShowPropertiesButton(Composite parent)
	{
		showPropertiesButton = toolkit.createButton(parent, "show properties", CHECK);
		showPropertiesButton.setBackground(parent.getBackground());
	}

	protected Control createGeneralTabControl(Composite parent)
	{
		Composite composite = toolkit.createComposite(parent);
		composite.setLayout(new GridLayout());

		createReadOnlyButton(composite);
		createEditModePart(composite);

		return composite;
	}

	protected Control createMenuTabControl(Composite parent)
	{
		Composite composite = toolkit.createComposite(parent);
		composite.setLayout(new GridLayout());

		createEnableMenuButton(composite);
		createShowPropertiesButton(composite);

		return composite;
	}

	protected Control createColumnsTabControl(Composite parent)
	{
		columnTabItem = new ColumnMasterDetailsPage<T>(obj, parent, store);

		return columnTabItem.getComposite();
	}

	/*
	 * DialogPage
	 */

	@Override
	public void dispose()
	{
		if(isNotNull(columnTabItem))
			columnTabItem.dispose();

		super.dispose();
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

		defaultButton = new Button(composite, CHECK);
		defaultButton.setText("Use default table settings");
		defaultButton.setSelection(true);
		defaultButton.addSelectionListener(onDefaultButton);
	}

	@Override
	protected void postCreateContents()
	{
		tabFolder.setVisible(!defaultButton.getSelection());
	}

	@Override
	protected TabFolder createTabFolder(Composite parent)
	{
		TabFolder tabFolder = super.createTabFolder(parent);

		addTabItem(tabFolder, "General", createGeneralTabControl(tabFolder));
		addTabItem(tabFolder, "Menu", createMenuTabControl(tabFolder));
		addTabItem(tabFolder, "Columns", createColumnsTabControl(tabFolder));

		return tabFolder;
	}

	/*
	 * Listener
	 */

	private SelectionListener onDefaultButton = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			tabFolder.setVisible(!defaultButton.getSelection());
		}
	};
}
