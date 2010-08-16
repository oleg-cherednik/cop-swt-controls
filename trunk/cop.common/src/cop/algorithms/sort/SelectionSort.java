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

import java.util.Comparator;

import cop.common.predicates.Less;
import cop.common.predicates.NotEqual;

/**
 * Bubble sort is a simple sorting algorithm. It works by repeatedly stepping through the list to be sorted, comparing
 * each pair of adjacent items and swapping them if they are in the wrong order. The pass through the list is repeated
 * until no swaps are needed, which indicates that the list is sorted. The algorithm gets its name from the way smaller
 * elements "bubble" to the top of the list. Because it only uses comparisons to operate on elements, it is a comparison
 * sort.
 * <p>
 * <b>Perfomance & Space:</b><br>
 * <ul>
 * <li>Worst case: O(n<sup>2</sup>)
 * <li>Best case: O(n)
 * <li>Average case: O(n<sup>2</sup>)
 * <li>Worst case space complexity: O(1) auxiliary
 * </ul>
 * 
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 */
public final class SelectionSort
{
	/**
	 * Closed constructor
	 */
	private SelectionSort()
	{}

	/**
	 * Sort given array using selection sort algorithm and giving comparator.<br>
	 * If array or comparator is <tt>null</tt> or empty then do nothing.
	 * 
	 * @param arr
	 *            array to sort
	 * @param cmp
	 *            comparator
	 * @return just return <b>arr</b> parameter
	 */
	public static int[] selectionSort(int[] arr)
	{
		if(isEmpty(arr))
			return arr;

		int j = 0;
		int tmp = 0;
		int size = arr.length - 1;

		for(int i = 0, min = i; i < size; i++, min = i)
		{
			for(j = i + 1; j < arr.length; j++)
				if(arr[j] < arr[min])
					min = j;

			if(min == i)
				continue;
			else if(arr[i] != arr[min])
			{
				// swap
				tmp = arr[i];
				arr[i] = arr[min];
				arr[min] = tmp;
			}
		}

		return arr;

	}

	/**
	 * Sort given array using selection sort algorithm and giving comparator.<br>
	 * If array or comparator is <tt>null</tt> or empty then do nothing.
	 * 
	 * @param arr
	 *            array to sort
	 * @param cmp
	 *            comparator
	 * @return just return <b>arr</b> parameter
	 */
	public static <T> T[] selectionSort(T[] arr, Comparator<T> cmp)
	{
		if(isEmpty(arr) || isNull(cmp))
			return arr;

		int j = 0;
		int size = arr.length - 1;
		T tmp = null;

		Less<T> less = new Less<T>(cmp);
		NotEqual<T> notEquals = new NotEqual<T>(cmp);

		for(int i = 0, min = i; i < size; i++, min = i)
		{
			for(j = i + 1; j < arr.length; j++)
				if(less.check(arr[j], arr[min]))
					min = j;

			if(min == i)
				continue;
			else if(notEquals.check(arr[i], arr[min]))
			{
				// swap
				tmp = arr[i];
				arr[i] = arr[min];
				arr[min] = tmp;
			}
		}

		return arr;
	}
}
