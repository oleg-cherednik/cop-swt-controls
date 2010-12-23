package cop.swt.widgets.menu.interfaces;

import java.util.Locale;

import cop.swt.images.ImageProvider;

public interface IMenuItemModifier
{
	String getText(Locale locale);
	
	String getKey();
	
	ImageProvider getImageProvider();
}
