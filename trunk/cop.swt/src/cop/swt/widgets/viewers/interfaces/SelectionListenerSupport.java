package cop.swt.widgets.viewers.interfaces;

public interface SelectionListenerSupport<T>
{
	void addSelectionListener(ISelectionListener<T> listener);

	void removeSelectionListener(ISelectionListener<T> listener);
}
