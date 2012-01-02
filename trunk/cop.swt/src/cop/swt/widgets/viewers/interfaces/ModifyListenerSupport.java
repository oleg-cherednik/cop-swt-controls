package cop.swt.widgets.viewers.interfaces;

public interface ModifyListenerSupport<T>
{
	void addModifyListener(ItemModifyListener<T> listener);

	void removeModifyListener(ItemModifyListener<T> listener);
}
