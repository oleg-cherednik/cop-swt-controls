package cop.swt.widgets.model.interfaces;

public interface IModelChange<T>
{
	void modelChanged(Model<T> model);
//	void modelChanged(Model<T> model, T item);
//	void modelChanged(Model<T> model, Collection<T> items);
}
