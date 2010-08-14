package cop.swt.widgets.menus.interfaces;

import java.util.Locale;

import cop.swt.images.ImageProvider;

public interface IMenuItemModifier
{
	String getText(Locale locale);
	
	String getKey();
	
	ImageProvider getImageProvider();
}
