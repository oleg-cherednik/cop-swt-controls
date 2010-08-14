package cop.swt.widgets.menus.interfaces;

import java.util.List;

public interface IMenuBuilder
{
	/**
	 * @return empty list if no items found
	 */
	List<IMenuItem> getMenuItems();
}
