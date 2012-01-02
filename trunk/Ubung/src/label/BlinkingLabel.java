package label;

import static cop.common.extensions.BitExtension.clearBits;
import static cop.common.extensions.BitExtension.isBitSet;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.swt.extensions.ColorExtension.getInvertedColor;
import static org.eclipse.swt.SWT.BACKGROUND;
import static org.eclipse.swt.SWT.FOREGROUND;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * @author cop
 */
public class BlinkingLabel extends Label
{
	private static final long DEF_DELAY = 500;

	private boolean colorInv;
	private boolean blinkBackground;
	private boolean blinkForeground;

	private static Color colorBg;
	private static Color colorFg;
	private static Color colorBgInv;
	private static Color colorFgInv;

	private static long delay = DEF_DELAY; // ms

	private Thread blinkThread;

	/**
	 * @param parent
	 * @param style SWT.FOREGROUND - blink foreground (default)/SWT.BACKGROUND - blink only background
	 */
	public BlinkingLabel(Composite parent, int style)
	{
		super(parent, clearBits(style, BACKGROUND | FOREGROUND));

		blinkBackground = isBitSet(style, BACKGROUND);
		blinkForeground = isBitSet(style, FOREGROUND);

		addDisposeListener(new DisposeListener()
		{
			@Override
			public void widgetDisposed(DisposeEvent e)
			{
				dispose();
			}
		});

		if(!blinkBackground && !blinkForeground)
			blinkForeground = true;
	}

	public void setDelay(long delay)
	{
		delay = (delay <= 0) ? DEF_DELAY : delay;
	}

	public long getDelay()
	{
		return delay;
	}

	public void setBlinkingEnabled(boolean enabled)
	{
		if(!(enabled ^ isNotNull(blinkThread)))
			return;

		if(enabled)
		{
			blinkThread = new Thread(changeColor);
			blinkThread.start();
		}
		else
		{
			blinkThread.interrupt();
			blinkThread = null;

			if(blinkBackground)
				super.setBackground(colorBg);

			if(blinkForeground)
				super.setForeground(colorFg);
		}
	}

	public boolean isBlinkingEnabled()
	{
		return isNotNull(blinkThread);
	}

	private Runnable changeColor = new Runnable()
	{
		@Override
		public void run()
		{
			try
			{
				while(!isDisposed() && !Thread.currentThread().isInterrupted())
				{
					Thread.sleep(delay);

					getDisplay().syncExec(new Runnable()
					{
						@Override
						public void run()
						{
							if(isNull(blinkThread) || blinkThread.isInterrupted())
								return;

							if(blinkBackground)
								BlinkingLabel.super.setBackground(colorInv ? colorBg : colorBgInv);

							if(blinkForeground)
								BlinkingLabel.super.setForeground(colorInv ? colorFg : colorFgInv);

							colorInv = !colorInv;
						}
					});
				}
			}
			catch(InterruptedException e)
			{}
		}
	};

	/*
	 * Widget
	 */

	@Override
	public void dispose()
	{
		if(isNotNull(blinkThread))
			blinkThread.interrupt();

		super.dispose();
	}

	@Override
	protected void checkSubclass()
	{}

	/*
	 * Control
	 */

	@Override
	public void setForeground(Color color)
	{
		colorFg = color;
		colorFgInv = getInvertedColor(color);

		super.setForeground(color);
	}

	@Override
	public void setBackground(Color color)
	{
		colorBg = color;
		colorBgInv = getInvertedColor(color);

		super.setBackground(color);
	}
}
