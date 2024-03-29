package cop.swt.widgets.menu.items;

import static cop.swt.widgets.keys.HotKey.EMPTY_HOT_KEY;
import static cop.swt.widgets.menu.enums.MenuItemEnum.MI_SEPARATOR;
import static cop.swt.widgets.menu.enums.MenuItemStyleEnum.MIS_SEPARATOR;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import cop.swt.widgets.keys.HotKey;
import cop.swt.widgets.menu.items.basics.AbstractMenuItem;

public class SeparatorMenuItem extends AbstractMenuItem
{
	public SeparatorMenuItem()
	{
		super(MIS_SEPARATOR, MI_SEPARATOR);
	}

	/*
	 * ICreateMenu
	 */

	@Override
	public MenuItem create(Menu parent)
	{
		Assert.isNotNull(parent);

		return new MenuItem(parent, getStyle().getSwtStyle());
	}

	/*
	 * MenuItem
	 */

	@Override
	@Deprecated
	public String getTitle()
	{
		return "";
	}

	@Override
	@Deprecated
	public HotKey getAccelerator()
	{
		return EMPTY_HOT_KEY;
	}

	@Override
	@Deprecated
	public Image getImage()
	{
		return null;
	}
}
