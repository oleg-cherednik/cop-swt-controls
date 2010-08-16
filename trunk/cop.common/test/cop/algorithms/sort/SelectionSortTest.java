/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id: SelectionSortTest.java 47 2010-08-16 12:19:28Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.common/cop.common/test/cop/algorithms/sort/SelectionSortTest.java $
 */
package cop.algorithms.sort;

import static cop.algorithms.sort.SelectionSort.selectionSort;
import static cop.algorithms.sort.SortTests.sortedIntArray;
import static cop.algorithms.sort.SortTests.unsortedIntArray;
import static cop.common.extensions.CollectionExtension.convertToIntegerArray;
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
public class SelectionSortTest
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
	public void testSelectionSort()
	{
		assertNull(selectionSort(null));
		assertArrayEquals(selectionSort(new int[0]), new int[0]);
		assertArrayEquals(selectionSort(arr), sortedIntArray);
	}

	@Test
	public void testTemplateSelectionSort()
	{
		assertNull(selectionSort(null, null));
		assertNull(selectionSort(null, cmp));
		assertArrayEquals(selectionSort(new Integer[0], null), new Integer[0]);
		assertArrayEquals(selectionSort(arr1, null), arr1);
		assertArrayEquals(selectionSort(arr1, cmp), convertToIntegerArray(sortedIntArray));
	}
}
