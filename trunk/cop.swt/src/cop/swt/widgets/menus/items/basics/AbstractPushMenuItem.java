package cop.swt.widgets.menus.items.basics;

import static cop.swt.widgets.menus.enums.MenuItemStyleEnum.MIS_PUSH;

import cop.swt.widgets.keys.HotKeyGroup;

public abstract class AbstractPushMenuItem extends AbstractMenuItem
{
	public AbstractPushMenuItem()
	{
		this(null);
	}

	public AbstractPushMenuItem(HotKeyGroup accelerator)
	{
		super(MIS_PUSH, accelerator);
	}
}
