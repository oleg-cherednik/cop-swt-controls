package cop.swt.widgets.menus.items.basics;

import static cop.swt.widgets.menus.enums.MenuItemStyleEnum.MIS_PUSH;
import cop.swt.widgets.keys.HotKeyGroup;
import cop.swt.widgets.menus.enums.MenuItemEnum;

public abstract class AbstractPushMenuItem extends AbstractMenuItem
{
	public AbstractPushMenuItem(MenuItemEnum key)
	{
		this(key, null);
	}

	public AbstractPushMenuItem(MenuItemEnum key, HotKeyGroup accelerator)
	{
		super(MIS_PUSH, key, accelerator);
	}
}
