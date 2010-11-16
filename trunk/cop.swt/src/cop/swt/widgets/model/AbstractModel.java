package cop.swt.widgets.model;

import static cop.common.extensions.CommonExtension.isNotNull;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import cop.swt.widgets.model.interfaces.IModelChange;
import cop.swt.widgets.model.interfaces.Model;

public abstract class AbstractModel<T> implements Model<T>
{
	private final String name;
	private Set<IModelChange<T>> listeners = new CopyOnWriteArraySet<IModelChange<T>>();

	public AbstractModel(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	/*
	 * Model
	 */

	@Override
	public void addListener(IModelChange<T> listener)
	{
		if(isNotNull(listener))
			listeners.add(listener);
	}

	@Override
	public void removeListener(IModelChange<T> listener)
	{
		if(isNotNull(listener))
			listeners.remove(listener);
	}

	@Override
	public void modelChanged()
	{
		for(IModelChange<T> viewerListener : listeners)
			viewerListener.modelChanged(this);
	}

	@Override
	public void modelChanged(T item)
	{
		for(IModelChange<T> viewerListener : listeners)
			viewerListener.modelChanged(this, item);
	}

	@Override
	public void modelChanged(Collection<T> items)
	{
		for(IModelChange<T> viewerListener : listeners)
			viewerListener.modelChanged(this, items);
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		return "Model [name=" + name + "]";
	}
}
