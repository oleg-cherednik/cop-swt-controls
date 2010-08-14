package cop.swt.widgets.comparators;

import static cop.common.extensions.CommonExtension.isNull;

import java.util.Comparator;

import org.eclipse.swt.graphics.RGB;

public final class RgbComparator implements Comparator<Object>
{
	public static int compareRgb(RGB obj1, RGB obj2)
	{
		if(obj1 == obj2)
			return 0;
		if(isNull(obj1) ^ isNull(obj2))
			return isNull(obj2) ? 1 : -1;

		if(obj1.red != obj2.red)
			return (obj1.red > obj2.red) ? 1 : -1;

		if(obj1.green != obj2.green)
			return (obj1.green > obj2.green) ? 1 : -1;

		if(obj1.blue != obj2.blue)
			return (obj1.blue > obj2.blue) ? 1 : -1;

		return 0;
	}

	@Override
	public int compare(Object obj1, Object obj2)
	{
		return compareRgb((RGB)obj1, (RGB)obj2);
	}
}
