package cop.swt.widgets.menu.items.basics;

import static cop.swt.widgets.keys.HotKey.EMPTY_HOT_KEY;
import static cop.swt.widgets.menu.enums.MenuItemStyleEnum.MIS_RADIO;
import cop.swt.widgets.keys.HotKey;
import cop.swt.widgets.menu.enums.MenuItemEnum;

public abstract class AbstractRadioMenuItem extends AbstractSelectionMenuItem
{
	public AbstractRadioMenuItem(MenuItemEnum key)
	{
		this(key, EMPTY_HOT_KEY);
	}

	public AbstractRadioMenuItem(MenuItemEnum key, HotKey accelerator)
	{
		super(MIS_RADIO, key, accelerator);
	}
}
