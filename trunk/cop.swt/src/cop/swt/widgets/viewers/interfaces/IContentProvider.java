package cop.swt.widgets.viewers.interfaces;

import java.util.List;

import cop.common.exceptions.MethodNotImplementedException;

public interface IContentProvider<T>
{
	public int getItemCount() throws MethodNotImplementedException;

	public List<T> getItems(int index, int maxCount) throws MethodNotImplementedException;

	public List<T> getItems(T parentItem) throws MethodNotImplementedException;

	public List<T> getItems() throws MethodNotImplementedException;
}
