/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.algorithms.sort;

import static cop.algorithms.sort.BubbleSort.bubbleSort;
import static cop.algorithms.sort.SortTestSuit.sortedIntArray;
import static cop.algorithms.sort.SortTestSuit.unsortedIntArray;
import static cop.common.extensions.ArrayExtension.convertToIntegerArray;
import static cop.common.extensions.CompareExtension.createComparator;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import cop.common.predicates.comparators.MoreOrEqual;

/**
 * @author <a href="mailto:abba-bestl@mail.ru">Cherednik, Oleg</a>
 */
public class BubbleSortTest
{
	private int[] arr;
	private Integer[] arr1;
	private MoreOrEqual<Integer> moreOrEqual = new MoreOrEqual<Integer>(createComparator(Integer.class));

	@Before
	public void setUp() throws Exception
	{
		assertNotNull(unsortedIntArray);
		assertNotNull(sortedIntArray);
		assertEquals(sortedIntArray.length, unsortedIntArray.length);

		arr = Arrays.copyOf(unsortedIntArray, unsortedIntArray.length);
		arr1 = convertToIntegerArray(arr);

		assertNotNull(moreOrEqual);
		assertNotNull(arr);
		assertNotNull(arr1);
		assertEquals(arr.length, unsortedIntArray.length);
		assertEquals(arr.length, arr1.length);
	}

	@Test
	public void testBubbleSort()
	{
		assertNull(bubbleSort(null));
		assertArrayEquals(bubbleSort(new int[0]), new int[0]);
		assertArrayEquals(bubbleSort(arr), sortedIntArray);
	}

	@Test
	public void testTemplateBubbleSort()
	{
		assertNull(bubbleSort(null, null));
		assertNull(bubbleSort(null, moreOrEqual));
		assertArrayEquals(bubbleSort(new Integer[0], null), new Integer[0]);
		assertArrayEquals(bubbleSort(arr1, null), arr1);
		assertArrayEquals(bubbleSort(arr1, moreOrEqual), convertToIntegerArray(sortedIntArray));
	}
}
