/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.algorithms.sort;

import static cop.common.extensions.CollectionExtension.convertToIntArray;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author <a href="mailto:abba-bestl@mail.ru">Cherednik, Oleg</a>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { InsertionSortTest.class, SelectionSortTest.class, BubbleSortTest.class, ShakerSortTest.class })
public class SortTestSuit
{
	protected static final String FILENAME = "numbers.txt";
	protected static final String FILEPATH = "test/resources";
	protected static final int READ_ALL = -1;
	protected static int TOTAL = READ_ALL;

	public static int[] unsortedIntArray;
	public static int[] sortedIntArray;

	@BeforeClass
	public static void beforeAllTests() throws Exception
	{
		unsortedIntArray = readDataFile();
		sortedIntArray = sortArray(unsortedIntArray);

		assertNotNull(unsortedIntArray);
		assertNotNull(sortedIntArray);
		assertEquals(sortedIntArray.length, unsortedIntArray.length);
	}

	protected static int[] readDataFile() throws Exception
	{
		List<Integer> arr = new ArrayList<Integer>();
		BufferedReader in = null;
		FileReader fr = null;

		try
		{
			fr = new FileReader(FILEPATH + "/" + FILENAME);
			in = new BufferedReader(fr);
			String str = in.readLine();
			int i = 0;

			while((str != null) && (TOTAL == READ_ALL || (TOTAL > 0 && i++ < TOTAL)))
			{
				arr.add(Integer.parseInt(str));
				str = in.readLine();
			}
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

			if(fr != null)
			{
				try
				{
					fr.close();
				}
				catch(IOException e)
				{}

				fr = null;
			}
		}

		return convertToIntArray(arr);
	}

	protected static int[] sortArray(int[] arr)
	{
		if(arr == null)
			return null;

		int[] sortedArray = Arrays.copyOf(arr, arr.length);

		Arrays.sort(sortedArray);

		return sortedArray;
	}
}
