package plugin.cop.swt.example.examples;

import static cop.swt.widgets.keys.enums.KeyEnum.KEY_ESC;
import static cop.swt.widgets.keys.enums.KeyEnum.parseKeyEnum;
import static org.eclipse.swt.SWT.BUTTON1;
import static org.eclipse.swt.SWT.MouseDown;
import static org.eclipse.swt.SWT.MouseMove;
import static org.eclipse.swt.SWT.NO_TRIM;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import plugin.cop.swt.example.Activator;

import cop.swt.extensions.ColorExtension;

public class WelcomeExample implements IExample
{
	private static Image image;
	private Image image1;
	private static ImageData imageData;
	private Display display;
	private Shell shell;
	private static Region region;
	private Shell shell1;
	private FormToolkit toolkit;
	
	private Label loginLabel;
	private Label passwordLabel;

	static
	{
		image = Activator.getImageDescriptor("icons//title3.gif").createImage();
		imageData = image.getImageData();
		region = createRegion(imageData);
	}
	
	static int[] circle(int r, int offsetX, int offsetY)
	{
		int[] polygon = new int[8 * r + 4];
		// x^2 + y^2 = r^2
		for(int i = 0; i < 2 * r + 1; i++)
		{
			int x = i - r;
			int y = (int)Math.sqrt(r * r - x * x);
			polygon[2 * i] = offsetX + x;
			polygon[2 * i + 1] = offsetY + y;
			polygon[8 * r - 2 * i - 2] = offsetX + x;
			polygon[8 * r - 2 * i - 1] = offsetY - y;
		}
		return polygon;
	}

	@Override
	public void run(final Composite parent)
	{
		display = parent.getDisplay();
		toolkit = new FormToolkit(display);

		//createButtonShell();
		createSplashShell();
		//createLogonShell();

		// shell.addDisposeListener(new DisposeListener()
		// {
		// public void widgetDisposed(DisposeEvent event)
		// {
		// region.dispose();
		// }
		//
		// });

		shell.open();
		//shell1.open();
		
		int i = 0;

		while(!shell.isDisposed())
		{
			i++;
			
			if(i++ >= 100)
			{
				Color background = shell1.getBackground();
				Color newColor;
				
				if(background.equals(ColorExtension.RED))
					newColor = ColorExtension.DARK_BLUE;
				else
					newColor = ColorExtension.RED;
				
				
				shell1.setBackground(newColor);
				loginLabel.setBackground(newColor);
				passwordLabel.setBackground(newColor);
				
				i = 0;
			}
			
			if(!display.readAndDispatch())
				display.sleep();
		}
		region.dispose();
		display.dispose();

	}

	private void createSplashShell()
	{
		shell = new Shell(display, NO_TRIM);
		region = createRegion(imageData);

		shell.setRegion(region);
		shell.addListener(MouseDown, moveOnMouse);
		shell.addListener(MouseMove, moveOnMouse);
		shell.addKeyListener(exitOnEscape);
		shell.addPaintListener(onPaint);

		shell.setSize(imageData.width, imageData.height);
	}

	private void createLogonShell()
	{
		shell1 = new Shell(shell, SWT.NO_TRIM);
		
		shell1.setLayout(new GridLayout(4, false));
		
		shell1.setBackground(ColorExtension.DARK_BLUE);
		
		shell1.addListener(MouseDown, moveOnMouse);
		shell1.addListener(MouseMove, moveOnMouse);
		
		loginLabel = new Label(shell1, SWT.NONE);
		Text loginText = new Text(shell1, SWT.CENTER);
		loginLabel.setText("login");
		loginLabel.setBackground(shell1.getBackground());
		loginLabel.setForeground(ColorExtension.WHITE);
		
		passwordLabel = new Label(shell1, SWT.NONE);
		Text passwordText = new Text(shell1, SWT.PASSWORD | SWT.CENTER);
		passwordLabel.setText("password");
		passwordLabel.setBackground(shell1.getBackground());
		passwordLabel.setForeground(ColorExtension.WHITE);

		shell1.pack();
	}

	public static Region createRegion(ImageData imageData)
	{
		Region region = new Region();

		if(imageData.alphaData != null)
		{
			for(int y = 0; y < imageData.height; y++)
				for(int x = 0; x < imageData.width; x++)
					if(imageData.getAlpha(x, y) == 255)
						region.add(new Rectangle(x, y, 1, 1));
		}
		else
		{
			ImageData mask = imageData.getTransparencyMask();

			for(int y = 0; y < mask.height; y++)
				for(int x = 0; x < mask.width; x++)
					if(mask.getPixel(x, y) != 0)
						region.add(new Rectangle(x, y, 1, 1));
		}

		return region;
	}

	private KeyListener exitOnEscape = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if(parseKeyEnum(e.keyCode) == KEY_ESC)
				shell.dispose();
		}
	};

	private PaintListener onPaint = new PaintListener()
	{
		@Override
		public void paintControl(PaintEvent e)
		{
			e.gc.drawImage(image, 0, 0);
		}
	};

	private PaintListener onPaint1 = new PaintListener()
	{
		@Override
		public void paintControl(PaintEvent e)
		{
			e.gc.drawImage(image1, 0, 0);
		}
	};

	private Listener moveOnMouse = new Listener()
	{
		private int startX = 0;
		private int startY = 0;

		@Override
        public void handleEvent(Event e)
		{
			if(e.type == MouseDown && e.button == 1)
			{
				startX = e.x;
				startY = e.y;
			}
			else if(e.type == MouseMove && (e.stateMask & BUTTON1) != 0)
			{
				Point point = shell.toDisplay(e.x, e.y);

				point.x -= startX;
				point.y -= startY;

				((Control)e.widget).setLocation(point);
			}
		}
	};
	
	private void createButtonShell()
	{
		shell = new Shell(display, SWT.NO_TRIM);
		shell.setText("Regions on a Control");
		shell.setLayout(new FillLayout());
		shell.setBackground(display.getSystemColor(SWT.COLOR_DARK_RED));

		Text b2 = new Text(shell, SWT.PUSH);
		b2.setText("Button with Regions");

		// define a region that looks like a circle with two holes in ot
//		Region region = new Region();
//		region.add(circle(67, 87, 77));
//		region.subtract(circle(20, 87, 47));
//		region.subtract(circle(20, 87, 113));
		//shell.setRegion(region);

		// define the shape of the button using setRegion
		b2.setRegion(region);
		b2.setLocation(100, 50);
		
		b2.addListener(MouseDown, moveOnMouse);
		b2.addListener(MouseMove, moveOnMouse);
		b2.addKeyListener(exitOnEscape);

//		b2.addListener(SWT.Selection, new Listener()
//		{
//			public void handleEvent(Event e)
//			{
//				shell.close();
//			}
//		});

		shell.setSize(imageData.width, imageData.height);
		//shell.setSize(200, 200);
		
		Font font;
	}

}
