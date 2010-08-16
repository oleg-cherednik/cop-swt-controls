package cop.swt.widgets.keys;

import static cop.common.extensions.CollectionExtension.isNotEmpty;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_UNKNOWN;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import cop.common.extensions.CollectionExtension;
import cop.common.extensions.StringExtension;
import cop.swt.widgets.interfaces.IClear;
import cop.swt.widgets.keys.enums.KeyEnum;

public class KeyGroup implements IClear, Cloneable
{
	private Set<KeyEnum> keys;

	{
		keys = createContainer();
	}

	protected Set<KeyEnum> createContainer()
	{
		return new LinkedHashSet<KeyEnum>();
	}

	public KeyGroup(KeyEnum... keys)
	{
		if(CollectionExtension.isEmpty(keys))
			return;

		for(KeyEnum key : keys)
			if(isNotNull(key) && key != KEY_UNKNOWN)
				this.keys.add(key);
	}

	public KeyEnum[] getKeys()
	{
		return keys.toArray(new KeyEnum[0]);
	}
	
	public void addKey(KeyEnum key)
	{
		if(isNotNull(key))
			keys.add(key);
	}

	public boolean removeKey(KeyEnum key)
	{
		return isNotNull(key) ? keys.remove(key) : false;
	}

	public int size()
	{
		return keys.size();
	}

	public boolean contains(KeyEnum key)
	{
		return isNotNull(key) ? keys.contains(key) : false;
	}

	public boolean containsAll(Collection<KeyEnum> keys)
	{
		return isNotEmpty(keys) ? keys.containsAll(keys) : false;
	}

	public String getName()
	{
		if(CollectionExtension.isEmpty(keys))
			return "";

		StringBuilder buf = new StringBuilder();

		for(KeyEnum key : getKeys())
		{
			if(buf.length() > 0)
				buf.append("+");

			buf.append(StringExtension.isEmpty(key.getKeyName()) ? key : key.getKeyName());
		}

		return "" + buf;
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		if(CollectionExtension.isEmpty(keys))
			return "";

		return getName();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keys == null) ? 0 : keys.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof KeyGroup))
			return false;
		KeyGroup other = (KeyGroup)obj;
		if(keys == null)
		{
			if(other.keys != null)
				return false;
		}
		else if(!keys.equals(other.keys))
			return false;
		return true;
	}

	@Override
	public Object clone()
	{
		return new KeyGroup(getKeys());
	}

	/*
	 * IClear
	 */

	@Override
	public void clear()
	{
		keys.clear();
	}

	@Override
	public boolean isEmpty()
	{
		return keys.isEmpty();
	}
}
