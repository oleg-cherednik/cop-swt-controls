package cop.algorithms.search;

import static cop.extensions.ArrayExt.isEmpty;
import static cop.extensions.CommonExt.isNull;
import cop.common.predicates.comparators.Equal;

public final class LinearSearch
{
	private LinearSearch()
	{}

	public static int linearSearch(int[] arr, int key)
	{
		if(isEmpty(arr))
			return -1;

		for(int i = 0; i < arr.length; i++)
			if(key == arr[i])
				return i;

		return -1;
	}

	public static <T> int linearSearch(T[] arr, T key)
	{
		if(isEmpty(arr))
			return -1;

		for(int i = 0; i < arr.length; i++)
			if(isNull(key) ? isNull(arr[i]) : key.equals(arr[i]))
				return i;

		return -1;
	}

	public static <T> int linearSearch(T[] arr, T key, Equal<T> equal)
	{
		if(isEmpty(arr) || key == null || equal == null)
			return -1;

		for(int i = 0; i < arr.length; i++)
			if(equal.check(arr[i], key))
				return i;

		return -1;
	}
}
