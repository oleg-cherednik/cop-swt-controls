package cop.swt.widgets.menus.interfaces;

public interface PropertyProvider<T>
{
	// must not return null
	T getProperty();
}
