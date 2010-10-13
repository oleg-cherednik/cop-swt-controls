/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.algorithms.sort;

import static cop.algorithms.sort.InsertionSort.insertionSort;
import static cop.algorithms.sort.SortTestSuit.sortedIntArray;
import static cop.algorithms.sort.SortTestSuit.unsortedIntArray;
import static cop.common.extensions.ArrayExtension.convertToIntegerArray;
import static cop.common.extensions.ComparatorExtension.createComparator;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:abba-bestl@mail.ru">Cherednik, Oleg</a>
 */
public class InsertionSortTest
{
	private int[] arr;
	private Integer[] arr1;
	private Comparator<Integer> cmp = createComparator(Integer.class);

	@Before
	public void setUp() throws Exception
	{
		assertNotNull(unsortedIntArray);
		assertNotNull(sortedIntArray);
		assertEquals(sortedIntArray.length, unsortedIntArray.length);

		arr = Arrays.copyOf(unsortedIntArray, unsortedIntArray.length);
		arr1 = convertToIntegerArray(arr);

		assertNotNull(cmp);
		assertNotNull(arr);
		assertNotNull(arr1);
		assertEquals(arr.length, unsortedIntArray.length);
		assertEquals(arr.length, arr1.length);
	}

	@Test
	public void testInsertionSort()
	{
		assertNull(insertionSort(null));
		assertArrayEquals(insertionSort(new int[0]), new int[0]);
		assertArrayEquals(insertionSort(arr), sortedIntArray);
	}

	@Test
	public void testTemplateInsertionSort()
	{
		assertNull(insertionSort(null, null));
		assertNull(insertionSort(null, cmp));
		assertArrayEquals(insertionSort(new Integer[0], null), new Integer[0]);
		assertArrayEquals(insertionSort(arr1, null), arr1);
		assertArrayEquals(insertionSort(arr1, cmp), convertToIntegerArray(sortedIntArray));
	}
}
