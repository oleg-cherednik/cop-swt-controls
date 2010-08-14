package cop.swt.widgets.viewers.model;

import cop.swt.widgets.model.AbstractModel;
import cop.swt.widgets.viewers.model.interfaces.ViewerModel;

public abstract class AbstractViewerModel<T> extends AbstractModel<T> implements ViewerModel<T>
{
	public AbstractViewerModel(String name)
	{
		super(name);
	}
}
