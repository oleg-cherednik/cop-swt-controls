/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.common.extensions;

import static cop.common.extensions.ArrayExtension.EMPTY_INT_ARR;

import java.util.Collection;

public final class CollectionExtension
{
	private CollectionExtension()
	{}

	public static boolean isEmpty(Collection<?> collection)
	{
		return collection == null || collection.isEmpty();
	}

	public static int[] convertToIntArray(Collection<Integer> values)
	{
		if(isEmpty(values))
			return EMPTY_INT_ARR;

		int[] arr = new int[values.size()];
		int i = 0;

		for(Integer value : values)
			arr[i++] = (value != null) ? value.intValue() : 0;

		return arr;
	}

	public static char[] replaceAll(char[] arr, char oldVal, char newVal)
	{
		for(int i = 0; i < arr.length; i++)
			if(arr[i] == oldVal)
				arr[i] = newVal;

		return arr;
	}
}
