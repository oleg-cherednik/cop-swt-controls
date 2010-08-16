package cop.swt.widgets.tmp;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

//import com.marathon.calendar.gui.Activator;

public class FoodLabelProvider implements ILabelProvider
{
	/**
	 * ListViewers don't support images
	 * 
	 * @param arg0 the element
	 * @return Image
	 */
	@Override
    public Image getImage(Object arg0)
	{
		return null;
	}

	/**
	 * Gets the text for an element
	 * 
	 * @param arg0 the element
	 * @return String
	 */
	@Override
    public String getText(Object arg0)
	{
		return ((Food)arg0).getName();
	}

	/**
	 * Adds a listener
	 * 
	 * @param arg0 the listener
	 */
	@Override
    public void addListener(ILabelProviderListener arg0)
	{
	// Throw it away
	}

	/**
	 * Disposes any resources
	 */
	@Override
    public void dispose()
	{
	// Nothing to dispose
	}

	/**
	 * Returns whether changing the specified property for the specified element affect the label
	 * 
	 * @param arg0 the element
	 * @param arg1 the property
	 * @return boolean
	 */
	@Override
    public boolean isLabelProperty(Object arg0, String arg1)
	{
		return false;
	}

	/**
	 * Removes a listener
	 * 
	 * @param arg0 the listener
	 */
	@Override
    public void removeListener(ILabelProviderListener arg0)
	{
	// Ignore
	}
}
