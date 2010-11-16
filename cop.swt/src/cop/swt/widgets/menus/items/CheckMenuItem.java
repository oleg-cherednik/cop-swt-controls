package cop.swt.widgets.menus.items;

import java.util.Locale;

import org.eclipse.swt.widgets.Listener;

import cop.common.extensions.StringExtension;
import cop.swt.widgets.menus.enums.MenuItemEnum;
import cop.swt.widgets.menus.interfaces.PropertyProvider;
import cop.swt.widgets.menus.items.basics.AbstractCheckMenuItem;

public class CheckMenuItem extends AbstractCheckMenuItem
{
	public CheckMenuItem(MenuItemEnum key)
	{
		super(key, key.getAccelerator());
	}

	public CheckMenuItem(MenuItemEnum key, PropertyProvider<Boolean> selectionProvider, Listener listener)
	{
		this(key, null, null, selectionProvider, listener);
	}

	public CheckMenuItem(MenuItemEnum key, PropertyProvider<Boolean> visibleProvider,
	                PropertyProvider<Boolean> enabledProvider, PropertyProvider<Boolean> selectionProvider,
	                Listener listener)
	{
		this(key);

		setSelectionProvider(selectionProvider);
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
		return key.name();
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
