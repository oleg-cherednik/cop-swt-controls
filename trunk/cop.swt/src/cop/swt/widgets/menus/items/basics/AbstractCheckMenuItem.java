package cop.swt.widgets.menus.items.basics;

import static cop.swt.widgets.menus.enums.MenuItemStyleEnum.MIS_CHECK;

import cop.swt.widgets.keys.HotKeyGroup;
import cop.swt.widgets.menus.enums.MenuItemEnum;

public abstract class AbstractCheckMenuItem extends AbstractSelectionMenuItem
{
	public AbstractCheckMenuItem(MenuItemEnum key)
	{
		this(key, null);
	}

	public AbstractCheckMenuItem(MenuItemEnum key, HotKeyGroup accelerator)
	{
		super(MIS_CHECK, key, accelerator);
	}
}
