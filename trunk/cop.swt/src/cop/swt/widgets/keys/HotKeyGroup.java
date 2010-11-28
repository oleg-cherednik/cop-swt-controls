package cop.swt.widgets.keys;

import static cop.algorithms.search.LinearSearch.linearSearch;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_ALT;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_CTRL;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_DELETE;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_ESC;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_SHIFT;

import java.util.Set;
import java.util.TreeSet;

import cop.common.extensions.StringExtension;
import cop.swt.widgets.keys.enums.KeyEnum;

public class HotKeyGroup extends KeyGroup
{
	private static final int MAX_KEYS_IN_GROUP = 5;
	
	private static final KeyEnum[] MAGIC_KEYS; // can be use without control keys
	private static final KeyEnum[] CONTROL_KEYS;

	static
	{
		MAGIC_KEYS = new KeyEnum[] { KEY_DELETE, KEY_ESC };
		CONTROL_KEYS = new KeyEnum[] { KEY_CTRL, KEY_ALT, KEY_SHIFT };
	}

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

	public boolean containsOnlyMagicKeys()
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
		if(isNull(keys) || keys.isEmpty())
			return false;

		if(keys.size() > CONTROL_KEYS.length + 2)
			return false;

		return true;
	}

	/*
	 * Object
	 */

	@Override
	public Object clone()
	{
		return new HotKeyGroup(getKeys());
	}

	/*
	 * KeyGroup
	 */

	@Override
	protected Set<KeyEnum> createContainer()
	{
		return new TreeSet<KeyEnum>();
	}

	@Override
	public void addKey(KeyEnum key)
	{
		if(isNotNull(key) && size() <= MAX_KEYS_IN_GROUP)
			super.addKey(key);
	}

	@Override
	public String getName()
	{
		KeyEnum[] keys = getKeys();
		StringBuilder buf = new StringBuilder();
		KeyEnum tmpKey;

		for(int size = keys.length, i = size - 1; i >= 0; i--)
		{
			if(buf.length() > 0)
				buf.append("+");

			tmpKey = keys[i];

			buf.append(StringExtension.isEmpty(tmpKey.getKeyName()) ? tmpKey : tmpKey.getKeyName());
		}

		return "" + buf;
	}
}
