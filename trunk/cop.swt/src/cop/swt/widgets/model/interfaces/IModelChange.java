package cop.swt.widgets.model.interfaces;

public interface IModelChange<T>
{
	void modelChanged(Model<T> model/* AbstractModel model, ModelProperty property */);
}
