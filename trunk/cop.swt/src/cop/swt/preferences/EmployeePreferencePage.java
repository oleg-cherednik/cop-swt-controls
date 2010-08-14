package cop.swt.preferences;

import static org.eclipse.swt.SWT.NULL;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class EmployeePreferencePage extends AbstractPreferencePage
{
	/*
	 * PreferencePage
	 */

	@Override
	protected Control createContents(Composite parent)
	{
		return new Composite(parent, NULL);
	}
}
