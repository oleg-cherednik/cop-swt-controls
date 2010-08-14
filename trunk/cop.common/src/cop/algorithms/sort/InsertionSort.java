/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.algorithms.sort;

import static cop.common.extensions.CollectionExtension.isEmpty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Insertion sort is a simple sorting algorithm, a comparison sort in which the sorted array (or list) is built one
 * entry at a time. It is much less efficient on large lists than more advanced algorithms such as quicksort, heapsort,
 * or merge sort.
 * <p>
 * However, insertion sort provides several advantages:
 * <ul>
 * <li>Simple implementation
 * <li>Efficient for (quite) small data sets
 * <li>Adaptive, i.e. efficient for data sets that are already substantially sorted: the time complexity is O(n + d),
 * where d is the number of inversions
 * <li>More efficient in practice than most other simple quadratic, i.e. O(n2) algorithms such as selection sort or
 * bubble sort; the best case (nearly sorted input) is O(n)
 * <li>Stable, i.e. does not change the relative order of elements with equal keys
 * <li>In-place, i.e. only requires a constant amount O(1) of additional memory space
 * <li>Online, i.e. can sort a list as it receives it
 * </ul>
 * 
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
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
	 * If array is null or empty then do not anything.
	 * 
	 * @param arr
	 *            array to sort
	 * @return just return <tt>arr</tt> parameter
	 */
	public static int[] insertionSort(int[] arr)
	{
		if(isEmpty(arr))
			return arr;

		int tmp = 0;
		int j = 0;
		int k = 0;

		for(int i = 1, size = arr.length; i < size; i++)
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

	public static void main(String[] args)
	{
		BufferedReader in = null;
		final int TOTAL = -1;

		try
		{
			in = new BufferedReader(new FileReader("c:\\numbers.txt"));

			List<Integer> unsortedArray = new ArrayList<Integer>();
			// Set<Integer> sortedArray = new TreeSet<Integer>();
			String str = in.readLine();
			int i = 0;

			while(str != null || (i > 0 && i++ < TOTAL))
			{
				unsortedArray.add(Integer.parseInt(str));
				str = in.readLine();
			}

			int a = 0;
			a++;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(in != null)
			{
				try
				{
					in.close();
				}
				catch(IOException e)
				{}

				in = null;
			}
		}
	}
}
