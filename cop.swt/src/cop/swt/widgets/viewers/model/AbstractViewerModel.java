package cop.swt.widgets.viewers.model;

import java.util.Collection;

import org.eclipse.jface.viewers.Viewer;

import cop.swt.widgets.model.AbstractModel;
import cop.swt.widgets.viewers.model.interfaces.ViewerModel;

public abstract class AbstractViewerModel<T> extends AbstractModel<T> implements ViewerModel<T>
{
	public AbstractViewerModel(String name)
	{
		super(name);
	}

	/*
	 * _IContentProvider<T>
	 */

	@Override
	public void inputChanged(Viewer viewer, Collection<T> oldItems, Collection<T> newItems)
	{}
}
