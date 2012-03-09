package cop.swt.widgets.dirty;

import static cop.extensions.CollectionExt.isEmpty;
import static cop.extensions.CommonExt.isNull;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

import cop.swt.widgets.dirty.interfaces.IDirtyListener;
import cop.swt.widgets.dirty.interfaces.IDirtyObserver;

public abstract class DirtyObserver implements IDirtyObserver
{
	protected List<IDirtyListener> listeners;
	private boolean dirty;
	private boolean notified;

	protected void checkDirty()
	{
		boolean newDirty = getNewDirty();

		if(dirty == newDirty)
			return;

		dirty = newDirty;
		notified = false;

		sendListeners();
	}

	private void sendListeners()
	{
		if(notified || isEmpty(listeners))
			return;

		for(IDirtyListener listener : listeners)
			listener.setDirty(dirty);

		notified = true;
	}

	protected abstract boolean getNewDirty();

	protected abstract void setOriginal();

	protected abstract void addDirtyListener();

	protected abstract void removeDirtyListener();

	/*
	 * IDirty
	 */

	@Override
	public boolean isDirty()
	{
		return dirty;
	}

	//TODO
	@Override
	public void setDirty(boolean dirty)
	{
		setOriginal();

		if(dirty)
			checkDirty();
	}

	/*
	 * IDirtyObserver
	 */

	@Override
	public void addDirtyListener(IDirtyListener listener) throws NullPointerException
	{
		if(isNull(listener))
			throw new NullPointerException("listener == null");

		if(isNull(listeners))
			listeners = new LinkedList<IDirtyListener>();

		listeners.add(listener);

		if(listeners.size() == 1)
			addDirtyListener();
	}

	@Override
	public void removeDirtyListener(IDirtyListener listener) throws NullPointerException
	{
		if(isNull(listener))
			throw new NullPointerException("listener == null");

		if(isNull(listeners))
			return;

		listeners.remove(listener);

		if(listeners.isEmpty())
			removeDirtyListener();
	}

	@Override
	public void removeAllDirtyListener()
	{
		if(isEmpty(listeners))
			return;

		listeners.clear();
		removeDirtyListener();
	}

	/*
	 * Listeners
	 */

	protected final ModifyListener checkDirtyOnModify = new ModifyListener()
	{
		@Override
		public void modifyText(ModifyEvent e)
		{
			try
			{
				checkDirty();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	};
}
