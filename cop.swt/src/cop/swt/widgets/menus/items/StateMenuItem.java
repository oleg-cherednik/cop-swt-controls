package cop.swt.widgets.menus.items;

import static cop.swt.widgets.menus.enums.MenuItemEnum.MI_STATE;

import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.Listener;

import cop.common.extensions.StringExtension;
import cop.swt.images.ImageProvider;
import cop.swt.widgets.keys.HotKeyGroup;
import cop.swt.widgets.menus.interfaces.IMenuItemModifier;
import cop.swt.widgets.menus.interfaces.PropertyProvider;
import cop.swt.widgets.menus.items.basics.AbstractPushMenuItem;

public class StateMenuItem extends AbstractPushMenuItem
{
	private static final String KEY = "MI_STATE";

	private IMenuItemModifier itemModifier;

	public StateMenuItem(IMenuItemModifier itemModifier, HotKeyGroup accelerator, Listener listener)
	{
		this(itemModifier, accelerator, null, null, listener);
	}

	public StateMenuItem(IMenuItemModifier itemModifier, HotKeyGroup accelerator,
	                PropertyProvider<Boolean> visibleProvider, PropertyProvider<Boolean> enabledProvider,
	                Listener listener)
	{
		super(MI_STATE, accelerator);

		Assert.isNotNull(itemModifier);

		this.itemModifier = itemModifier;

		setVisibleProvider(visibleProvider);
		setEnabledProvider(enabledProvider);
		setListener(listener);
	}

	public static String getKey()
	{
		return KEY;
	}

	/*
	 * AbstractMenuItem
	 */

	@Override
	protected String _getKey()
	{
		return StringExtension.getText(itemModifier.getKey(), getKey());
	}

	/*
	 * IMenuItem
	 */

	@Override
	public String getText(Locale locale)
	{
		return StringExtension.getText(itemModifier.getText(locale), getKey());
	}

	@Override
	public ImageProvider getIconProvider()
	{
		return itemModifier.getImageProvider();
	}
}
