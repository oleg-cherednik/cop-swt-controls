package cop.swt.widgets.model.interfaces;

import java.util.Collection;

import cop.swt.widgets.viewers.model.enums.ModificationTypeEnum;

public interface Model<T>
{
	void addListener(IModelChange<T> listener);

	void removeListener(IModelChange<T> listener);

	void modelChanged();

	void modelChanged(T item);

	void modelChanged(Collection<T> items);

	void modify(T item, ModificationTypeEnum type);
}
