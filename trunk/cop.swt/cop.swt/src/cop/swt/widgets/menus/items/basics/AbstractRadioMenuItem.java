package cop.swt.widgets.menus.items.basics;

import static cop.swt.widgets.menus.enums.MenuItemStyleEnum.MIS_RADIO;

import cop.swt.widgets.keys.HotKeyGroup;

public abstract class AbstractRadioMenuItem extends AbstractSelectionMenuItem
{
	public AbstractRadioMenuItem()
	{
		this(null);
	}

	public AbstractRadioMenuItem(HotKeyGroup accelerator)
	{
		super(MIS_RADIO, accelerator);
	}
}
