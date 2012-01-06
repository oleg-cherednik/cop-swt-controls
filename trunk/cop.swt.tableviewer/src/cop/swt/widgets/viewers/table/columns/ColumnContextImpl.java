package cop.swt.widgets.viewers.table.columns;

import java.util.Locale;

import cop.swt.images.ImageProvider;

public class ColumnContextImpl implements ColumnContext
{
	private ImageProvider imageProvider;
	private Locale locale;

	public void setImageProvider(ImageProvider imageProvider)
	{
		this.imageProvider = imageProvider;
	}

	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}

	/*
	 * ColumnSettingsContext
	 */

	@Override
	public ImageProvider getImageProvider()
	{
		return imageProvider;
	}

	@Override
	public Locale getLocale()
	{
		return locale;
	}

}
