package cop.swt.widgets.viewers.table;

import cop.swt.images.ImageProvider;
import cop.swt.widgets.viewers.table.interfaces.ViewerConfig;

public abstract class AbstractViewerConfig implements ViewerConfig
{
	private ImageProvider imageProvider;

	public void setImageProvider(ImageProvider imageProvider)
	{
		this.imageProvider = imageProvider;
	}

	/*
	 * ImageProviderSupport
	 */

	@Override
	public ImageProvider getImageProvider()
	{
		return imageProvider;
	}
}
