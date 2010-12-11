package cop.swt.widgets.model.interfaces;

import cop.swt.widgets.viewers.model.enums.ModificationTypeEnum;

public interface Model<T> extends ModelChangedListener<T>
{
	String getName();
	
	void addListener(ModelChangedListener<T> listener);

	void removeListener(ModelChangedListener<T> listener);

	void modify(T item, ModificationTypeEnum type);
}
