package cop.swt.widgets;

import static cop.common.extensions.CommonExtension.isNull;
import static org.eclipse.swt.SWT.Selection;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

public class MultiTouchButton extends Button
{
	public static final int DEF_INIT_DELAY_MS = 200;
	public static final int DEF_REPEAT_DELAY_MS = 100;

	private int initDelay = DEF_INIT_DELAY_MS;
	private int repeatDelay = DEF_REPEAT_DELAY_MS;

	private List<SelectionListener> selectionListeners = new ArrayList<SelectionListener>(3);
	private Repeater repeater;

	/**
	 * @param parent Parent container.
	 * @param style Button style.
	 */
	public MultiTouchButton(Composite parent, int style)
	{
		super(parent, style);

		addListeners();
	}

	private void addListeners()
	{
		addMouseListener(updateRepeaterOnMouseButton);
		addMouseTrackListener(cancelRepeaterOnMouseExit);
	}

	@Override
    public void addSelectionListener(SelectionListener listener)
	{
		selectionListeners.add(listener);
	}

	@Override
    public void removeSelectionListener(SelectionListener listener)
	{
		selectionListeners.remove(listener);
	}

	/**
	 * @return Returns the initial repeat delay in milliseconds.
	 */
	public int getInitDelay()
	{
		return initDelay;
	}

	/**
	 * @param delay The new initial repeat delay in milliseconds.
	 */
	public void setInitDelay(int delay)
	{
		this.initDelay = (delay <= 0) ? 0 : delay;
	}

	/**
	 * @return Returns the repeat delay in millisecons.
	 */
	public int getRepeatDelay()
	{
		return repeatDelay;
	}

	/**
	 * @param repeatDelay The new repeat delay in milliseconds.
	 */
	public void setRepeatDelay(int delay)
	{
		this.initDelay = (delay <= 0) ? 0 : delay;
	}

	private void buttonPressed(int stateMask, int time)
	{
		for(SelectionListener listener : selectionListeners)
			listener.widgetSelected(new SelectionEvent(creatEvent(stateMask, time)));
	}

	private Event creatEvent(int stateMask, int time)
	{
		Event event = new Event();

		event.type = Selection;
		event.display = getDisplay();
		event.widget = this;
		event.stateMask = stateMask;
		event.time = time;

		return event;
	}

	private void cancelRepeater()
	{
		if(isNull(repeater))
			return;

		repeater.cancel();
		repeater = null;
	}

	/*
	 * Widget
	 */

	@Override
	protected void checkSubclass()
	{}

	/*
	 * Listeners
	 */

	private MouseListener updateRepeaterOnMouseButton = new MouseAdapter()
	{
		@Override
        public void mouseDown(MouseEvent e)
		{
			cancelRepeater();

			if(e.button != 1)
				return;

			buttonPressed(e.stateMask, e.time);

			repeater = new Repeater(e.stateMask, repeatDelay);

			getDisplay().timerExec(initDelay, repeater);
		}

		@Override
        public void mouseUp(MouseEvent event)
		{
			if(event.button == 1)
				cancelRepeater();
		}
	};

	private MouseTrackListener cancelRepeaterOnMouseExit = new MouseTrackAdapter()
	{
		@Override
		public void mouseExit(MouseEvent e)
		{
			cancelRepeater();
		}
	};

	private class Repeater implements Runnable
	{
		private boolean canceled;
		private int stateMask;
		private int delay;

		public Repeater(int stateMask, int delay)
		{
			super();

			this.stateMask = stateMask;
			this.delay = delay;
		}

		@Override
		public void run()
		{
			if(canceled)
				return;

			buttonPressed(stateMask, (int)System.currentTimeMillis());
			getDisplay().timerExec(delay, this);
		}

		public void cancel()
		{
			canceled = true;
		}
	}
}
