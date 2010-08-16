package cop.swt.widgets.viewers.list;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public abstract class TextLabelProvider implements ILabelProvider
{
	/*
	 * ILabelProvider
	 */

	@Override
	public final Image getImage(Object element)
	{
		return null;
	}

	/*
	 * IBaseLabelProvider
	 */

	@Override
	public void addListener(ILabelProviderListener listener)
	{}

	@Override
	public void removeListener(ILabelProviderListener listener)
	{}

	@Override
	public void dispose()
	{}

	@Override
	public boolean isLabelProperty(Object element, String property)
	{
		return false;
	}
}
