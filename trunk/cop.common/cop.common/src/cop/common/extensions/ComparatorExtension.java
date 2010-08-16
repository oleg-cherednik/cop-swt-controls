package cop.common.extensions;

import static cop.common.extensions.CommonExtension.isNull;

import java.util.Comparator;

public final class ComparatorExtension
{
	private ComparatorExtension()
	{}

	public static <T> int compareObjects(Comparable<T> obj1, T obj2)
	{
		if(obj1 == obj2)
			return 0;
		if(isNull(obj1) ^ isNull(obj2))
			return isNull(obj2) ? 1 : -1;

		return obj1.compareTo(obj2);
	}

	public static <T extends Comparable<T>> Comparator<T> createComparator(Class<T> obj)
	{
		return new Comparator<T>()
		{
			@Override
			public int compare(T obj1, T obj2)
			{
				return compareObjects(obj1, obj2);
			}
		};
	}
}
