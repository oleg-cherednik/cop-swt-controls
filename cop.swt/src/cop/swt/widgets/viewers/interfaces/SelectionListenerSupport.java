package cop.swt.widgets.viewers.interfaces;

import org.eclipse.jface.viewers.ISelectionChangedListener;

public interface SelectionListenerSupport
{
	void addSelectionListener(ISelectionChangedListener listener);

	void removeSelectionListener(ISelectionChangedListener listener);
}
