package mouse;

/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

/*
 * Composite example snippet: intercept mouse events (drag a button with the mouse)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import cop.swt.extensions.ColorExtension;

public class DragButtonMouseEvents1
{
	public static void main(String[] args)
	{
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setBackground(ColorExtension.MAGENTA);
		
		final CWidget composite = new CWidget(shell, SWT.NONE);
		
		//final Composite composite = new Composite(shell, SWT.NONE);
		//composite.setEnabled(false);
		//composite.setLayout(new FillLayout());
		composite.setBackground(ColorExtension.BLUE);
		
		
//		Button button1 = new Button(composite, SWT.PUSH);
//		button1.setText("OK");
//
//		Button button2 = new Button(composite, SWT.PUSH);
//		button2.setText("Cancel");
		
		
		composite.pack();
		composite.setLocation(10, 10);
		final Point[] offset = new Point[1];
		
		
		Listener listener = new Listener()
		{
			public void handleEvent(Event event)
			{
				switch(event.type)
				{
				case SWT.MouseDown:
					System.out.println("MouseDown");
					Rectangle rect = composite.getBounds();
					if(rect.contains(event.x, event.y))
					{
						Point pt1 = composite.toDisplay(0, 0);
						Point pt2 = shell.toDisplay(event.x, event.y);
						offset[0] = new Point(pt2.x - pt1.x, pt2.y - pt1.y);
					}
					break;
				case SWT.MouseMove:
					System.out.println("MouseMove");
					if(offset[0] != null)
					{
						Point pt = offset[0];
						composite.setLocation(event.x - pt.x, event.y - pt.y);
					}
					break;
				case SWT.MouseUp:
					System.out.println("MouseUp");
					offset[0] = null;
					break;
				}
			}
		};
		composite.addListener(SWT.MouseDown, listener);
		composite.addListener(SWT.MouseUp, listener);
		composite.addListener(SWT.MouseMove, listener);
		shell.setSize(300, 300);
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}

class CWidget extends Composite
{
	private boolean mouseActive;
	private Rectangle bounds = new Rectangle(0, 0, 0, 0);
	
	int x, y;
	
	public CWidget(Shell parent, int style)
	{
		super(parent, style);
		
		setLayout(new GridLayout());
		setLayoutData(new GridData());
		
		Button button = new Button(this, SWT.NONE);
		button.setLayoutData(new GridData());
		
		button.setText("Oleg");
		
		//setBounds(0, 0, 100, 100);
		//addListeners();
	}
	
	@Override
    public void setBounds (int x, int y, int width, int height)
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
		addMouseTrackListener(onMouseTrack);
	}
	
	/*
	 * Listener
	 */
	
	private MouseTrackListener onMouseTrack = new MouseTrackAdapter()
	{
		@Override
		public void mouseExit(MouseEvent e)
		{
			removeMouseMoveListener(onMouseMove);
			removeMouseListener(onMouseButton);
		}
		
		@Override
		public void mouseEnter(MouseEvent e)
		{
			addMouseMoveListener(onMouseMove);
			addMouseListener(onMouseButton);
		}
	};
	
	private MouseMoveListener onMouseMove = new MouseMoveListener()
	{
		@Override
		public void mouseMove(MouseEvent e)
		{
			if(!mouseActive)
				return;
			
			System.out.println("mouseMove()");
			
			int offsX = e.x - x;
			int offsY = e.y - y;
			
			CWidget widget = (CWidget)e.widget;
			Rectangle rect = widget.getBounds();
			
			rect.x += offsX;
			rect.y += offsY;
			
			widget.setBounds(rect);
			
			x = e.x;
			y = e.y;
			
			widget.getParent().layout(true, true);
		}
	};
	
	private MouseListener onMouseButton = new MouseAdapter()
	{
		@Override
		public void mouseUp(MouseEvent e)
		{
			mouseActive = false;
		}

		@Override
		public void mouseDown(MouseEvent e)
		{
			mouseActive = true;
			x = e.x;
			y = e.y;
		}
	};
	
	
}
