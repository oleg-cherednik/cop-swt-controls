package cop.swt.widgets.menus;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;

import cop.swt.images.ImageProvider;
import cop.swt.images.ImageProviderSupport;
import cop.swt.widgets.menus.interfaces.IMenuBuilder;
import cop.swt.widgets.menus.interfaces.IMenuItem;

public class MenuBuilder implements IMenuBuilder, ImageProviderSupport
{
	private ImageProvider imageProvider;
	private List<IMenuItem> menuItems = new ArrayList<IMenuItem>();

	public MenuBuilder(ImageProvider imageProvider)
	{
		this.imageProvider = imageProvider;
	}

	public void addMenuItem(IMenuItem menuItem)
	{
		Assert.isNotNull(menuItem);

		menuItem.setImageProvider(imageProvider);
		menuItems.add(menuItem);
	}

	/*
	 * IMenuBuilder
	 */

	@Override
	public List<IMenuItem> getMenuItems()
	{
		return menuItems;
	}
	
	/*
	 * IImageProvider
	 */

	@Override
	public ImageProvider getImageProvider()
	{
		return imageProvider;
	}
}
