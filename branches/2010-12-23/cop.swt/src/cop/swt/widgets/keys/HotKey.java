package cop.swt.widgets.keys;

import static cop.swt.widgets.keys.enums.KeyEnum.KEY_CTRL;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_DOWN;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_UP;

import java.util.HashMap;
import java.util.Map;

import cop.common.extensions.ArrayExtension;
import cop.swt.widgets.keys.enums.KeyEnum;

public final class HotKey extends HotKeyGroup
{
	public static final HotKey EMPTY_HOT_KEY = new HotKey();

	public static final HotKey keyCtrlUp;
	public static final HotKey keyCtrlDown;

	private static final Map<String, HotKey> map = new HashMap<String, HotKey>();

	static
	{
		keyCtrlUp = createHotKey(KEY_CTRL, KEY_UP);
		keyCtrlDown = createHotKey(KEY_CTRL, KEY_DOWN);
	}

	public static HotKey createHotKey(KeyEnum... keys)
	{
		if(ArrayExtension.isEmpty(keys))
			return EMPTY_HOT_KEY;

		String name = getName(keys);
		HotKey key = map.get(name);

		if(key == null)
			map.put(name, key = new HotKey(keys));

		return key;
	}

	private HotKey(KeyEnum... keys)
	{
		super(keys);
	}

	/*
	 * KeyGroup
	 */

	@Override
	@Deprecated
	public void addKey(KeyEnum key)
	{}

	@Override
	@Deprecated
	public boolean removeKey(KeyEnum key)
	{
		return false;
	}

	/*
	 * Object
	 */

	@Override
	public HotKey clone()
	{
		return this;
	}
}
