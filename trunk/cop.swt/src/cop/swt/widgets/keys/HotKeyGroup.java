package cop.swt.widgets.keys;

import static cop.algorithms.search.LinearSearch.linearSearch;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_ALT;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_CTRL;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_DELETE;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_ESC;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_SHIFT;
import cop.swt.widgets.keys.enums.KeyEnum;

class HotKeyGroup extends KeyGroup
{
	// magic keys can be use without control keys
	private static final KeyEnum[] MAGIC_KEYS = new KeyEnum[] { KEY_DELETE, KEY_ESC };
	private static final KeyEnum[] CONTROL_KEYS = new KeyEnum[] { KEY_CTRL, KEY_ALT, KEY_SHIFT };

	public HotKeyGroup()
	{}

	public HotKeyGroup(KeyEnum... keys)
	{
		super(keys);

		if(!isValidHotKey(this))
			clear();
	}

	public boolean containsOnlyControls()
	{
		if(isEmpty() || size() > 3)
			return false;

		for(KeyEnum key : getKeys())
			if(!isControlKey(key))
				return false;

		return true;
	}

	public boolean isHasOnlyMagicKeys()
	{
		if(isEmpty())
			return false;

		for(KeyEnum key : getKeys())
			if(!isMagicKey(key))
				return false;

		return true;
	}

	public static boolean isMagicKey(KeyEnum key)
	{
		return isNotNull(key) ? (linearSearch(MAGIC_KEYS, key) >= 0) : false;
	}

	public static boolean isControlKey(KeyEnum key)
	{
		return isNotNull(key) ? (linearSearch(CONTROL_KEYS, key) >= 0) : false;
	}

	/*
	 * 1..3 (control keys) + letter 1..3 (control keys) + letter,letter magic key
	 */
	public static boolean isValidHotKey(KeyGroup keys)
	{
		if(keys == null || keys.isEmpty())
			return false;

		if(CONTROL_KEYS != null && keys.size() > CONTROL_KEYS.length + 2)
			return false;

		return true;
	}
}
