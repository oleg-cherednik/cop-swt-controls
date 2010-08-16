package cop.swt.widgets.viewers.model.interfaces;

public interface ModelSupport<T>
{
	void beginListenToModel(ViewerModel<T> model);

	void stopListenToModel(ViewerModel<T> model);
}
