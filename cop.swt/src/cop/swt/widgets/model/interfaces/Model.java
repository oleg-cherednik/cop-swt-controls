package cop.swt.widgets.model.interfaces;

import cop.swt.widgets.viewers.model.enums.ModificationTypeEnum;

public interface Model<T>
{
	void addListener(IModelChange<T> listener);

	void removeListener(IModelChange<T> listener);

	void modelChanged();
	
	void modify(T item, ModificationTypeEnum type);
}
