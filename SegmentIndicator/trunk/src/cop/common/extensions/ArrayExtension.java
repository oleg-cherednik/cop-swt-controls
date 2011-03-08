package cop.common.extensions;

import static cop.common.extensions.NumericExtension.isInRangeMinMax;

import java.util.Collection;

public final class ArrayExtension
{
	private ArrayExtension()
	{}

	public static <T> boolean isEmpty(T[] arr)
	{
		return arr == null || arr.length == 0;
	}

	public static boolean isEmpty(int[] arr)
	{
		return arr == null || arr.length == 0;
	}

	public static boolean isNotEmpty(int[] arr)
	{
		return !isEmpty(arr);
	}

	public static <T> T[] invertArray(T[] arr)
	{
		if(arr == null)
			return null;

		T[] res = arr.clone();
		int i = res.length - 1;

		for(T val : arr)
			res[i--] = val;

		return res;
	}

	@SuppressWarnings("rawtypes")
	public static Collection<Integer> toCollection(final int[] arr, Class<? extends Collection> cls)
	{
		try
		{
			return toCollection(arr, cls, null, null);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Collection<Integer> toCollection(final int[] arr, Class<? extends Collection> cls, Integer minimum,
	                Integer maximum) throws InstantiationException, IllegalAccessException
	{
		Collection<Integer> dest = cls.newInstance();

		if(isEmpty(arr))
			return dest;

		for(Integer val : arr)
			if(isInRangeMinMax(val, minimum, maximum))
				dest.add(val);

		return dest;
	}
}
