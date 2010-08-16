package cop.swt.widgets.calendar.viewers;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static java.util.Calendar.DAY_OF_MONTH;
import static org.eclipse.swt.SWT.CENTER;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.MouseDoubleClick;
import static org.eclipse.swt.SWT.MouseDown;
import static org.eclipse.swt.SWT.NO_FOCUS;

import java.util.Calendar;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.widgets.FormToolkit;

import cop.swt.widgets.calendar.interfaces.IDayViewer;
import cop.swt.widgets.interfaces.Clearable;
import cop.swt.widgets.interfaces.Refreshable;

final class DaySell extends Composite implements IDayViewer, Refreshable, Clearable
{
	private static int BORDER_WIDTH = 2;

	private Label label;
	private Calendar date;

	public DaySell(Composite parent)
	{
		super(parent, NO_FOCUS);

		FormToolkit toolkit = new FormToolkit(getDisplay());

		createMainLayout();
		createLabel(toolkit);

		setBackground(parent.getBackground());
		setFont(parent.getFont());
	}

	private void createMainLayout()
	{
		GridLayout layout = new GridLayout();

		layout.marginWidth = BORDER_WIDTH;
		layout.marginHeight = BORDER_WIDTH;

		super.setLayout(layout);
		super.setLayoutData(new GridData(FILL, FILL, true, true));
	}

	private void createLabel(FormToolkit toolkit)
	{
		label = new SpecialLabel(this, CENTER | NO_FOCUS);

		label.setLayoutData(new GridData(FILL, FILL, true, true));

		label.addListener(MouseDown, notifyEvent);
		label.addListener(MouseDoubleClick, notifyEvent);
	}

	public String getText()
	{
		return label.getText();
	}

	public void setBorderColor(Color color)
	{
		super.setBackground(color);
	}

	/*
	 * Control
	 */

	@Override
	@Deprecated
	public void setLayoutData(Object layoutData)
	{}

	/*
	 * Composite
	 */

	@Override
	@Deprecated
	public void setLayout(Layout layout)
	{}

	/*
	 * Clearable
	 */

	@Override
	public void clear()
	{
		label.setText("");
	}

	/*
	 * Refreshable
	 */

	@Override
	public void refresh()
	{
		update();
		redraw();
	}

	/*
	 * IDayViewer
	 */

	@Override
	public void setDate(Calendar date)
	{
		if(isNull(date))
			return;

		this.date = (Calendar)date.clone();
		label.setText(Integer.toString(date.get(DAY_OF_MONTH)));
	}

	@Override
	public Calendar getDate()
	{
		return (Calendar)date.clone();
	}

	/*
	 * Control
	 */

	@Override
	public void update()
	{
		super.update();
		label.update();
	}

	@Override
	public void redraw()
	{
		super.redraw();
		label.redraw();
	}

	@Override
	public void setFont(Font font)
	{
		super.setFont(font);
		label.setFont(font);
	}

	@Override
	public void setBackground(Color color)
	{
		label.setBackground(color);
	}

	@Override
	public void setForeground(Color color)
	{
		label.setForeground(color);
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		return isNotNull(label) ? "" + label : "[---]";
	}

	/*
	 * Listener
	 */

	private Listener notifyEvent = new Listener()
	{
		@Override
		public void handleEvent(Event event)
		{
			notifyListeners(event.type, event);
		}
	};
}
