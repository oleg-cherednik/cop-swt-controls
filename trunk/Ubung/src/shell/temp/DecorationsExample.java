package shell.temp;

//Send questions, comments, bug reports, etc. to the authors:

//Rob Warner (rwarner@interspatial.com)
//Robert Harris (rbrt_harris@yahoo.com)

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import cop.swt.extensions.ColorExtension;

/**
 * This application shows the various styles of Decorations
 */
public class DecorationsExample
{
	private int num = 1;
	
	private GridLayout marketLayout;

	private List<Decorations> widgets = new ArrayList<Decorations>();
	private Composite marketComposite;
	
	/**
	 * Runs the application
	 */
	public void run()
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Decorations Example");
		shell.setLayout(new GridLayout());
		
		createControlPart(shell);
		createMarketPart(shell);
		createContents(marketComposite);
		
		shell.open();
		
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		display.dispose();
	}
	
	private void createControlPart(Composite composite)
	{
		composite = new Composite(composite, SWT.NONE);
		// There are nine distinct styles, so create
		// a 3x3 grid
		composite.setLayout(new GridLayout(4, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		composite.setBackground(ColorExtension.WHITE);
		
		Button addMarketButton = new Button(composite, SWT.PUSH);
		addMarketButton.setText("Add market");

		addMarketButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				addWidget("Market " + num++);
			}
		});
		
		final Button freeButton = new Button(composite, SWT.CHECK);
		freeButton.setText("Free");
		freeButton.setBackground(composite.getBackground());
		
		freeButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
//				if(marketLayout != null)
//					marketLayout.setFree(freeButton.getSelection());
			}
		});
		
		Button storeButton = new Button(composite, SWT.PUSH);
		storeButton.setText("Store");
		
		storeButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
//				if(marketLayout != null)
//					marketLayout.store();
			}
		});
		
		Button restoreButton = new Button(composite, SWT.PUSH);
		restoreButton.setText("Restore");
		
		restoreButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
//				if(marketLayout != null)
//					marketLayout.restore();
			}
		});
	}
	
	private void createMarketPart(Composite parent)
	{
		marketComposite = new Composite(parent, SWT.NONE);
		// There are nine distinct styles, so create
		// a 3x3 grid
		marketLayout = new GridLayout(3, true);
		marketComposite.setLayout(marketLayout);
		marketComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		marketComposite.setBackground(ColorExtension.MAGENTA);
	}

	/**
	 * Creates the various Decorations
	 * 
	 * @param composite the parent composite
	 */
	public void createContents(Composite composite)
	{
		//composite = new Composite(composite, SWT.NONE);
		// There are nine distinct styles, so create
		// a 3x3 grid
//		composite.setLayout(new FlexibleLayout(3, true));
//		composite.setLayoutData(new FlexibleData(SWT.FILL, SWT.FILL, true, true));
//		composite.setBackground(ColorExtension.WHITE);

		// The SWT.BORDER style
		Decorations d = new Decorations(composite, SWT.BORDER | SWT.RESIZE);
		d.setLayoutData(new GridData(GridData.FILL_BOTH));
		d.setLayout(new GridLayout());
		new Label(d, SWT.CENTER).setText("SWT.BORDER");

		// The SWT.CLOSE style
		d = new Decorations(composite, SWT.CLOSE);
		d.setLayoutData(new GridData(GridData.FILL_BOTH));
		d.setLayout(new FillLayout());
		new Label(d, SWT.CENTER).setText("SWT.CLOSE");

		// The SWT.MIN style
		d = new Decorations(composite, SWT.MIN);
		d.setLayoutData(new GridData(GridData.FILL_BOTH));
		d.setLayout(new GridLayout());
		new Label(d, SWT.CENTER).setText("SWT.MIN");
		
		
		d.addListener(SWT.Close, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				System.out.println("handleEvent");
				event.doit = false;
			
			}
		});  
		

		// The SWT.MAX style
		d = new Decorations(composite, SWT.MAX);
		d.setLayoutData(new GridData(GridData.FILL_BOTH));
		d.setLayout(new GridLayout());
		new Label(d, SWT.CENTER).setText("SWT.MAX");

		// The SWT.NO_TRIM style
		d = new Decorations(composite, SWT.NO_TRIM);
		d.setLayoutData(new GridData(GridData.FILL_BOTH));
		d.setLayout(new GridLayout());
		new Label(d, SWT.CENTER).setText("SWT.NO_TRIM");

		// The SWT.RESIZE style
		d = new Decorations(composite, SWT.RESIZE);
		d.setLayoutData(new GridData(GridData.FILL_BOTH));
		d.setLayout(new GridLayout());
		new Label(d, SWT.CENTER).setText("SWT.RESIZE");

		// The SWT.TITLE style
		d = new Decorations(composite, SWT.TITLE);
		d.setLayoutData(new GridData(GridData.FILL_BOTH));
		d.setLayout(new GridLayout());
		new Label(d, SWT.CENTER).setText("SWT.TITLE");

		// The SWT.ON_TOP style
		d = new Decorations(composite, SWT.ON_TOP);
		d.setLayoutData(new GridData(GridData.FILL_BOTH));
		d.setLayout(new GridLayout());
		new Label(d, SWT.CENTER).setText("SWT.ON_TOP");

		// The SWT.TOOL style
		d = new Decorations(composite, SWT.TOOL);
		d.setLayoutData(new GridData(GridData.FILL_BOTH));
		d.setLayout(new GridLayout());
		new Label(d, SWT.CENTER).setText("SWT.TOOL");
	}
	
	private void addWidget(String str)
	{
		Decorations d = new Decorations(marketComposite, SWT.BORDER | SWT.RESIZE);
		d.setLayoutData(new GridData(GridData.FILL_BOTH));
		d.setLayout(new GridLayout());
		new Label(d, SWT.CENTER).setText("SWT.BORDER");
		
		d.setVisible(true);
		
		marketComposite.redraw();
		marketComposite.layout(true, true);
	}

	/**
	 * The entry point for the application
	 * 
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		new DecorationsExample().run();
	}
}
