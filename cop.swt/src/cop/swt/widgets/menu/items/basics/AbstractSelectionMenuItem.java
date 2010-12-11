package cop.swt.widgets.menu.items.basics;

import static cop.common.extensions.CommonExtension.isNotNull;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import cop.swt.widgets.keys.HotKey;
import cop.swt.widgets.menu.enums.MenuItemEnum;
import cop.swt.widgets.menu.enums.MenuItemStyleEnum;
import cop.swt.widgets.menu.interfaces.PropertyProvider;

public abstract class AbstractSelectionMenuItem extends AbstractMenuItem
{
	private PropertyProvider<Boolean> selectionProvider;

	protected AbstractSelectionMenuItem(MenuItemStyleEnum style, MenuItemEnum key, HotKey accelerator)
	{
		super(style, key, accelerator);
	}

	public void setSelectionProvider(PropertyProvider<Boolean> selectionProvider)
	{
		this.selectionProvider = selectionProvider;
	}

	/*
	 * ICreateMenu
	 */

	@Override
	public MenuItem create(Menu parent)
	{
		MenuItem item = super.create(parent);

		if(isNotNull(selectionProvider))
			item.setSelection(selectionProvider.getProperty());

		return item;
	}
}
