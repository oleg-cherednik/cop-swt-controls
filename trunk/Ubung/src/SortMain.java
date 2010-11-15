import static cop.algorithms.sort.InsertionSort.insertionSort;

public class SortMain
{
	public static void main(String[] args)
	{
		int[] arr = { 12, 15, 1, 3, 19, 4, 23, 1, 2, 9, 7, 5 };

		System.out.println("--- Sorting alorithms testing ---");
		System.out.println("elemetns: " + arr.length);

		testInsertionSort(arr);

		System.out.println("--- end ---");
	}

	private static void testInsertionSort(int[] arr)
	{
		System.out.print("--- insertion sort: ");

		double time1 = System.currentTimeMillis();

		insertionSort(arr);

		double time2 = System.currentTimeMillis();

		System.out.println(((time2 - time1) / 1000) + " sec.");
	}
}
