package cop.swt.widgets.menu.items.basics;

import static cop.extensions.CommonExt.isNotNull;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import cop.swt.widgets.keys.HotKey;
import cop.swt.widgets.menu.enums.MenuItemStyleEnum;
import cop.swt.widgets.menu.interfaces.MenuItemKey;
import cop.swt.widgets.menu.interfaces.PropertyProvider;

public abstract class AbstractSelectionMenuItem extends AbstractMenuItem
{
	private PropertyProvider<Boolean> selectionProvider;

	protected AbstractSelectionMenuItem(MenuItemStyleEnum style, MenuItemKey key, HotKey accelerator)
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
