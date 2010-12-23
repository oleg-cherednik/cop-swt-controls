package cop.common.structures;

import java.util.Map.Entry;

public class EntrySet<K, V> implements Entry<K, V>
{
	private final K key;
	private V value;

	public EntrySet(K key)
	{
		this(key, null);
	}

	public EntrySet(K key, V value)
	{
		this.key = key;
		this.value = value;
	}

	/*
	 * Entry
	 */

	@Override
	public K getKey()
	{
		return key;
	}

	@Override
	public V getValue()
	{
		return value;
	}

	@Override
	public V setValue(V value)
	{
		V oldValue = this.value;
		this.value = value;
		return oldValue;
	}
}
