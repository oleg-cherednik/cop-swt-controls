package shell.temp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cop.swt.extensions.ColorExtension;
import cop.swt.widgets.keys.enums.KeyEnum;

public class CWidget extends Decorations
{
	private boolean active;
	private Rectangle bounds = new Rectangle(0, 0, 0, 0);
	private String name;

	int x, y;

	private final Color COLOR_ACTIVE = ColorExtension.RED;
	private Color backgroundColor = ColorExtension.BLUE;

	public CWidget(Composite parent, int style, String str)
	{
		super(parent, style);
		
		name = str;

		//FlexibleData layoutData = new FlexibleData();

//		layoutData.horizontalIndent = 5;
//		layoutData.verticalIndent = 5;

		//setLayoutData(layoutData);

		Button button = new Button(this, SWT.NONE);
		button.setText(str);

		// setBounds(0, 0, 10, 10);
		addListeners();
		
		setVisible(true);

		setBackground(backgroundColor);
		layout();
	}

	// public boolean isActive()
	// {
	// return active;
	// }

	public void setActive(boolean active)
	{
		if(this.active ^ active)
			return;

		if(active)
			activate();
		else
			deactivate();
	}
	
//	private Event getEvent()
//	{
//		Event event = new Event();
//		Rectangle bounds = getBounds();
//
//		event.x = bounds.x;
//		event.y = bounds.y;
//		event.width = bounds.width;
//		event.height = bounds.height;
//		event.time = (int)System.currentTimeMillis();
//		event.widget = this;
//
//		return event;
//	}

	private void activate()
	{
		setBackground(COLOR_ACTIVE);
		active = true;
		
		//notifyListeners(SWT.FocusIn, getEvent());
		setFocus();
		moveAbove(null);
	}

	private void deactivate()
	{
		//super.setBackground(backgroundColor);
		active = false;
		super.setBackground(backgroundColor);
		//notifyListeners(SWT.FocusOut, getEvent());
	}

	@Override
	public void setBounds(int x, int y, int width, int height)
	{
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;

		super.setBounds(x, y, width, height);
	}

	@Override
	protected void checkSubclass()
	{}

	private void addListeners()
	{
		addListener(SWT.MouseDown, listener);
		addListener(SWT.MouseUp, listener);
		addListener(SWT.MouseMove, listener);
	}
	
	@Override
    public String toString()
    {
		return "CWidget [name=" + name + ", active=" + active + "]";
    }

//	/*
//	 * Control
//	 */
//
//	@Override
//	public void setBackground(Color color)
//	{
//		backgroundColor = color;
//		super.setBackground(color);
//	}

	/*
	 * Listener
	 */

	private Listener listener = new Listener()
	{
		private Point mouseOffs = new Point(0, 0);

		@Override
		public void handleEvent(Event event)
		{
			//System.out.println("handleEvent");
//			if(event.stateMask != KeyEnum.KEY_CTRL.getKeyCode())
//				return;
			
			switch(event.type)
			{
			case SWT.MouseDown:
				if(event.stateMask != KeyEnum.KEY_CTRL.getKeyCode())
					break;
				
				mouseOffs.x = event.x;
				mouseOffs.y = event.y;

				activate();
				break;
			case SWT.MouseMove:
				if(!active)
					break;

				Rectangle bounds1 = getBounds();

				int posX = bounds1.x + event.x - mouseOffs.x;
				int posY = bounds1.y + event.y - mouseOffs.y;

				setLocation(posX, posY);

				break;
			case SWT.MouseUp:
				if(active)
					deactivate();
				
				break;
			}
		}
	};
}
