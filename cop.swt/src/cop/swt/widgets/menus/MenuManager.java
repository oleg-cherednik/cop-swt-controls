package cop.swt.widgets.menus;

import static cop.common.extensions.CommonExtension.isNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import cop.common.extensions.CollectionExtension;
import cop.swt.widgets.keys.HotKeyManager;
import cop.swt.widgets.localization.interfaces.LocaleSupport;
import cop.swt.widgets.menus.interfaces.IMenuBuilder;
import cop.swt.widgets.menus.interfaces.IMenuItem;
import cop.swt.widgets.menus.items.SeparatorMenuItem;

public final class MenuManager implements LocaleSupport
{
	private Control control;
	private HotKeyManager keyManager;
	private List<IMenuItem> menuItems;

	public MenuManager(Control control, HotKeyManager keyManager, IMenuBuilder menuBuilder)
	{
		Assert.isNotNull(control);
		Assert.isNotNull(menuBuilder);

		this.control = control;
		this.keyManager = keyManager;

		addMenuItems(menuBuilder);
	}

	public boolean isEmpty()
	{
		return CollectionExtension.isEmpty(menuItems);
	}

	private void addMenuItems(IMenuBuilder menuBuilder)
	{
		Assert.isNotNull(menuBuilder);

		menuItems = menuBuilder.getMenuItems();

		for(IMenuItem menuItem : menuItems)
			if(isNotNull(keyManager) && isNotNull(menuItem.getListener()))
				keyManager.addHotKey(menuItem.getAccelerator(), menuItem.getListener(), menuItem);
	}

	public Menu createMenu()
	{
		return isEmpty() ? null : createMenu(new Menu(control));
	}

	public Menu createMenu(MenuItem parentItem)
	{
		return isEmpty() ? null : createMenu(new Menu(parentItem));
	}

	protected Menu createMenu(Menu menu)
	{
		List<List<IMenuItem>> groups = new ArrayList<List<IMenuItem>>();
		List<IMenuItem> arr = new ArrayList<IMenuItem>();

		for(IMenuItem menuItem : menuItems)
		{
			if(menuItem instanceof SeparatorMenuItem)
				storeNotEmptyItemGroup(groups, arr);
			else
				addOnlyVisibleItems(arr, menuItem);
		}

		storeNotEmptyItemGroup(groups, arr);

		for(List<IMenuItem> menuItems : groups)
		{
			if(menu.getItemCount() != 0)
				new SeparatorMenuItem().create(menu);

			for(IMenuItem menuItem : menuItems)
				menuItem.create(menu);
		}

		return menu;
	}

	private static void addOnlyVisibleItems(List<IMenuItem> menuItems, IMenuItem menuItem)
	{
		Assert.isNotNull(menuItems);
		Assert.isNotNull(menuItem);

		if(menuItem.isVisible())
			menuItems.add(menuItem);

	}

	private static void storeNotEmptyItemGroup(List<List<IMenuItem>> groups, List<IMenuItem> menuItems)
	{
		Assert.isNotNull(groups);
		Assert.isNotNull(menuItems);

		if(menuItems.isEmpty())
			return;

		groups.add(new ArrayList<IMenuItem>(menuItems));
		menuItems.clear();
	}

	/*
	 * LocaleSupport
	 */

	@Override
	public void setLocale(Locale locale)
	{
		Assert.isNotNull(locale);

		if(isEmpty())
			return;

		for(IMenuItem menuItem : menuItems)
			menuItem.setLocale(locale);
	}
}
