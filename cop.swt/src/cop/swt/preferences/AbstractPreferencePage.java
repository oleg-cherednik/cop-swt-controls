package cop.swt.preferences;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

//import com.marathon.calendar.gui.Activator;

public abstract class AbstractPreferencePage extends PreferencePage implements IWorkbenchPreferencePage
{
	protected final IPreferenceStore store;

	public AbstractPreferencePage()
	{
		store = null;
		//setPreferenceStore(store = Activator.getDefault().getPreferenceStore());

		Assert.isNotNull(store);
	}

	/*
	 * IWorkbenchPreferencePage
	 */

	@Override
	public void init(IWorkbench workbench)
	{}
}
