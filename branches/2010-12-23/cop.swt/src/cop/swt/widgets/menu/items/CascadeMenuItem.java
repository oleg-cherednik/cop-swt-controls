package cop.swt.widgets.menu.items;

import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import cop.swt.widgets.keys.HotKeyManager;
import cop.swt.widgets.menu.MenuManager;
import cop.swt.widgets.menu.enums.MenuItemEnum;
import cop.swt.widgets.menu.interfaces.IMenuBuilder;
import cop.swt.widgets.menu.interfaces.PropertyProvider;
import cop.swt.widgets.menu.items.basics.AbstractCascadeMenuItem;

public class CascadeMenuItem extends AbstractCascadeMenuItem
{
	private MenuManager menuManager;

	public CascadeMenuItem(Control control, HotKeyManager keyManager, MenuItemEnum key, IMenuBuilder menuBuilder)
	{
		super(key, key.getAccelerator());

		Assert.isNotNull(menuBuilder);

		this.menuManager = new MenuManager(control, keyManager, menuBuilder);
	}

	public CascadeMenuItem(Control control, HotKeyManager keyManager, MenuItemEnum key, IMenuBuilder menuBuilder,
	                PropertyProvider<Boolean> visibleProvider, PropertyProvider<Boolean> enabledProvider)
	{
		this(control, keyManager, key, menuBuilder);

		setVisibleProvider(visibleProvider);
		setEnabledProvider(enabledProvider);
	}

	/*
	 * ICreateMenu
	 */

	@Override
	public MenuItem create(Menu parent)
	{
		MenuItem item = super.create(parent);
		item.setMenu(menuManager.createMenu(item));

		return item;
	}

	/*
	 * IMenuItem
	 */

	@Override
	public String getText(Locale locale)
	{
		return getMenuItemKey().i18n(locale);
	}

	@Override
	public boolean isVisible()
	{
		boolean visible = super.isVisible();

		if(!visible)
			return false;

		Assert.isNotNull(hasSubMenuProvider);

		visible = hasSubMenuProvider.getProperty();

		return visible;
	}

	/*
	 * LocaleSupport
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		Assert.isNotNull(menuManager);

		menuManager.setLocale(locale);
	}

	/**
	 * property providers
	 */

	private PropertyProvider<Boolean> hasSubMenuProvider = new PropertyProvider<Boolean>()
	{
		@Override
		public Boolean getProperty()
		{
			return !menuManager.isEmpty();
		}
	};
}
