package cop.swt.widgets.menus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import cop.common.extensions.CollectionExtension;
import cop.common.structures.EntrySet;
import cop.swt.widgets.keys.HotKey;
import cop.swt.widgets.keys.HotKeyManager;
import cop.swt.widgets.localization.interfaces.LocaleSupport;
import cop.swt.widgets.menus.enums.MenuItemEnum;
import cop.swt.widgets.menus.interfaces.IMenuBuilder;
import cop.swt.widgets.menus.interfaces.IMenuItem;
import cop.swt.widgets.menus.items.SeparatorMenuItem;

public final class MenuManager implements LocaleSupport
{
	public static final String MENU_ITEM_PATH = "menu_item_path";
	public static final String MENU_ITEM_PATH_SEPARATOR = "/";

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
		HotKey group;
		Listener listener;
		Entry<String, ? extends Object> data;

		menuItems = menuBuilder.getMenuItems();

		for(IMenuItem menuItem : menuItems)
		{
			if(keyManager == null || menuItem.getListener() == null)
				continue;

			group = menuItem.getAccelerator();
			listener = menuItem.getListener();
			data = new EntrySet<String, MenuItemEnum>(MenuItemEnum.MENU_ITEM_ENUM, menuItem.getMenuItemKey());

			keyManager.addHotKey(group, listener, data);
		}
	}

	public Menu createMenu()
	{
		if(isEmpty())
			return null;

		Menu menu = new Menu(control);
		menu.setData(MENU_ITEM_PATH, "");

		return createMenu(menu);
	}

	public Menu createMenu(MenuItem parentItem)
	{
		if(isEmpty())
			return null;

		Menu menu = new Menu(parentItem);

		menu.setData(MENU_ITEM_PATH, parentItem.getData(MENU_ITEM_PATH));

		return createMenu(menu);
	}

	protected final Menu createMenu(Menu menu)
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
