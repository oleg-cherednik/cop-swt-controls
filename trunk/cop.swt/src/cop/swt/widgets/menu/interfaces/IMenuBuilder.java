package cop.swt.widgets.menu.interfaces;

import java.util.List;

public interface IMenuBuilder
{
	/**
	 * @return empty list if no items found
	 */
	List<IMenuItem> getMenuItems();
}
