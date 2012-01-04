package cop.swt.widgets.viewers.table.columns;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

import cop.swt.images.ImageProvider;

public class ColumnSettingsContextImpl implements ColumnSettingsContext
{
	private AccessibleObject obj;
	private ImageProvider imageProvider;
	private Locale locale;

	public void setObject(AccessibleObject obj)
	{
		this.obj = obj;
	}

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
	public AccessibleObject getObject()
	{
		return obj;
	}

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
