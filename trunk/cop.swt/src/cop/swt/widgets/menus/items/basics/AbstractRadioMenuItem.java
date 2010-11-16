package cop.swt.widgets.menus.items.basics;

import static cop.swt.widgets.menus.enums.MenuItemStyleEnum.MIS_RADIO;
import cop.swt.widgets.keys.HotKeyGroup;
import cop.swt.widgets.menus.enums.MenuItemEnum;

public abstract class AbstractRadioMenuItem extends AbstractSelectionMenuItem
{
	public AbstractRadioMenuItem(MenuItemEnum key)
	{
		this(key, null);
	}

	public AbstractRadioMenuItem(MenuItemEnum key, HotKeyGroup accelerator)
	{
		super(MIS_RADIO, key, accelerator);
	}
}
