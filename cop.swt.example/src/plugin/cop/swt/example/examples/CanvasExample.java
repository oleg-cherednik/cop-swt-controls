package plugin.cop.swt.example.examples;

import static cop.common.extensions.BitExtension.isBitSet;
import static cop.swt.extensions.ColorExtension.BLACK;
import static cop.swt.extensions.ColorExtension.CYAN;
import static cop.swt.extensions.ColorExtension.MAGENTA;
import static cop.swt.extensions.ColorExtension.RED;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import Jama.Matrix;

import cop.swt.widgets.segments.ByteNumber;
import cop.swt.widgets.segments.SegmentContainer;
import cop.swt.widgets.segments.SegmentedIndicator;
import cop.swt.widgets.segments.SignSegment;
import cop.swt.widgets.segments.seven.DigitalNumericSevenSegment;

public class CanvasExample implements IExample
{
	private Display display;
	private Shell shell;
	private GC gc;
	//private List<SegmentedIndicator> digits = new ArrayList<SegmentedIndicator>();
	//private SimpleSegment seg;
	private SegmentContainer<Byte> digits;
	private SegmentedIndicator segment; 
	private SegmentedIndicator plus1;
//	private ISegment plus2;
//	private ISegment plus3;
//	private ISegment plus4;

	private Color[] colors = new Color[] { BLACK, RED, CYAN, MAGENTA };
	int currColor = 0;

	@Override
	public void run(Composite parent)
	{
		double[][] arr1 = {{1,2,3},{4,5,6},{7,8,9}};
		Matrix A = new Matrix(arr1);
		Matrix B = A.transpose();
		double[][] arr2 = B.getArray();
		
		display = parent.getDisplay();
		shell = new Shell(display);
		gc = new GC(shell);

		shell.setLayout(new GridLayout());

		createControls();

		shell.addPaintListener(new PaintListener()
		{
			@Override
            public void paintControl(PaintEvent e)
			{
				work(e);
				//System.out.println("onPaint(): x: " + e.x + ", y: " + e.y + ", width: " + e.width + ", height: "
				//                + e.height);
				// Rectangle clientArea = shell.getClientArea();
				// e.gc.drawLine(0, 0, clientArea.width, clientArea.height);
			}
		});
		shell.setSize(400, 300);

		shell.open();

		// new Thread(new Runnable()
		// {
		// @Override
		// public void run()
		// {
		// try
		// {
		// while(true)
		// {
		// if(num != null)
		// {
		// int i = num.getValue() + 1;
		//
		// if(i > 9)
		// i = 0;
		//
		// num.setValue(i);
		// }
		// Thread.sleep(200);
		// }
		// }
		// catch(Exception e)
		// {
		// e.printStackTrace();
		// }
		// }
		// }).start();

		int i = 0;

		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}

