package cop.swt.widgets.menus.items.basics;

import static cop.swt.widgets.menus.enums.MenuItemStyleEnum.MIS_CASCADE;
import cop.swt.widgets.keys.HotKeyGroup;
import cop.swt.widgets.menus.enums.MenuItemEnum;

public abstract class AbstractCascadeMenuItem extends AbstractMenuItem
{
	public AbstractCascadeMenuItem(MenuItemEnum key)
	{
		this(key, null);
	}

	public AbstractCascadeMenuItem(MenuItemEnum key, HotKeyGroup accelerator)
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
