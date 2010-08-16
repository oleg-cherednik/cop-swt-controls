package cop.swt.widgets.menus.items.basics;

import static cop.swt.widgets.menus.enums.MenuItemStyleEnum.MIS_CHECK;

import cop.swt.widgets.keys.HotKeyGroup;

public abstract class AbstractCheckMenuItem extends AbstractSelectionMenuItem
{
	public AbstractCheckMenuItem()
	{
		this(null);
	}

	public AbstractCheckMenuItem(HotKeyGroup accelerator)
	{
		super(MIS_CHECK, accelerator);
	}
}
