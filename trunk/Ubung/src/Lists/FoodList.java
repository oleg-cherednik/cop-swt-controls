package Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This class demonstrates ListViewer.
 */
public class FoodList extends ApplicationWindow
{
	// The healthy filter
	private HealthyFilter filter = new HealthyFilter();

	/**
	 * FoodList constructor
	 */
	public FoodList()
	{
		super(null);
	}

	/**
	 * Runs the application
	 */
	public void run()
	{
		// Don't return from open() until window closes
		setBlockOnOpen(true);

		// Open the main window
		open();

		// Dispose the display
		Display.getCurrent().dispose();
	}

	/**
	 * Configures the shell
	 * 
	 * @param shell
	 *            the shell
	 */
	@Override
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setText("Food List");
	}

	/**
	 * Creates the main window's contents
	 * 
	 * @param parent
	 *            the main window
	 * @return Control
	 */
	@Override
	protected Control createContents(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		// Add a checkbox to toggle filter
		Button filterHealthy = new Button(composite, SWT.CHECK);
		filterHealthy.setText("&Show only healthy");

		final ListViewer lv = new ListViewer(composite);
		lv.addFilter(filter);
		lv.setContentProvider(new FoodContentProvider());
		lv.setLabelProvider(new FoodLabelProvider());
		//lv.setInput(new GroceryList());
		
		lv.add(new Food("Broccoli", true));
		lv.add(new Food("Bundt Cake", false));
		lv.add(new Food("Cabbage", true));
		lv.add(new Food("Candy Canes", false));
		lv.add(new Food("Eggs", true));
		lv.add(new Food("Potato Chips", false));
		lv.add(new Food("Milk", true));
		lv.add(new Food("Soda", false));
		lv.add(new Food("Chicken", true));
		lv.add(new Food("Cinnamon Rolls", false));

		// When user checks the checkbox, toggle the filter
		filterHealthy.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				if(((Button)event.widget).getSelection())
					lv.addFilter(filter);
				else
					lv.removeFilter(filter);
			}
		});

		parent.pack();
		return composite;
	}

	/**
	 * The application entry point
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args)
	{
		new FoodList().run();
	}
}

/**
 * This class filters only healthy items from the grocery list
 */

class HealthyFilter extends ViewerFilter
{
	/**
	 * Returns whether the specified element passes this filter
	 * 
	 * @param arg0
	 *            the viewer
	 * @param arg1
	 *            the parent element
	 * @param arg2
	 *            the element
	 * @return boolean
	 */
	@Override
	public boolean select(Viewer arg0, Object arg1, Object arg2)
	{
		return ((Food)arg2).isHealthy();
	}
}

/**
 * This class represents a type of food
 */

class Food
{
	// The name of the food
	private String name;

	// Is it healthy?
	private boolean healthy;

	/**
	 * Food constructor
	 * 
	 * @param name
	 *            the name
	 * @param healthy
	 *            whether or not it's healthy
	 */
	public Food(String name, boolean healthy)
	{
		this.name = name;
		this.healthy = healthy;
	}

	/**
	 * Gets whether this is healthy
	 * 
	 * @return boolean
	 */
	public boolean isHealthy()
	{
		return healthy;
	}

	/**
	 * Gets the name
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	@Override
    public String toString()
    {
	    return name + " - " + healthy;
    }
	
	
}

/**
 * This class provides the labels for the FoodList application
 */

class FoodLabelProvider implements ILabelProvider
{

	/**
	 * ListViewers don't support images
	 * 
	 * @param arg0
	 *            the element
	 * @return Image
	 */
	public Image getImage(Object arg0)
	{
		System.out.println("getImage()");
		return null;
	}

	/**
	 * Gets the text for an element
	 * 
	 * @param arg0
	 *            the element
	 * @return String
	 */
	public String getText(Object arg0)
	{
		return ((Food)arg0).getName();
	}

	/**
	 * Adds a listener
	 * 
	 * @param arg0
	 *            the listener
	 */
	public void addListener(ILabelProviderListener arg0)
	{
	// Throw it away
	}

	/**
	 * Disposes any resources
	 */
	public void dispose()
	{
	// Nothing to dispose
	}

	/**
	 * Returns whether changing the specified property for the specified element affect the label
	 * 
	 * @param arg0
	 *            the element
	 * @param arg1
	 *            the property
	 * @return boolean
	 */
	public boolean isLabelProperty(Object arg0, String arg1)
	{
		return false;
	}

	/**
	 * Removes a listener
	 * 
	 * @param arg0
	 *            the listener
	 */
	public void removeListener(ILabelProviderListener arg0)
	{
	// Ignore
	}
}

/**
 * This class contains all the foods on the "grocery list"
 */

class GroceryList
{
	// Holds the foods
	private List<Food> foods;

	/**
	 * Constructs a grocery list
	 */
	public GroceryList()
	{
		foods = new ArrayList<Food>();

		// Add some foods
		foods.add(new Food("Broccoli", true));
		foods.add(new Food("Bundt Cake", false));
		foods.add(new Food("Cabbage", true));
		foods.add(new Food("Candy Canes", false));
		foods.add(new Food("Eggs", true));
		foods.add(new Food("Potato Chips", false));
		foods.add(new Food("Milk", true));
		foods.add(new Food("Soda", false));
		foods.add(new Food("Chicken", true));
		foods.add(new Food("Cinnamon Rolls", false));
	}

	/**
	 * Returns the foods in this grocery list
	 * 
	 * @return List
	 */
	public List<Food> getFoods()
	{
		return Collections.unmodifiableList(foods);
	}
}

/**
 * This class provides the content for the FoodList application
 */

class FoodContentProvider implements IStructuredContentProvider
{
	/**
	 * Gets the food items for the list
	 * 
	 * @param arg0
	 *            the data model
	 * @return Object[]
	 */
	public Object[] getElements(Object arg0)
	{
		System.out.println("getElements()");
		return ((GroceryList)arg0).getFoods().toArray();
	}

	/**
	 * Disposes any created resources
	 */
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
	public void inputChanged(Viewer arg0, Object arg1, Object arg2)
	{
	// Do nothing
	}
}
