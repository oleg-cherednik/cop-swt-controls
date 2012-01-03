package cop.swt.widgets.viewers.interfaces;

import org.eclipse.swt.widgets.Widget;

public interface PModifyProvider<T>
{
	boolean canModify(Widget widget, T item, String key);
}
