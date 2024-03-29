package cop.swt.tmp;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
	 * @param shell the shell
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
	 * @param parent the main window
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
		lv.setContentProvider(new FoodContentProvider());
		lv.setLabelProvider(new FoodLabelProvider());
		lv.setInput(new GroceryList());

		lv.add(new Food("Broccoli11", true));

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
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		new FoodList().run();
	}
}
