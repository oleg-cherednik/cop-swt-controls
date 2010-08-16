/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.common.extensions;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.NumericExtension.isInRangeMin;
import static cop.common.extensions.NumericExtension.isInRangeMinMax;
import static java.lang.Math.max;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public final class CollectionExtension
{
	private CollectionExtension()
	{}

	public static boolean isArraysEqual(int[] arr1, int[] arr2)
	{
		if(arr1 == arr2)
			return true;

		if(isNull(arr1) || isNull(arr2) || arr1.length != arr2.length)
			return false;

		for(int i = 0, size = arr1.length; i < size; i++)
			if(arr1[i] != arr2[i])
				return false;

		return true;
	}

	public static boolean isArraysEqual(Class<?>[] arr1, Class<?>[] arr2)
	{
		if(arr1 == arr2)
			return true;

		if(isNull(arr1) || isNull(arr2) || arr1.length != arr2.length)
			return false;

		for(int i = 0, size = arr1.length; i < size; i++)
			if(arr1[i] != arr2[i])
				return false;

		return true;
	}

	public static <T> T[] removeNull(T[] arr)
	{
		List<T> res = new LinkedList<T>();

		for(T val : arr)
		{
			if(val != null)
				res.add(val);
		}

		return res.toArray(arr);
	}

	public static <T> T[] replaceAll(T[] arr, T oldVal, T newVal)
	{
		List<T> res = new LinkedList<T>();

		for(T val : arr)
			res.add(val.equals(oldVal) ? newVal : val);

		return res.toArray(arr);
	}

	public static <T> boolean isEmpty(T[] arr)
	{
		return arr == null || arr.length == 0;
	}

	public static <T> boolean isNotEmpty(T[] arr)
	{
		return !isEmpty(arr);
	}

	public static boolean isEmpty(double[] arr)
	{
		return arr == null || arr.length == 0;
	}

	public static boolean isEmpty(char[] arr)
	{
		return arr == null || arr.length == 0;
	}

	public static boolean isNotEmpty(double[] arr)
	{
		return !isEmpty(arr);
	}

	public static boolean isEmpty(boolean[] arr)
	{
		return arr == null || arr.length == 0;
	}

	public static boolean isNotEmpty(boolean[] arr)
	{
		return !isEmpty(arr);
	}

	public static boolean isEmpty(int[] arr)
	{
		return arr == null || arr.length == 0;
	}

	public static boolean isNotEmpty(int[] arr)
	{
		return !isEmpty(arr);
	}

	public static boolean isEmpty(long[] arr)
	{
		return arr == null || arr.length == 0;
	}

	public static boolean isNotEmpty(long[] arr)
	{
		return !isEmpty(arr);
	}

	public static boolean isEmpty(Collection<?> collection)
	{
		return collection == null || collection.isEmpty();
	}

	public static boolean isNotEmpty(Collection<?> collection)
	{
		return !isEmpty(collection);
	}

	public static boolean isEmpty(Map<?, ?> map)
	{
		return map == null || map.isEmpty();
	}

	public static boolean isNotEmpty(Map<?, ?> map)
	{
		return !isEmpty(map);
	}

	public static <T> int getMaximumLength(T[] arr)
	{
		if(arr == null)
			return -1;

		if(arr.length == 0)
			return 0;

		int res = 0;

		for(T obj : arr)
			res = max(res, obj.toString().length());

		return res;
	}

	public static <T> int getMinimumLength(T[] arr)
	{
		if(arr == null)
			return -1;

		if(arr.length == 0)
			return 0;

		int res = Integer.MAX_VALUE;

		for(T obj : arr)
			res = max(res, obj.toString().length());

		return res;
	}

	public static <T> void addNotNull(Collection<T> dest, Collection<T> src)
	{
		if(dest == null || isEmpty(src) || dest == src)
			return;

		for(T obj : src)
		{
			if(obj != null)
				dest.add(obj);
		}
	}

	public static <T> int find(T[] arr, T key)
	{
		if(isEmpty(arr) || key == null)
			return -1;

		for(int i = 0; i < arr.length; i++)
			if(key.equals(arr[i]))
				return i;

		return -arr.length;
	}

	public static int find(int[] arr, int key)
	{
		if(isEmpty(arr))
			return -1;

		for(int i = 0; i < arr.length; i++)
			if(key == arr[i])
				return i;

		return -arr.length;
	}

	public static <T> T[] copyOfRange(T[] arr, int from)
	{
		return copyOfRange(arr, from, -1);
	}

	public static <T> T[] copyOfRange(T[] arr, int from, int to)
	{
		if(isEmpty(arr) || from < 0)
			return arr;

		if(from > arr.length - 1)
			return null;

		if(to < 0)
		{
			int _from = from;
			int _to = arr.length;
			return Arrays.copyOfRange(arr, _from, _to);
		}

		return (from < to) ? arr : Arrays.copyOfRange(arr, from, to);
	}

	public static <T> T getLastItem(T[] arr) throws IllegalArgumentException
	{
		if(isEmpty(arr))
			throw new IllegalArgumentException("arr is empty or null");

		return arr[arr.length - 1];
	}

	public static double getLastItem(double[] arr) throws IllegalArgumentException
	{
		if(isEmpty(arr))
			throw new IllegalArgumentException("arr is empty or null");

		return arr[arr.length - 1];
	}

	public static boolean getLastItem(boolean[] arr) throws IllegalArgumentException
	{
		if(isEmpty(arr))
			throw new IllegalArgumentException("arr is empty or null");

		return arr[arr.length - 1];
	}

	public static int getLastItem(int[] arr) throws IllegalArgumentException
	{
		if(isEmpty(arr))
			throw new IllegalArgumentException("arr is empty or null");

		return arr[arr.length - 1];
	}

	public static long getLastItem(long[] arr)
	{
		if(isEmpty(arr))
			throw new IllegalArgumentException("arr is empty or null");

		return arr[arr.length - 1];
	}

	public static <T> boolean swap(List<T> list, int index1, int index2)
	{
		if(isEmpty(list))
			return false;

		int size = list.size();

		if(!isInRangeMin(index1, 0, size) || !isInRangeMin(index2, 0, size))
			return false;

		T tmp = list.get(index1);

		list.set(index1, list.get(index2));
		list.set(index2, tmp);

		return true;
	}

	public static int[] removeDublicates(int[] arr)
	{
		return removeDublicates(arr, null, null);
	}

	public static int[] removeDublicates(int[] arr, Integer minimum, Integer maximum)
	{
		try
		{
			return toIntArray(toCollection(arr, LinkedHashSet.class, minimum, maximum));
		}
		catch(Exception e)
		{
			return new int[0];
		}
	}

	public static int[] removeDublicatesAndSort(int[] arr)
	{
		return removeDublicatesAndSort(arr, null, null);
	}

	public static int[] removeDublicatesAndSort(int[] arr, Integer minimum, Integer maximum)
	{
		try
		{
			return toIntArray(toCollection(arr, TreeSet.class, minimum, maximum));
		}
		catch(Exception e)
		{
			return new int[0];
		}
	}

	public static <T> void convertToAnotherColletion(Collection<T> dest, Collection<T> src)
	{
		if(isNull(dest) || isEmpty(src))
			return;

		if(!dest.isEmpty())
			dest.clear();

		for(T obj : src)
			dest.add(obj);
	}

	public static <T> String[] convertToStringArray(T[] arr)
	{
		return convertToStringArray(arr, -1);
	}

	public static <T> String[] convertToStringArray(T[] arr, int length)
	{
		if(isEmpty(arr))
			return new String[0];

		int newLength = isInRangeMinMax(length, 1, arr.length) ? length : arr.length;
		String[] res = new String[newLength];

		for(int i = 0, size = newLength; i < size; i++)
			res[i] = "" + arr[i];

		return res;
	}

	public static int[] convertToIntArray(Collection<Integer> values)
	{
		if(isEmpty(values))
			return new int[0];

		int[] arr = new int[values.size()];
		int i = 0;

		for(Integer value : values)
			arr[i++] = isNotNull(value) ? value.intValue() : 0;

		return arr;
	}
	
	public static int[] convertToIntArray(Integer[] values)
	{
		if(isEmpty(values))
			return new int[0];
		
		int[] arr = new int[values.length];
		int i = 0;
		
		for(Integer value : values)
			arr[i++] = isNotNull(value) ? value.intValue() : 0;
			
			return arr;
	}

	public static Integer[] convertToIntegerArray(int[] values)
	{
		if(isEmpty(values))
			return new Integer[0];

		Integer[] arr = new Integer[values.length];
		int i = 0;

		for(Integer value : values)
			arr[i++] = Integer.valueOf(isNotNull(value) ? value : 0);

		return arr;
	}

	public static <T> void removeDublicates(Collection<T> arr)
	{
		try
		{
			if(isEmpty(arr))
				return;

			filterCollection(LinkedHashSet.class, arr);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static <T> void removeDublicatesAndSort(Collection<T> arr)
	{
		try
		{
			if(isEmpty(arr))
				return;

			filterCollection(TreeSet.class, arr);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void moveArrayX(int[] arr, int offs)
	{
		if(isNull(arr))
			return;

		for(int i = 0; i < arr.length; i += 2)
			arr[i] = arr[i] + offs;
	}

	public static void moveArrayY(int[] arr, int offs)
	{
		if(isNull(arr))
			return;

		for(int i = 1; i < arr.length; i += 2)
			arr[i] = arr[i] + offs;
	}

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public static <T> Collection<T> toCollection(final T[] arr, Class<? extends Collection> cls)
	{
		try
		{
			Collection<T> dest = cls.newInstance();

			if(isEmpty(arr))
				return dest;

			for(T val : arr)
				dest.add(val);

			return dest;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] invertArray(T[] arr)
	{
		if(isEmpty(arr))
			return (T[])(new Object[0]);

		T[] res = arr.clone();
		int i = res.length - 1;

		for(T val : arr)
			res[i--] = val;

		return res;
	}

	public static char[] invertArray(char[] arr)
	{
		if(isEmpty(arr))
			return new char[0];

		char[] res = arr.clone();
		int i = res.length - 1;

		for(char ch : arr)
			res[i--] = ch;

		return res;
	}

	// public static <T> Collection<T>invertCollection(Collection<T> arr)
	// {
	//
	// }

	private static <T extends Number> int[] toIntArray(Collection<T> arr)
	{
		int i = 0;
		int[] res = new int[arr.size()];

		for(T val : arr)
			res[i++] = val.intValue();

		return res;
	}

	@SuppressWarnings("unchecked")
	private static <T> void filterCollection(Class<? extends Collection> cls, Collection<T> arr)
	        throws InstantiationException, IllegalAccessException
	{
		if(isEmpty(arr))
			return;

		Collection<T> tmp = cls.newInstance();

		convertToAnotherColletion(tmp, arr);
		convertToAnotherColletion(arr, tmp);
	}

	@SuppressWarnings("unchecked")
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