		// gc.dispose();
		display.dispose();
	}

	private void createControls()
	{
		//seg = new SpaceSegment();

		//		shell.setBackground(colors[currColor]);

		//		digits.add(new NumberSegment(shell, 0, 5, 5, 3));
		//		digits.add(new NumberSegment(shell, 0, 16, 5, 2));
		//		digits.add(new NumberSegment(shell, 0, 31, 5, 3));
		//		digits.add(new NumberSegment(shell, 0, 54, 5, 4));

		//		NumericSevenSegment num = new DigitalNumericSevenSegment(shell, 1, 1, 1);
		//		num.setValue(7);

		//		digits.add(num);
		digits = ByteNumber.createByteNumber(shell, SWT.DEFAULT);
		//		digits.add(new DigitalNumericSevenSegment(shell, 16, 5, 2));
		//		digits.add(new DigitalNumericSevenSegment(shell, 27, 5, 2));
		//		digits.add(new DigitalNumericSevenSegment(shell, 38, 5, 2));
		//		digits.add(new DigitalNumericSevenSegment(shell, 4, 49, 5, 2));
		//		digits.add(new DigitalNumericSevenSegment(shell, 5, 60, 5, 2));
		//		digits.add(new DigitalNumericSevenSegment(shell, 6, 71, 5, 2));
		//		digits.add(new DigitalNumericSevenSegment(shell, 7, 82, 5, 2));
		//		digits.add(new DigitalNumericSevenSegment(shell, 8, 93, 5, 2));
		//		digits.add(new DigitalNumericSevenSegment(shell, 9, 104, 5, 2));

		//		num2.setVisible(false);
		//		num4.setVisible(false);

		//		num.build(x, y, 5);
		//		num.setValue(0);
		
		plus1 = new SignSegment();
		segment = new DigitalNumericSevenSegment();
//		plus2 = new MinusSegment();
//		plus3 = new MinusSegment();
//		plus4 = new MinusSegment();
//		
		plus1.setBounds(0, 0, 2);
		segment.setBounds(10, 0, 2);
//		plus2.setBounds(0, 10, 2);
//		plus3.setBounds(0, 25, 3);
//		plus4.setBounds(0, 40, 4);
		
		shell.setLayout(new GridLayout());
		shell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Button changeBackgroundButton = new Button(shell, SWT.NONE);
		changeBackgroundButton.setText("background");
		changeBackgroundButton.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false));
		changeBackgroundButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				currColor++;

				if(currColor >= colors.length)
					currColor = 0;

				shell.setBackground(colors[currColor]);
				//gc.setBackground(colors[currColor]);
			}
		});

		Button visibleButton = new Button(shell, SWT.NONE);
		visibleButton.setText("visible");
		visibleButton.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false));
		visibleButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				digits.setVisible(!digits.isVisible());
				//				for(SegmentedIndicator segment : digits)
				//					segment.setVisible(!segment.isVisible());
			}
		});

		final Spinner spinner = new Spinner(shell, SWT.NONE);
		spinner.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false));
		spinner.setMinimum(Integer.MIN_VALUE);
		spinner.setMaximum(Integer.MAX_VALUE);
		spinner.addModifyListener(new ModifyListener()
		{
			@Override
			public void modifyText(ModifyEvent e)
			{
				digits.setValue((byte)spinner.getSelection());
				//				for(SegmentedIndicator segment : digits)
				//					((NumericSevenSegment)segment).setValue(spinner.getSelection());
			}
		});

		Button moveRightButton = new Button(shell, SWT.ARROW | SWT.RIGHT);
		//moveRightButton.setText("move right");
		moveRightButton.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false));
		moveRightButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				Rectangle rect = digits.getBounds();
				digits.setPosition(rect.x + 10, rect.y);

				//				int i = spinner.getSelection();

				//				if(i < 0 || i > 3)
				//				{
				//					Rectangle rect = digits.getBounds();
				//					digits.setPosition(rect.x + 10, rect.y);
				//					for(SegmentedIndicator segment : digits)
				//					{
				//						if(segment == null)
				//							continue;
				//						
				//						Rectangle rect = segment.getBounds();
				//						segment.setPosition(rect.x + 10, rect.y);
				//					}
			}
			//				else
			//				{
			//					Rectangle rect = digits.getBounds();
			//					digits.setPosition(rect.x + 10, rect.y);
			//					SegmentedIndicator segment = digits.get(i);
			//					
			//					if(segment == null)
			//						return;
			//
			//					Rectangle rect = segment.getBounds();
			//					segment.setPosition(rect.x + 10, rect.y);
			//				}
			//			}
		});

		Button moveLeftButton = new Button(shell, SWT.ARROW | SWT.LEFT);
		//moveRightButton.setText("move left");
		moveLeftButton.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false));
		moveLeftButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				Rectangle rect = digits.getBounds();
				digits.setPosition(rect.x - 10, rect.y);
				//				int i = spinner.getSelection();
				//				
				//				if(i < 0 || i > 3)
				//				{
				//					for(SegmentedIndicator segment : digits)
				//					{
				//						if(segment == null)
				//							continue;
				//						
				//						Rectangle rect = segment.getBounds();
				//						segment.setPosition(rect.x - 10, rect.y);
				//					}
				//				}
				//				else
				//				{
				//					SegmentedIndicator segment = digits.get(i);
				//					
				//					if(segment == null)
				//						return;
				//					
				//					Rectangle rect = segment.getBounds();
				//					segment.setPosition(rect.x - 10, rect.y);
				//				}
			}
		});

		final Button transparentButton = new Button(shell, SWT.CHECK);
		transparentButton.setText("transparent");
		transparentButton.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false));

		transparentButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				digits.setTransparent(transparentButton.getSelection());
				//				for(SegmentedIndicator segment : digits)
				//				{
				//					if(segment == null)
				//						continue;
				//					
				//					segment.setTransparent(transparentButton.getSelection());
				//				}
			}
		});

		final Button rotateRightButton = new Button(shell, SWT.TOGGLE);
		rotateRightButton.setText("rotate right");
		rotateRightButton.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false));

		rotateRightButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				int orientation = digits.getOrientation();

				if(isBitSet(orientation, SWT.UP))
				{
					digits.setOrientation(SWT.RIGHT);
					segment.setOrientation(SWT.RIGHT);
					plus1.setOrientation(SWT.RIGHT);
				}
				else if(isBitSet(orientation, SWT.RIGHT))
				{
					digits.setOrientation(SWT.DOWN);
					segment.setOrientation(SWT.DOWN);
					plus1.setOrientation(SWT.DOWN);
				}
				else if(isBitSet(orientation, SWT.DOWN))
				{
					digits.setOrientation(SWT.LEFT);
					segment.setOrientation(SWT.LEFT);
					plus1.setOrientation(SWT.LEFT);
				}
				else if(isBitSet(orientation, SWT.LEFT))
				{
					digits.setOrientation(SWT.UP);
					segment.setOrientation(SWT.LEFT);
					plus1.setOrientation(SWT.LEFT);
				}

				//				for(SegmentedIndicator segment : digits)
				//				{
				//					if(segment == null)
				//						continue;
				//					
				//					int orientation = segment.getOrientation();
				//					
				//					if(isBitSet(orientation, SWT.UP))
				//						segment.setOrientation(SWT.RIGHT);
				//					else if(isBitSet(orientation, SWT.RIGHT))
				//						segment.setOrientation(SWT.DOWN);
				//					else if(isBitSet(orientation, SWT.DOWN))
				//						segment.setOrientation(SWT.LEFT);
				//					else if(isBitSet(orientation, SWT.LEFT))
				//						segment.setOrientation(SWT.UP);
				//					
				//				}
			}
		});

		final Spinner spinner1 = new Spinner(shell, SWT.NONE);
		spinner1.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false));
		spinner1.setSelection(1);
		spinner1.setMinimum(1);
		spinner1.setMaximum(4);
		spinner1.addModifyListener(new ModifyListener()
		{
			@Override
			public void modifyText(ModifyEvent e)
			{
				digits.setScale(spinner1.getSelection());
				//				for(SegmentedIndicator segment : digits)
				//					((NumericSevenSegment)segment).setScale(spinner1.getSelection());
			}
		});

		spinner1.setSelection(2);

		Button clearButton = new Button(shell, SWT.NONE);
		clearButton.setText("clear");
		clearButton.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false));
		clearButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				digits.clear();
				spinner.setSelection(0);
				//				for(SegmentedIndicator segment : digits)
				//					segment.setVisible(!segment.isVisible());
			}
		});

	}

	private void work(PaintEvent e)
	{
		shell.setBackground(colors[currColor]);

		digits.redraw(e.x, e.y, e.width, e.height);
//		plus1.draw(gc, ColorExtension.WHITE);
//		segment.draw(gc, ColorExtension.WHITE);
		
		System.out.println("segment: " + digits);
//		plus2.draw(gc, ColorExtension.WHITE);
//		plus3.draw(gc, ColorExtension.WHITE);
//		plus4.draw(gc, ColorExtension.WHITE);
		

		//		int[] arr0 = ShapeBasics.createPlus(20, 20, 6);
		//		int[] arr1 = ShapeBasics.createTriangle(20, 30, 10, 3, SWT.DOWN);

		//ISegment s1 = new PlusSegment();
		//		ISegment s2 = new PlusSegment();
		//		ISegment s3 = new PlusSegment();
		//		ISegment s4 = new PlusSegment();

		//s1.setBounds(0, 20, 2);
		//		s2.setBounds(10, 0, 2);
		//		s3.setBounds(25, 0, 3);
		//		s4.setBounds(40, 0, 4);

		//		s1.draw(gc, ColorExtension.WHITE);
		//		s2.draw(gc, ColorExtension.WHITE);
		//		s3.draw(gc, ColorExtension.WHITE);
		//		s4.draw(gc, ColorExtension.WHITE);

		//		gc.setBackground(ColorExtension.WHITE);
		//		gc.fillPolygon(arr0);
		//		gc.setForeground(ColorExtension.WHITE);
		//		gc.drawPolygon(arr1);

		//		for(SegmentedIndicator segment : digits)
		//			segment.redraw(e.x, e.y, e.width, e.height);

		//		gc.setForeground(ColorExtension.RED);
		//		gc.drawRectangle(10, 10, 15, 4);
		//		gc.drawRectangle(30, 10, 4, 15);
		//		gc.drawRectangle(10, 30, 15, 4);
		//		gc.drawRectangle(30, 30, 15, 4);
		//		gc.drawRectangle(10, 50, 4, 15);
		//		gc.drawRectangle(30, 50, 4, 15);
		//		
		//		int[] arr1 = ShapeBasics.createRhombus(10, 10, 16, 5, SWT.HORIZONTAL);
		//		int[] arr2 = ShapeBasics.createRhombus(30, 10, 5, 16, SWT.VERTICAL);
		//		int[] arr3 = ShapeBasics.createTriangle(10, 30, 16, 5, SWT.HORIZONTAL | SWT.UP);
		//		int[] arr4 = ShapeBasics.createTriangle(30, 30, 16, 5, SWT.HORIZONTAL | SWT.DOWN);
		//		int[] arr5 = ShapeBasics.createTriangle(10, 50, 5, 16, SWT.VERTICAL | SWT.LEFT);
		//		int[] arr6 = ShapeBasics.createTriangle(30, 50, 5, 16, SWT.VERTICAL | SWT.RIGHT);

		//		int[] arr2 = ShapeBasics.createTriangle(10, 10, 50, 35, SWT.DOWN);

		//createTriangle

		//		gc.setForeground(ColorExtension.BLACK);
		//		gc.drawPolygon(arr1);
		//		gc.drawPolyline(arr2);
		//		gc.drawPolygon(arr3);
		//		gc.drawPolygon(arr4);
		//		gc.drawPolygon(arr5);
		//		gc.drawPolygon(arr6);

		//gc.drawPolygon(seg.getShape());

	}
}
