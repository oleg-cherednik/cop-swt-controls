package cop.swt.widgets.menus.items.basics;

import static cop.swt.widgets.keys.HotKey.EMPTY_HOT_KEY;
import static cop.swt.widgets.menus.enums.MenuItemStyleEnum.MIS_CASCADE;
import cop.swt.widgets.keys.HotKey;
import cop.swt.widgets.menus.enums.MenuItemEnum;

public abstract class AbstractCascadeMenuItem extends AbstractMenuItem
{
	public AbstractCascadeMenuItem(MenuItemEnum key)
	{
		this(key, EMPTY_HOT_KEY);
	}

	public AbstractCascadeMenuItem(MenuItemEnum key, HotKey accelerator)
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
