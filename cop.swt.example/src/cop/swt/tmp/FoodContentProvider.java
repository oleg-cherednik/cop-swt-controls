package cop.swt.tmp;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class FoodContentProvider implements IStructuredContentProvider
{
	/**
	 * Gets the food items for the list
	 * 
	 * @param arg0
	 *            the data model
	 * @return Object[]
	 */
	@Override
    public Object[] getElements(Object arg0)
	{
		return ((GroceryList)arg0).getFoods().toArray();
	}

	/**
	 * Disposes any created resources
	 */
	@Override
    public void dispose()
	{
	// Do nothing
	}

	/**
	 * Called when the input changes
	 * 
	 * @param arg0
	 *            the viewer
	 * @param arg1
	 *            the old input
	 * @param arg2
	 *            the new input
	 */
	@Override
    public void inputChanged(Viewer arg0, Object arg1, Object arg2)
	{
	// Do nothing
	}
}