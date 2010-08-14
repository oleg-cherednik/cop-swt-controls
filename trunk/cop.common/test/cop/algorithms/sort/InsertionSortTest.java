package cop.algorithms.sort;

import static cop.common.extensions.CollectionExtension.convertToIntArray;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class InsertionSortTest
{
	private static final String FILENAME = "numbers.txt";
	private static final String FILEPATH = "test/resources";
	private static final int READ_ALL = -1;
	private static final int TOTAL = READ_ALL;

	private int[] unsortedArray;
	private int[] sortedArray;

	@Before
	public void setUp() throws Exception
	{
		unsortedArray = readDataFile();

		assertNotNull(unsortedArray);
		assertTrue(unsortedArray.length > 0);

		sortedArray = sortArray(unsortedArray);
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

			while(str != null || (i > 0 && i++ < TOTAL))
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

	@Test
	public void testInsertionSort()
	{
		int[] res = Arrays.copyOf(unsortedArray, unsortedArray.length);

		assertArrayEquals(InsertionSort.insertionSort(res), sortedArray);
	}

}
