package cop.common.extensions;

import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.ReflectionExtension.isBoolean;
import static cop.common.extensions.ReflectionExtension.isByte;
import static cop.common.extensions.ReflectionExtension.isDouble;
import static cop.common.extensions.ReflectionExtension.isFloat;
import static cop.common.extensions.ReflectionExtension.isInteger;
import static cop.common.extensions.ReflectionExtension.isLong;
import static cop.common.extensions.ReflectionExtension.isShort;

import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class CompareExtension
{
	private static final Map<Class<?>, Comparator<?>> comparators = new HashMap<Class<?>, Comparator<?>>();

	private CompareExtension()
	{}

	public static <T> int compareObjects(Comparable<T> obj1, T obj2)
	{
		if(obj1 == obj2)
			return 0;
		if(isNull(obj1) ^ isNull(obj2))
			return isNull(obj2) ? 1 : -1;

		return obj1.compareTo(obj2);
	}

	public static <T> int compareStrings(String str1, String str2, Collator collator)
	{
		if(collator == null)
			return compareObjects(str1, str2);
		if(str1 == str2)
			return 0;
		if((str1 == null) ^ (str2 == null))
			return (str2 == null) ? 1 : -1;

		return collator.compare(str1, str2);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> Comparator<T> createComparator(Class<T> obj)
	{
		Comparator<?> comparator = comparators.get(obj);

		if(comparator == null)
		{
			comparators.put(obj, comparator = new Comparator<T>()
			{
				@Override
				public int compare(T obj1, T obj2)
				{
					return compareObjects(obj1, obj2);
				}
			});
		}

		return (Comparator<T>)comparator;
	}

	public static int compareNumbers(Class<?> type, Object obj1, Object obj2)
	{
		if(isBoolean(type))
			return compareObjects((Boolean)obj1, (Boolean)obj2);
		if(isByte(type))
			return compareObjects((Byte)obj1, (Byte)obj2);
		if(isShort(type))
			return compareObjects((Short)obj1, (Short)obj2);
		if(isInteger(type))
			return compareObjects((Integer)obj1, (Integer)obj2);
		if(isLong(type))
			return compareObjects((Long)obj1, (Long)obj2);
		if(isFloat(type))
			return compareObjects((Float)obj1, (Float)obj2);
		if(isDouble(type))
			return compareObjects((Double)obj1, (Double)obj2);

		throw new IllegalArgumentException(type + " is not a Number");
	}
}
