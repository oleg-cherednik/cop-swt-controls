/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.algorithms.sort;

import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isNull;
import cop.common.predicates.MoreOrEqual;

public final class BubbleSort
{
	/**
	 * Closed constructor
	 */
	private BubbleSort()
	{}

	/**
	 * Sort given array using bubble sort algorithm.<br>
	 * If array is <tt>null</tt> or empty then do nothing.
	 * 
	 * @param arr
	 *            array to sort
	 * @return just return <b>arr</b> parameter
	 */
	public static int[] bubbleSort(int[] arr)
	{
		if(isEmpty(arr))
			return arr;

		int j = 0;
		int tmp = 0;
		int size = arr.length - 1;
		boolean noSwap = true;

		for(int i = 0; i < arr.length; i++, noSwap = true)
		{
			for(j = size; j > i; j--)
			{
				if(arr[j] >= arr[j - 1])
					continue;

				noSwap = false;

				// swap
				tmp = arr[j];
				arr[j] = arr[j - 1];
				arr[j - 1] = tmp;
			}

			if(noSwap)
				break;
		}

		return arr;
	}

	/**
	 * Sort given array using bubble sort algorithm and giving {@link MoreOrEqual} predicate.<br>
	 * If array or comparator is <tt>null</tt> or empty then do nothing.
	 * 
	 * @param arr
	 *            array to sort
	 * @param moreOrEqual
	 *            predicate
	 * @return just return <b>arr</b> parameter
	 * 
	 * @see MoreOrEqual
	 */
	public static <T> T[] bubbleSort(T[] arr, MoreOrEqual<T> moreOrEqual)
	{
		if(isEmpty(arr) || isNull(moreOrEqual))
			return arr;

		int j = 0;
		T tmp = null;
		int size = arr.length - 1;
		boolean noSwap = true;

		for(int i = 0; i < arr.length; i++, noSwap = true)
		{
			for(j = size; j > i; j--)
			{
				if(moreOrEqual.check(arr[j], arr[j - 1]))
					continue;

				noSwap = false;

				// swap
				tmp = arr[j];
				arr[j] = arr[j - 1];
				arr[j - 1] = tmp;
			}

			if(noSwap)
				break;
		}

		return arr;
	}
}
