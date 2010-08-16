package cop.swt.widgets.viewers.interfaces;

public interface ModifyListenerSupport<T>
{
	void addModifyListener(IModifyListener<T> listener);

	void removeModifyListener(IModifyListener<T> listener);
}
