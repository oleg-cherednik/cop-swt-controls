package cop.swt.widgets.model.interfaces;

import cop.swt.widgets.viewers.model.enums.ModificationTypeEnum;

public interface Model<T> extends ModelChanged<T>
{
	void addListener(ModelChanged<T> listener);

	void removeListener(ModelChanged<T> listener);

	void modify(T item, ModificationTypeEnum type);
}
