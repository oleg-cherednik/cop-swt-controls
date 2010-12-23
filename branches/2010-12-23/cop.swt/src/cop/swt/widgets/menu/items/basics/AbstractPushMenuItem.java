package cop.swt.widgets.menu.items.basics;

import static cop.swt.widgets.keys.HotKey.EMPTY_HOT_KEY;
import static cop.swt.widgets.menu.enums.MenuItemStyleEnum.MIS_PUSH;
import cop.swt.widgets.keys.HotKey;
import cop.swt.widgets.menu.interfaces.MenuItemKey;

public abstract class AbstractPushMenuItem extends AbstractMenuItem
{
	public AbstractPushMenuItem(MenuItemKey key)
	{
		this(key, EMPTY_HOT_KEY);
	}

	public AbstractPushMenuItem(MenuItemKey key, HotKey accelerator)
	{
		super(MIS_PUSH, key, accelerator);
	}
}
