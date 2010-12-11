package cop.swt.widgets.menu.items;

import java.util.Locale;

import org.eclipse.swt.widgets.Listener;

import cop.common.extensions.StringExtension;
import cop.swt.widgets.menu.enums.MenuItemEnum;
import cop.swt.widgets.menu.interfaces.PropertyProvider;
import cop.swt.widgets.menu.items.basics.AbstractPushMenuItem;

public class PushMenuItem extends AbstractPushMenuItem
{
	public PushMenuItem(MenuItemEnum key)
	{
		super(key, key.getAccelerator());
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
	 * IMenuItem
	 */

	@Override
	public String getText(Locale locale)
	{
		return StringExtension.getText(key.i18n(locale), key.getText());
	}
}
