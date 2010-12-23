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

import cop.common.predicates.comparators.LessOrEqual;
import cop.common.predicates.comparators.MoreOrEqual;

/**
 * Cocktail sort, also known as bidirectional bubble sort, cocktail shaker sort, shaker sort (which can also refer to a
 * variant of selection sort), ripple sort, shuttle sort or happy hour sort, is a variation of bubble sort that is both
 * a stable sorting algorithm and a comparison sort. The algorithm differs from bubble sort in that sorts in both
 * directions each pass through the list. This sorting algorithm is only marginally more difficult than bubble sort to
 * implement, and solves the problem with so-called turtles in bubble sort.
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
public final class ShakerSort
{
	/**
	 * Closed constructor
	 */
	private ShakerSort()
	{}

	/**
	 * Sort given array using shaker sort algorithm.<br>
	 * If array is <tt>null</tt> or empty then do nothing.
	 * 
	 * @param arr array to sort
	 * @return just return <b>arr</b> parameter
	 */
	public static int[] shakerSort(int[] arr)
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
			else
				noSwap = true;

			for(j = i + 1; j < size; j++)
			{
				if(arr[j] <= arr[j + 1])
					continue;

				noSwap = false;
				// swap
				tmp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = tmp;
			}

			if(noSwap)
				break;
		}

		return arr;
	}

	/**
	 * Sort given array using shaker sort algorithm and giving comparator.<br>
	 * If array or comparator is <tt>null</tt> or empty then do nothing.
	 * 
	 * @param arr array to sort
	 * @param cmp comparator
	 * @return just return <b>arr</b> parameter
	 */
	public static <T> T[] shakerSort(T[] arr, Comparator<T> cmp)
	{
		if(isEmpty(arr) || isNull(cmp))
			return arr;

		int j = 0;
		boolean noSwap = true;
		int size = arr.length - 1;
		T tmp = null;

		MoreOrEqual<T> moreOrEqual = new MoreOrEqual<T>(cmp);
		LessOrEqual<T> lessOrEqual = new LessOrEqual<T>(cmp);

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
			else
				noSwap = true;

			for(j = i + 1; j < size; j++)
			{
				if(lessOrEqual.check(arr[j], arr[j + 1]))
					continue;

				noSwap = false;

				// swap
				tmp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = tmp;
			}

			if(noSwap)
				break;
		}

		return arr;
	}
}
