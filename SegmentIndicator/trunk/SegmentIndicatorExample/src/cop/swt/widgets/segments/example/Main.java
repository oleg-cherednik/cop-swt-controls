/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.segments.example;

import static cop.common.extensions.BitExtension.isBitSet;
import static org.eclipse.swt.SWT.COLOR_BLACK;
import static org.eclipse.swt.SWT.COLOR_CYAN;
import static org.eclipse.swt.SWT.COLOR_MAGENTA;
import static org.eclipse.swt.SWT.COLOR_RED;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import cop.swt.widgets.segments.components.numeric.ShortIndicator;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public class Main implements SelectionListener, ModifyListener
{
	private static final short MIN = Short.MIN_VALUE;
	private static final short MAX = Short.MAX_VALUE;

	private Button visibleButton;
	private Button backgroundButton;
	private Button transparentCheckBox;
	private Button clearButton;
	private Button rotateButton;
	private Spinner valueSpinner;
	private Spinner scaleSpinner;
	private ShortIndicator segmentIndicator;

	private Color[] colors;
	int currColor = 0;

	private static Color BLACK;
	private static Color RED;
	private static Color CYAN;
	private static Color MAGENTA;

	public static void main(String[] args)
	{
		new Main().run();
	}

	public void run()
	{
		Display display = new Display();

		BLACK = display.getSystemColor(COLOR_BLACK);
		RED = display.getSystemColor(COLOR_RED);
		CYAN = display.getSystemColor(COLOR_CYAN);
		MAGENTA = display.getSystemColor(COLOR_MAGENTA);
		colors = new Color[] { BLACK, RED, CYAN, MAGENTA };

		createContents(display);

		display.dispose();
	}

	private void createContents(Display display)
	{
		Shell shell = new Shell(display);
		shell.setText("HtmlViewer example");
		shell.setSize(400, 300);
		shell.setBackground(BLACK);
		shell.setLayout(new GridLayout());

		createControls(shell);
		onClearButton();

		shell.open();

		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
	}

	private void createControls(Composite parent)
	{
		createSegmentIndicator(parent);
		createChangeBackgroundButtonPart(parent);
		createSetVisibleButtonPart(parent);
		createSetValueSpinnerPart(parent);
		createSetTransparentCheckBoxPart(parent);
		createRotateButtonPart(parent);
		createScaleSpinner(parent);
		createClearButtonPart(parent);
	}

	private void createSegmentIndicator(Composite parent)
	{
		segmentIndicator = new ShortIndicator(parent, SWT.NONE);

		segmentIndicator.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false));
		segmentIndicator.setScale(2);
		segmentIndicator.setBackground(BLACK);
	}

	private void createChangeBackgroundButtonPart(final Composite parent)
	{
		backgroundButton = new Button(parent, SWT.NONE);

		backgroundButton.setText("background");
		backgroundButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		backgroundButton.addSelectionListener(this);
	}

	private void createSetVisibleButtonPart(Composite parent)
	{
		visibleButton = new Button(parent, SWT.NONE);

		visibleButton.setText("visible");
		visibleButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		visibleButton.addSelectionListener(this);
	}

	private void createSetValueSpinnerPart(Composite parent)
	{
		valueSpinner = new Spinner(parent, SWT.NONE);

		valueSpinner.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		valueSpinner.setMinimum(MIN);
		valueSpinner.setMaximum(MAX);
		valueSpinner.addModifyListener(this);
	}

	private void createSetTransparentCheckBoxPart(Composite parent)
	{
		transparentCheckBox = new Button(parent, SWT.CHECK);

		transparentCheckBox.setText("transparent");
		transparentCheckBox.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		transparentCheckBox.addSelectionListener(this);
	}

	private void createRotateButtonPart(Composite parent)
	{
		rotateButton = new Button(parent, SWT.TOGGLE);

		rotateButton.setText("rotate");
		rotateButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		rotateButton.addSelectionListener(this);
	}

	private void createScaleSpinner(Composite parent)
	{
		scaleSpinner = new Spinner(parent, SWT.NONE);

		scaleSpinner.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		scaleSpinner.setSelection(1);
		scaleSpinner.setMinimum(1);
		scaleSpinner.setMaximum(4);
		scaleSpinner.addModifyListener(this);
		scaleSpinner.setSelection(2);
	}

	private void createClearButtonPart(Composite parent)
	{
		clearButton = new Button(parent, SWT.NONE);

		clearButton.setText("clear");
		clearButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		clearButton.addSelectionListener(this);
	}

	private void onVisibleButton()
	{
		segmentIndicator.setVisible(!segmentIndicator.isVisible());
	}

	private void onBackgroundButton()
	{
		currColor++;

		if(currColor >= colors.length)
			currColor = 0;

		backgroundButton.getParent().setBackground(colors[currColor]);
		segmentIndicator.setBackground(colors[currColor]);
	}

	private void onValueSpinner()
	{
		int value = valueSpinner.getSelection();

		if(value >= MIN && value <= MAX)
			segmentIndicator.setValue((short)value);
	}

	private void onScaleSpinner()
	{
		segmentIndicator.setScale(scaleSpinner.getSelection());
	}

	private void onTransparentCheckBox()
	{
		segmentIndicator.setTransparent(transparentCheckBox.getSelection());
	}

	private void onClearButton()
	{
		segmentIndicator.clear();
	}

	private void onRotateButton()
	{
		int orientation = segmentIndicator.getOrientation();

		if(isBitSet(orientation, SWT.UP))
			segmentIndicator.setOrientation(SWT.RIGHT);
		else if(isBitSet(orientation, SWT.RIGHT))
			segmentIndicator.setOrientation(SWT.DOWN);
		else if(isBitSet(orientation, SWT.DOWN))
			segmentIndicator.setOrientation(SWT.LEFT);
		else if(isBitSet(orientation, SWT.LEFT))
			segmentIndicator.setOrientation(SWT.UP);
	}

	/*
	 * SelectionListener
	 */

	public void widgetSelected(SelectionEvent e)
	{
		if(e.getSource() == visibleButton)
			onVisibleButton();
		else if(e.getSource() == backgroundButton)
			onBackgroundButton();
		else if(e.getSource() == transparentCheckBox)
			onTransparentCheckBox();
		else if(e.getSource() == clearButton)
			onClearButton();
		else if(e.getSource() == rotateButton)
			onRotateButton();
	}

	public void widgetDefaultSelected(SelectionEvent e)
	{}

	/*
	 * ModifyListener
	 */

	public void modifyText(ModifyEvent e)
	{
		if(e.getSource() == valueSpinner)
			onValueSpinner();
		else if(e.getSource() == scaleSpinner)
			onScaleSpinner();
	}
}
