package cop.swt.widgets.viewers.interfaces;

import org.eclipse.swt.widgets.Widget;

import cop.swt.widgets.viewers.model.enums.ModificationTypeEnum;

public interface IModifyListener<T>
{
	void itemModified(Widget widget, T item, ModificationTypeEnum type);
}
