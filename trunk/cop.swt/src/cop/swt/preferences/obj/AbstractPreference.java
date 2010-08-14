package cop.swt.preferences.obj;

import static cop.common.extensions.CommonExtension.isNotNull;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

public abstract class AbstractPreference implements IPropertyChangeListener
{
	private Set<IPropertyChangeListener> listeners = new HashSet<IPropertyChangeListener>();
	protected final IPreferenceStore store;

	public AbstractPreference(IPreferenceStore store)
	{
		Assert.isNotNull(store);

		this.store = store;
		this.store.addPropertyChangeListener(this);
		this.store.addPropertyChangeListener(onPropertyChange);
	}

	public void dispose()
	{
		store.removePropertyChangeListener(onPropertyChange);
		store.removePropertyChangeListener(this);

		listeners.clear();
	}

	public void addPropertyChangeListener(IPropertyChangeListener listener)
	{
		if(isNotNull(listener))
			listeners.add(listener);
	}

	public void removePropertyChangeListener(String property, IPropertyChangeListener listener)
	{
		if(isNotNull(listener))
			listeners.remove(listener);
	}

	private void notifyPropertyChangeListener(PropertyChangeEvent event)
	{
		for(IPropertyChangeListener listener : listeners)
			listener.propertyChange(event);
	}

	protected abstract Set<String> getProperties();

	/*
	 * Listener
	 */

	protected IPropertyChangeListener onPropertyChange = new IPropertyChangeListener()
	{
		@Override
		public void propertyChange(PropertyChangeEvent event)
		{
			if(getProperties().contains(event.getProperty()))
				notifyPropertyChangeListener(event);
		}
	};
}
