package cop.swt.widgets.comparators;

import static cop.extensions.CommonExt.isNull;

import java.util.Comparator;

public class EnumComparator<T> implements Comparator<T>
{
	@SuppressWarnings("unchecked")
    public static int compareEnum(Enum obj1, Enum obj2)
	{
		if(obj1 == obj2)
			return 0;
		if(isNull(obj1) ^ isNull(obj2))
			return isNull(obj2) ? 1 : -1;

		return obj1.compareTo(obj2);
	}

	@Override
	public int compare(T obj1, T obj2)
	{
		return compareEnum((Enum)obj1, (Enum)obj2);
	}
}
