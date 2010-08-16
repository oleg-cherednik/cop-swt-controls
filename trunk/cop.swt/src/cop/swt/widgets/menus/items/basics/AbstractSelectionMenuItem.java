package cop.swt.widgets.menus.items.basics;

import static cop.common.extensions.CommonExtension.isNotNull;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import cop.swt.widgets.keys.HotKeyGroup;
import cop.swt.widgets.menus.enums.MenuItemStyleEnum;
import cop.swt.widgets.menus.interfaces.PropertyProvider;

public abstract class AbstractSelectionMenuItem extends AbstractMenuItem
{
	private PropertyProvider<Boolean> selectionProvider;

	protected AbstractSelectionMenuItem(MenuItemStyleEnum style, HotKeyGroup accelerator)
	{
		super(style, accelerator);
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
