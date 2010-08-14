package cop.swt.widgets.viewers.interfaces;

import java.util.List;

import org.eclipse.swt.widgets.Widget;

public interface ISelectionListener<T>
{
	void itemSelected(Widget widget, List<T> items);
}
