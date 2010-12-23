package swtcalendar.org.vafada.swtcalendar;

import static cop.common.extensions.CalendarExtension.isDay;
import static java.lang.Integer.parseInt;
import static org.eclipse.swt.SWT.CENTER;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.MouseDoubleClick;
import static org.eclipse.swt.SWT.MouseDown;
import static org.eclipse.swt.SWT.MouseUp;
import static org.eclipse.swt.SWT.NO_FOCUS;
import static org.eclipse.swt.SWT.RIGHT;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

class DayControl extends Composite
{
	private Composite filler;
	private Label label;

	public DayControl(Composite parent)
	{
		super(parent, NO_FOCUS);

		createMainLayout();
		createFillerComposite();
		createLabel(filler);

		setBorderColor(parent.getBackground());
		setBackground(parent.getBackground());
		setFont(parent.getFont());
	}

	private void createMainLayout()
	{
		GridLayout layout = new GridLayout();

		layout.marginWidth = 1;
		layout.marginHeight = 1;

		setLayout(layout);
	}

	private void createFillerComposite()
	{
		filler = new Composite(this, NO_FOCUS);

		GridLayout layout = new GridLayout();
		GridData layoutData = new GridData(FILL, FILL, true, true);

		layout.marginWidth = 2;
		layout.marginHeight = 0;

		filler.setLayout(layout);
		filler.setLayoutData(layoutData);

		filler.addListener(MouseDown, notifyEvent);
		filler.addListener(MouseUp, notifyEvent);
		filler.addListener(MouseDoubleClick, notifyEvent);
	}

	private void createLabel(Composite parent)
	{
		label = new DayLabel(parent, RIGHT | NO_FOCUS);

		label.setLayoutData(new GridData(CENTER, CENTER, true, true));

		label.addListener(MouseDown, notifyEvent);
		label.addListener(MouseUp, notifyEvent);
		label.addListener(MouseDoubleClick, notifyEvent);
	}

	public void setValue(int day)
	{
		if(isDay(day))
			label.setText(Integer.toString(day));
	}

	public int getValue()
	{
		return parseInt(getText());
	}

	public String getText()
	{
		return label.getText();
	}

	@Override
	public void setFont(Font font)
	{
		super.setFont(font);

		filler.setFont(font);
		label.setFont(font);
	}

	@Override
	public void setBackground(Color color)
	{
		filler.setBackground(color);
		label.setBackground(color);
	}

	@Override
	public void setForeground(Color color)
	{
		label.setForeground(color);
	}

	public void setBorderColor(Color color)
	{
		super.setBackground(color);
	}

	private Listener notifyEvent = new Listener()
	{
		@Override
		public void handleEvent(Event event)
		{
			notifyListeners(event.type, event);
		}
	};
}
