package cop.swt.widgets.model;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import cop.swt.widgets.model.interfaces.Model;
import cop.swt.widgets.model.interfaces.ModelChangedListener;

public abstract class AbstractModel<T> implements Model<T>
{
	private final String name;
	private Set<ModelChangedListener<T>> listeners = new CopyOnWriteArraySet<ModelChangedListener<T>>();

	public AbstractModel(String name)
	{
		this.name = name;
	}

	/*
	 * Model
	 */

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void addListener(ModelChangedListener<T> listener)
	{
		if(listener != null)
			listeners.add(listener);
	}

	@Override
	public void removeListener(ModelChangedListener<T> listener)
	{
		if(listener != null)
			listeners.remove(listener);
	}

	/*
	 * ModelChanged
	 */

	@Override
	public void modelChanged(T... items)
	{
		for(ModelChangedListener<T> viewerListener : listeners)
			viewerListener.modelChanged(items);
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
