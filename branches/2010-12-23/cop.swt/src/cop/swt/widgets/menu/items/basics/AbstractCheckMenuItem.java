package cop.swt.widgets.menu.items.basics;

import static cop.swt.widgets.menu.enums.MenuItemStyleEnum.MIS_CHECK;
import cop.swt.widgets.keys.HotKey;
import static cop.swt.widgets.keys.HotKey.*;
import cop.swt.widgets.menu.enums.MenuItemEnum;

public abstract class AbstractCheckMenuItem extends AbstractSelectionMenuItem
{
	public AbstractCheckMenuItem(MenuItemEnum key)
	{
		this(key, EMPTY_HOT_KEY);
	}

	public AbstractCheckMenuItem(MenuItemEnum key, HotKey accelerator)
	{
		super(MIS_CHECK, key, accelerator);
	}
}
