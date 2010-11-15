package shell.temp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import cop.swt.extensions.ColorExtension;

public class Snippet98
{
	static int pageNum = 0;
	static Composite pageComposite;
	public static int FLEX_STYLE = SWT.BORDER | SWT.RESIZE;

	public static void main(String args[])
	{
		new Snippet98().createPartControl();
	}

	private void createPartControl()
	{
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		shell.setBackground(ColorExtension.GREEN);

		FlexBoard flex = new FlexBoard(shell, FLEX_STYLE, "Main");
		flex.setLayout(new GridLayout());
		flex.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		shell.open();

		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}

class FlexBoard extends CWidget
{
	private int num = 1;

	private FlexibleLayout marketLayout;

	private Composite marketComposite;
	private static Color[] colors = { ColorExtension.CYAN, ColorExtension.MAGENTA, ColorExtension.YELLOW };
	private static int col = 0;

	public FlexBoard(Composite parent, int style, String str)
	{
		super(parent, style, str);

		createControlPart(this);
		createMarketPart(this);
		
		setVisible(true);
	}

	@Override
	protected void checkSubclass()
	{}

	private void createControlPart(Composite parent)
	{
		parent = new Composite(parent, SWT.NONE);

		parent.setLayout(new GridLayout(10, false));
		parent.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		parent.setBackground(ColorExtension.WHITE);

		final Button addMarketButton = new Button(parent, SWT.PUSH);
		addMarketButton.setText("Add market");

		addMarketButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				addMarket("Market " + num++);
			}
		});

		final Button addButtonButton = new Button(parent, SWT.PUSH);
		addButtonButton.setText("Add button");

		addButtonButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				addButton("Button " + num++);
			}
		});

		final Button addFlexButton = new Button(parent, SWT.PUSH);
		addFlexButton.setText("Add flex");

		addFlexButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				addFlex();
			}
		});

		final Button freeButton = new Button(parent, SWT.CHECK);
		freeButton.setText("Free");
		freeButton.setBackground(parent.getBackground());
		// freeButton.setEnabled(false);

		freeButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				if(marketLayout != null)
				{
//					marketLayout.setFree(freeButton.getSelection());
//					addMarketButton.setEnabled(!freeButton.getSelection());
//					addButtonButton.setEnabled(!freeButton.getSelection());
//					addFlexButton.setEnabled(!freeButton.getSelection());
				}
			}
		});

		Button storeButton = new Button(parent, SWT.PUSH);
		storeButton.setText("Store");

		storeButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				if(marketLayout != null)
					marketLayout.makeScreenshot();
			}
		});

		Button restoreButton = new Button(parent, SWT.PUSH);
		restoreButton.setText("Restore");

		restoreButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				if(marketLayout != null)
					marketLayout.restoreScreenshot();
			}
		});
	}

	private void createMarketPart(Composite parent)
	{
		marketComposite = new Composite(parent, SWT.NONE);
		marketComposite.setLayout(marketLayout = new FlexibleLayout());
		marketComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		marketComposite.setBackground(ColorExtension.DARK_YELLOW);
		marketComposite.setMenu(FlexibleLayout.createMenu(marketComposite, marketLayout));
	}

	private void addMarket(String str)
	{
		CWidget widget = new CWidget(marketComposite, SWT.NO_TRIM, str);
		widget.setLayout(new GridLayout());
		widget.setLayoutData(new GridData());
		
		marketComposite.setRedraw(false);
		marketComposite.layout(true, true);
		marketComposite.setRedraw(true);
		
		marketComposite.addControlListener(new ControlAdapter()
		{
			@Override
			public void controlResized(ControlEvent e)
			{
				System.out.println("main resize: " + marketComposite.getBounds());
			}
			
			@Override
			public void controlMoved(ControlEvent e)
			{
				System.out.println("main move: " + marketComposite.getBounds());
			}
		});
		
		widget.addControlListener(new ControlAdapter()
		{
			@Override
			public void controlResized(ControlEvent e)
			{
				System.out.println("widget resize: " + marketComposite.getBounds());
			}
			
			@Override
			public void controlMoved(ControlEvent e)
			{
				System.out.println("widget move: " + marketComposite.getBounds());
			}
		});
		
		//widget.layout(true, true);
		//marketComposite.layout();
		// marketLayout.store();
		//widget.redraw();
	}

	private void addButton(String str)
	{
		Button button = new Button(marketComposite, SWT.NONE);
		button.setText(str);
		button.setLayoutData(new GridData());
		marketComposite.setRedraw(false);
		marketComposite.layout(true, true);
		marketComposite.setRedraw(true);
		// marketLayout.store();
	}

	private void addFlex()
	{
		FlexBoard flex = new FlexBoard(marketComposite, Snippet98.FLEX_STYLE, "Child");
		flex.setLayoutData(new GridData());
		flex.setLayout(new FlexibleLayout());

		flex.setBackground(colors[col++]);
		if(col >= colors.length)
			col = 0;

		// marketLayout.store();
	}
}
