package cop.swt.widgets.menu.items.basics;

import static cop.swt.widgets.keys.HotKey.EMPTY_HOT_KEY;
import static cop.swt.widgets.menu.enums.MenuItemStyleEnum.MIS_CASCADE;
import cop.swt.widgets.keys.HotKey;
import cop.swt.widgets.menu.interfaces.MenuItemKey;

public abstract class AbstractCascadeMenuItem extends AbstractMenuItem
{
	public AbstractCascadeMenuItem(MenuItemKey key)
	{
		this(key, EMPTY_HOT_KEY);
	}

	public AbstractCascadeMenuItem(MenuItemKey key, HotKey accelerator)
	{
		super(MIS_CASCADE, key, accelerator);
	}

	/**
	 * AbstractMenuItem
	 */

	@Override
	protected boolean isListenerEnabled()
	{
		return true;
	}
}
