package cop.swt.widgets.menus.items.basics;

import static cop.swt.widgets.keys.HotKey.EMPTY_HOT_KEY;
import static cop.swt.widgets.menus.enums.MenuItemStyleEnum.MIS_PUSH;
import cop.swt.widgets.keys.HotKey;
import cop.swt.widgets.menus.enums.MenuItemEnum;

public abstract class AbstractPushMenuItem extends AbstractMenuItem
{
	public AbstractPushMenuItem(MenuItemEnum key)
	{
		this(key, EMPTY_HOT_KEY);
	}

	public AbstractPushMenuItem(MenuItemEnum key, HotKey accelerator)
	{
		super(MIS_PUSH, key, accelerator);
	}
}
