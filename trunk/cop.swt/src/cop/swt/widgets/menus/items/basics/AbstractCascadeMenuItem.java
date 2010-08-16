package cop.swt.widgets.menus.items.basics;

import static cop.common.extensions.CommonExtension.isNull;
import static cop.swt.widgets.menus.enums.MenuItemStyleEnum.MIS_CASCADE;

import cop.swt.widgets.keys.HotKeyGroup;

public abstract class AbstractCascadeMenuItem extends AbstractMenuItem
{
	public AbstractCascadeMenuItem()
	{
		this(null);
	}

	public AbstractCascadeMenuItem(HotKeyGroup accelerator)
	{
		super(MIS_CASCADE, accelerator);
	}
	
	/**
	 * AbstractMenuItem
	 */
	
	@Override
	protected boolean isListenerEnabled()
	{
		return true;
	}
}
