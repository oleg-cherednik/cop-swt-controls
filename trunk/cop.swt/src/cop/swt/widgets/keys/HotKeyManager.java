package cop.swt.widgets.keys;

import static cop.extensions.CommonExt.isNotNull;
import static cop.swt.widgets.keys.HotKeyGroup.isControlKey;
import static cop.swt.widgets.keys.HotKeyGroup.isMagicKey;
import static cop.swt.widgets.keys.enums.KeyEnum.parseKeyEnum;
import static org.eclipse.swt.SWT.Dispose;
import static org.eclipse.swt.SWT.KeyDown;
import static org.eclipse.swt.SWT.KeyUp;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cop.swt.widgets.interfaces.Clearable;
import cop.swt.widgets.keys.enums.KeyEnum;

public final class HotKeyManager implements Clearable, Listener
{
	public static final String HOT_KEY = "hot_key";
	private static final int DELAY = 5000;

	private Control control;
	private Map<KeyGroup, Listener> listeners = new HashMap<KeyGroup, Listener>();
	private Map<KeyGroup, Entry<String, ? extends Object>> keyData = new HashMap<KeyGroup, Entry<String, ? extends Object>>();
	private HotKeyGroup keys = new HotKeyGroup();
	private HotKeyGroup prvKeys = new HotKeyGroup();
	private int prvTime;
	private boolean usePrv;

	public HotKeyManager(Control control)
	{
		Assert.isNotNull(control, "Control can't be null");

		this.control = control;

		addListeners();
	}

	private void addListeners()
	{
		control.addListener(Dispose, this);
		control.addListener(KeyDown, this);
		control.addListener(KeyUp, this);
	}

	private void checkKeysForNotify(KeyEnum key)
	{
		if(usePrv && !prvKeys.isEmpty())
			checkPrvKeysForNotify();
		else if(!keys.isHasOnlyMagicKeys() && keys.containsOnlyControls())
			return;

		for(KeyGroup group : listeners.keySet())
		{
			if(!group.equals(keys))
				continue;

			listeners.get(group).handleEvent(createEvent(group));
			prvKeys.removeKey(key);
			prvTime = (int)System.currentTimeMillis();

			return;
		}
	}

	private Event createEvent(KeyGroup group)
	{
		Event event = new Event();
		Properties prop = new Properties();
		Entry<String, ? extends Object> entry = keyData.get(group);

		if(entry != null)
			prop.put(entry.getKey(), entry.getValue());

		prop.put(HOT_KEY, group);

		event.data = prop;
		event.widget = control;
		event.type = KeyDown;
		event.time = (int)System.currentTimeMillis();

		return event;
	}

	private void checkPrvKeysForNotify()
	{
		for(KeyGroup keys : listeners.keySet())
		{
			if(!keys.equals(prvKeys))
				continue;

			listeners.get(keys).handleEvent(createEvent(keys));
			clear();

			return;
		}

		clear();
	}

	public void addHotKey(HotKeyGroup group, Listener listener)
	{
		addHotKey(group, listener, null);
	}

	public void addHotKey(HotKeyGroup group, Listener listener, Entry<String, ? extends Object> data)
	{
		Assert.isNotNull(group, "Hot key can't be null");
		Assert.isNotNull(listener, "Listener for hot key can't be null");

		listeners.put(group, listener);

		if(data != null)
			keyData.put(group, data);
	}

	public void removeHotKey(HotKeyGroup keys)
	{
		if(isNotNull(keys))
			listeners.remove(keys);
	}

	public boolean isKeyPressed(KeyEnum key)
	{
		return keys.contains(key);
	}

	private void dispose()
	{
		control.removeListener(Dispose, this);
		control.removeListener(KeyDown, this);
		control.removeListener(KeyUp, this);
	}

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

	/*
	 * Listener
	 */

	public void handleEvent(Event event)
	{
		if(event.widget != control)
			return;

		if(event.type == Dispose)
			dispose();
		else if(event.type == KeyDown)
			onKeyDown(event);
		else if(event.type == KeyUp)
			onKeyUp(event);
	}

	/*
	 * listeners
	 */

	private void onKeyDown(Event event)
	{
		if(listeners.size() == 0)
			return;

		if(prvTime != 0 && (event.time - prvTime) > DELAY)
			clear();

		KeyEnum key = parseKeyEnum(event.keyCode);

		if(isKeyPressed(key))
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

	private void onKeyUp(Event event)
	{
		KeyEnum key = parseKeyEnum(event.keyCode);

		if(!keys.removeKey(key))
			return;

		prvKeys.addKey(key);
		prvTime = event.time;
		usePrv = keys.isEmpty();
	}
}
