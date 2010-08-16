package cop.swt.widgets.keys;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.swt.widgets.keys.HotKeyGroup.isControlKey;
import static cop.swt.widgets.keys.HotKeyGroup.isMagicKey;
import static cop.swt.widgets.keys.enums.KeyEnum.parseKeyEnum;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cop.swt.widgets.interfaces.Clearable;
import cop.swt.widgets.keys.enums.KeyEnum;

public final class HotKeyManager implements Clearable
{
	private static int DELAY = 5000;

	private Control control;
	private Map<KeyGroup, Listener> map = new HashMap<KeyGroup, Listener>();
	private HotKeyGroup keys = new HotKeyGroup();
	private HotKeyGroup prvKeys = new HotKeyGroup();
	private int prvTime;
	private boolean usePrv;

	public HotKeyManager(Control control)
	{
		Assert.isLegal(isNotNull(control), "Control can't be null");

		this.control = control;

		addListeners();
	}

	private void addListeners()
	{
		control.addKeyListener(keyListener);
		control.addDisposeListener(onDispose);
	}

	private void checkKeysForNotify(KeyEnum key)
	{
		if(usePrv && !prvKeys.isEmpty())
			checkPrvKeysForNotify();
		else if(!keys.containsOnlyMagicKeys() && keys.containsOnlyControls())
			return;

		for(KeyGroup group : map.keySet())
		{
			if(!group.equals(keys))
				continue;

			map.get(group).handleEvent(new Event());
			prvKeys.removeKey(key);
			prvTime = (int)System.currentTimeMillis();

			return;
		}
	}

	private void checkPrvKeysForNotify()
	{
		for(KeyGroup group : map.keySet())
		{
			if(!group.equals(prvKeys))
				continue;

			map.get(group).handleEvent(new Event());
			clear();

			return;
		}

		clear();
	}

	public void addHotKey(HotKeyGroup keys, Listener listener)
	{
		Assert.isLegal(isNotNull(keys), "Hot key can't be null");
		Assert.isLegal(isNotNull(listener), "Listener for hot key can't be null");

		map.put(keys, listener);
	}

	public void removeHotKey(HotKeyGroup keys)
	{
		if(isNotNull(keys))
			map.remove(keys);
	}

	/*
	 * Listener
	 */

	private KeyListener keyListener = new KeyListener()
	{
		@Override
		public void keyReleased(KeyEvent e)
		{
			KeyEnum key = parseKeyEnum(e.keyCode);

			if(!keys.removeKey(key))
				return;

			prvKeys.addKey(key);
			prvTime = e.time;
			usePrv = keys.isEmpty();
		}

		@Override
		public void keyPressed(KeyEvent e)
		{
			if(map.size() == 0)
				return;

			if(prvTime != 0 && (e.time - prvTime) > DELAY)
				clear();

			KeyEnum key = parseKeyEnum(e.keyCode);

			if(keys.contains(key))
				return;

			if(keys.isEmpty() && prvKeys.isEmpty() && !isControlKey(key) && !isMagicKey(key))
			{
				clear();
				return;
			}

			keys.addKey(key);
			prvKeys.removeKey(key);

			if(isControlKey(key) || prvKeys.containsOnlyControls())
				clearPrv();
			
			if(!keys.containsOnlyControls() || isMagicKey(key))
				checkKeysForNotify(key);
		}
	};

	private DisposeListener onDispose = new DisposeListener()
	{
		@Override
		public void widgetDisposed(DisposeEvent e)
		{
			control.removeKeyListener(keyListener);
		}
	};

	/*
	 * Clearable
	 */

	@Override
	public void clear()
	{
		keys.clear();
		clearPrv();
	}

	private void clearPrv()
	{
		prvKeys.clear();
		prvTime = 0;
		usePrv = false;
	}
}
