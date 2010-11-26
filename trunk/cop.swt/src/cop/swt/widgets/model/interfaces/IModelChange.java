package cop.swt.widgets.model.interfaces;

public interface IModelChange<T>
{
	void modelChanged();

	void modelChanged(T... items);
}
