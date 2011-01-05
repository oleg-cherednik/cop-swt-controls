package cop.swt.widgets.viewers;

import cop.swt.images.ImageProvider;
import cop.swt.widgets.viewers.interfaces.ViewerConfig;

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
