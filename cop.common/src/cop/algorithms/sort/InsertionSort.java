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

import java.util.Comparator;

import cop.common.predicates.comparators.LessOrEqual;
import cop.common.predicates.comparators.MoreOrEqual;

/**
 * Insertion sort is a simple sorting algorithm, a comparison sort in which the sorted array (or list) is built one
 * entry at a time. It is much less efficient on large lists than more advanced algorithms such as quicksort, heapsort,
 * or merge sort.
 * <p>
 * <b>Algorithm:</b><br>
 * Every repetition of insertion sort removes an element from the input data, inserting it into the correct position in
 * the already-sorted list, until no input elements remain. The choice of which element to remove from the input is
 * arbitrary, and can be made using almost any choice algorithm.
 * <p>
 * <b>Performance & Space:</b><br>
 * <ul>
 * <li>Worst case: O(n<sup>2</sup>)
 * <li>Best case: O(n<sup>2</sup>)
 * <li>Average case: O(n<sup>2</sup>)
 * <li>Worst case space complexity: O(n) total, O(1) auziliary
 * </ul>
 * <p>
 * <b>Advantages:</b><br>
 * <ul>
 * <li>Simple implementation
 * <li>Efficient for (quite) small data sets
 * <li>Adaptive, i.e. efficient for data sets that are already substantially sorted: the time complexity is O(n + d),
 * where d is the number of inversions
 * <li>More efficient in practice than most other simple quadratic, i.e. O(n<sup>2</sup>) algorithms such as selection
 * sort or bubble sort; the best case (nearly sorted input) is O(n)
 * <li>Stable, i.e. does not change the relative order of elements with equal keys
 * <li>In-place, i.e. only requires a constant amount O(1) of additional memory space
 * <li>Online, i.e. can sort a list as it receives it.
 * </ul>
 * 
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 16.08.2010
 */
public final class InsertionSort
{
	/**
	 * Closed constructor
	 */
	private InsertionSort()
	{}

	/**
	 * Sort given array using insertion sort algorithm.<br>
	 * If array is <tt>null</tt> or empty then do nothing.
	 * 
	 * @param arr array to sort
	 * @return just return <b>arr</b> parameter
	 */
	public static int[] insertionSort(int[] arr)
	{
		if(isEmpty(arr))
			return arr;

		int tmp = 0;
		int j = 0;
		int k = 0;

		for(int i = 1; i < arr.length; i++)
		{
			if(arr[i] >= arr[i - 1])
				continue;

			for(j = i; j > 0; j--)
				if(arr[j - 1] <= arr[i])
					break;

			tmp = arr[i];

			for(k = i; k > j; k--)
				arr[k] = arr[k - 1];

			arr[j] = tmp;
		}

		return arr;
	}

	/**
	 * Sort given array using insertion sort algorithm and giving comparator.<br>
	 * If array or comparator is <tt>null</tt> or empty then do nothing.
	 * 
	 * @param arr array to sort
	 * @param cmp comparator
	 * @return just return <b>arr</b> parameter
	 */
	public static <T> T[] insertionSort(T[] arr, Comparator<T> cmp)
	{
		if(isEmpty(arr) || isNull(cmp))
			return arr;

		int j = 0;
		int k = 0;
		T tmp = null;

		MoreOrEqual<T> moreOrEqual = new MoreOrEqual<T>(cmp);
		LessOrEqual<T> lessOrEqual = new LessOrEqual<T>(cmp);

		for(int i = 1; i < arr.length; i++)
		{
			if(moreOrEqual.check(arr[i], arr[i - 1]))
				continue;

			for(j = i; j > 0; j--)
				if(lessOrEqual.check(arr[j - 1], arr[i]))
					break;

			tmp = arr[i];

			for(k = i; k > j; k--)
				arr[k] = arr[k - 1];

			arr[j] = tmp;
		}

		return arr;
	}
}
