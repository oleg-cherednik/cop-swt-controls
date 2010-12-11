package cop.swt.widgets.menu.interfaces;

public interface PropertyProvider<T>
{
	// must not return null
	T getProperty();
}
