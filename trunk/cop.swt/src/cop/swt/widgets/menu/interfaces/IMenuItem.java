package cop.swt.widgets.menu.interfaces;

import java.util.Locale;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Listener;

import cop.swt.images.ImageProvider;
import cop.swt.widgets.keys.HotKey;
import cop.swt.widgets.localization.interfaces.LocaleSupport;
import cop.swt.widgets.menu.enums.MenuItemEnum;

public interface IMenuItem extends ICreateMenu, LocaleSupport
{
	String getText(Locale locale);

	String getTitle(); // text + accelerator

	Listener getListener();

	HotKey getAccelerator();

	Image getImage();

	boolean isVisible();

	boolean isEnabled();

	void setImageProvider(ImageProvider imageProvider);

	MenuItemEnum getMenuItemKey();
}
