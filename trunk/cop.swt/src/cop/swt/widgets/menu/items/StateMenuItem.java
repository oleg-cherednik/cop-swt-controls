package cop.swt.widgets.menu.items;

import static cop.swt.widgets.menu.enums.MenuItemEnum.MI_STATE;

import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.Listener;

import cop.extensions.StringExt;
import cop.swt.images.ImageProvider;
import cop.swt.widgets.keys.HotKey;
import cop.swt.widgets.menu.interfaces.IMenuItemModifier;
import cop.swt.widgets.menu.interfaces.PropertyProvider;
import cop.swt.widgets.menu.items.basics.AbstractPushMenuItem;

public class StateMenuItem extends AbstractPushMenuItem
{
	private IMenuItemModifier itemModifier;

	public StateMenuItem(IMenuItemModifier itemModifier, HotKey accelerator, Listener listener)
	{
		this(itemModifier, accelerator, null, null, listener);
	}

	public StateMenuItem(IMenuItemModifier itemModifier, HotKey accelerator, PropertyProvider<Boolean> visibleProvider,
	                PropertyProvider<Boolean> enabledProvider, Listener listener)
	{
		super(MI_STATE, accelerator);

		Assert.isNotNull(itemModifier);

		this.itemModifier = itemModifier;

		setVisibleProvider(visibleProvider);
		setEnabledProvider(enabledProvider);
		setListener(listener);
	}

	/*
	 * AbstractMenuItem
	 */

	@Override
	protected String _getKey()
	{
		return StringExt.getText(itemModifier.getKey(), super._getKey());
	}

	/*
	 * IMenuItem
	 */

	@Override
	public String getText(Locale locale)
	{
		return StringExt.getText(itemModifier.getText(locale), super._getKey());
	}

	@Override
	public ImageProvider getIconProvider()
	{
		return itemModifier.getImageProvider();
	}
}
