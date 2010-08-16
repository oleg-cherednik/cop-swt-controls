package cop.swt.preferences.detailpages;

import static cop.common.extensions.CommonExtension.*;
import static org.eclipse.swt.SWT.CHECK;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.NONE;

import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.forms.widgets.FormToolkit;

import cop.swt.widgets.localization.interfaces.LocaleSupport;
import static cop.swt.widgets.tmp.localization.InterfaceEnumBundle.*;

public class ColumnDetailPage implements LocaleSupport
{
	private Composite composite;
	private FormToolkit toolkit;
	private Locale locale = Locale.getDefault();

	private Group visibilityGroup;
	private Button movableButton;
	private Button resizeableButton;
	private Button readonlyButton;
	private Button sortableButton;
	private Button visibleButton;
	private Button hideableButton;

	public ColumnDetailPage(Composite parent, Locale locale)
	{
		Assert.isNotNull(parent);

		this.toolkit = new FormToolkit(parent.getDisplay());
		this.locale = isNotNull(locale) ? locale : Locale.getDefault();

		createComposite(parent);
		createContents();
		setLocale(locale);
	}

	private void createComposite(Composite parent)
	{
		composite = toolkit.createComposite(parent);

		composite.setLayout(new GridLayout(4, true));
		composite.setBackground(parent.getBackground());
		composite.setLayoutData(new GridData(FILL, FILL, true, true, 2, 1));
	}

	private void createVisibilityGroup(Composite parent)
	{
		visibilityGroup = new Group(parent, NONE);

		visibilityGroup.setText(VISIBILITY.i18n(locale));
		visibilityGroup.setBackground(parent.getBackground());
		visibilityGroup.setLayout(new GridLayout(2, false));
		visibilityGroup.setLayoutData(new GridData(FILL, FILL, false, false, 2, 1));

		visibleButton = toolkit.createButton(visibilityGroup, VISIBLE.i18n(locale), CHECK);
		hideableButton = toolkit.createButton(visibilityGroup, HIDEABLE.i18n(locale), CHECK);

		visibleButton.setBackground(parent.getBackground());
		hideableButton.setBackground(parent.getBackground());

		hideableButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				boolean hideable = hideableButton.getSelection();

				visibleButton.setEnabled(hideable);

				if(!hideable)
					visibleButton.setSelection(true);
			}
		});
	}

	private void createContents()
	{
		movableButton = toolkit.createButton(composite, MOVABLE.i18n(locale), CHECK);
		resizeableButton = toolkit.createButton(composite, RESIZEABLE.i18n(locale), CHECK);
		readonlyButton = toolkit.createButton(composite, READ_ONLY.i18n(locale), CHECK);
		sortableButton = toolkit.createButton(composite, SORTABLE.i18n(locale), CHECK);

		createVisibilityGroup(composite);

		movableButton.setBackground(composite.getBackground());
		resizeableButton.setBackground(composite.getBackground());
		readonlyButton.setBackground(composite.getBackground());
		sortableButton.setBackground(composite.getBackground());
	}

	/*
	 * LocaleSupport
	 */

	@Override
	public void setLocale(Locale locale)
	{
		if(isNull(locale))
			return;

		this.locale = locale;

		if(isNotNull(visibilityGroup))
			visibilityGroup.setText(VISIBILITY.i18n(locale));
		if(isNotNull(visibleButton))
			visibleButton.setText(VISIBLE.i18n(locale));
		if(isNotNull(hideableButton))
			hideableButton.setText(HIDEABLE.i18n(locale));
		if(isNotNull(movableButton))
			movableButton.setText(MOVABLE.i18n(locale));
		if(isNotNull(readonlyButton))
			readonlyButton.setText(READ_ONLY.i18n(locale));
		if(isNotNull(sortableButton))
			sortableButton.setText(SORTABLE.i18n(locale));
		if(isNotNull(resizeableButton))
			resizeableButton.setText(RESIZEABLE.i18n(locale));

		composite.layout();
	}
}
