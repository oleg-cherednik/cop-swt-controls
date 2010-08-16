package cop.swt.widgets.menus.items;

import java.util.Locale;

import org.eclipse.swt.widgets.Listener;

import cop.common.extensions.StringExtension;
import cop.swt.widgets.menus.enums.MenuItemEnum;
import cop.swt.widgets.menus.interfaces.PropertyProvider;
import cop.swt.widgets.menus.items.basics.AbstractPushMenuItem;

public class PushMenuItem extends AbstractPushMenuItem
{
	private final MenuItemEnum KEY;

	public PushMenuItem(MenuItemEnum key)
	{
		super(key.getAccelerator());

		KEY = key;
	}

	public PushMenuItem(MenuItemEnum key, Listener listener)
	{
		this(key, null, null, listener);
	}

	public PushMenuItem(MenuItemEnum key, PropertyProvider<Boolean> visibleProvider,
	                PropertyProvider<Boolean> enabledProvider, Listener listener)
	{
		this(key);

		setVisibleProvider(visibleProvider);
		setEnabledProvider(enabledProvider);
		setListener(listener);
	}

	/*
	 * AbstractMenuItem
	 */

	@Override
	public String _getKey()
	{
		return KEY.name();
	}

	/*
	 * IMenuItem
	 */

	@Override
	public String getText(Locale locale)
	{
		return StringExtension.getText(KEY.i18n(locale), KEY.getText());
	}
}
