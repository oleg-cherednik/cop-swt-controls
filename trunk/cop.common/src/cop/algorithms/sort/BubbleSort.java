/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.algorithms.sort;

import static cop.extensions.ArrayExt.isEmpty;
import static cop.extensions.CommonExt.isNull;
import cop.common.predicates.comparators.MoreOrEqual;

/**
 * Bubble sort is a simple sorting algorithm. It works by repeatedly stepping through the list to be sorted, comparing
 * each pair of adjacent items and swapping them if they are in the wrong order. The pass through the list is repeated
 * until no swaps are needed, which indicates that the list is sorted. The algorithm gets its name from the way smaller
 * elements "bubble" to the top of the list. Because it only uses comparisons to operate on elements, it is a comparison
 * sort.
 * <p>
 * <b>Performance & Space:</b><br>
 * <ul>
 * <li>Worst case: O(n<sup>2</sup>)
 * <li>Best case: O(n)
 * <li>Average case: O(n<sup>2</sup>)
 * <li>Worst case space complexity: O(1) auxiliary
 * </ul>
 * 
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 16.08.2010
 */
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
	 * @param arr array to sort
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
	 * @param arr array to sort
	 * @param moreOrEqual predicate
	 * @return just return <b>arr</b> parameter
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
