/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.algorithms.sort;

import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isNull;

import java.util.Comparator;

import cop.common.predicates.comparators.Less;
import cop.common.predicates.comparators.NotEqual;

/**
 * Selection sort is a sorting algorithm, specifically an in-place comparison sort. It has O(n2) complexity, making it
 * inefficient on large lists, and generally performs worse than the similar insertion sort. Selection sort is noted for
 * its simplicity, and also has performance advantages over more complicated algorithms in certain situations.
 * <p>
 * <b>Algorithm:</b><br>
 * The algorithm works as follows: find the minimum value in the list, swap it with the value in the first position,
 * repeat the steps above for the remainder of the list (starting at the second position and advancing each time).<br>
 * Effectively, the list is divided into two parts: the sublist of items already sorted, which is built up from left to
 * right and is found at the beginning, and the sublist of items remaining to be sorted, occupying the remainder of the
 * array. s *
 * <p>
 * <b>Perfomance & Space:</b><br>
 * <ul>
 * <li>Worst case: O(n<sup>2</sup>)
 * <li>Best case: O(n<sup>2</sup>)
 * <li>Average case: O(n<sup>2</sup>)
 * <li>Worst case space complexity: O(n) total, O(1) auxiliary
 * </ul>
 * 
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 16.08.2010
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
	 * @param arr array to sort
	 * @param cmp comparator
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
	 * @param arr array to sort
	 * @param cmp comparator
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
