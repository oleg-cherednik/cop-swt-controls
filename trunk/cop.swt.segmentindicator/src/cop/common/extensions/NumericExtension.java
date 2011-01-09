/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.common.extensions;

public final class NumericExtension
{
	public static final char[] EMPTY_CHAR_ARR = new char[0];

	private NumericExtension()
	{}

	/*
	 * isInRange
	 */

	public static <T extends Number> boolean isInRangeMinMax(T value, T minimum, T maximum)
	{
		return isInRange(value, minimum, maximum, true, true);
	}

	private static <T extends Number> boolean isInRange(T value, T minimum, T maximum, boolean equalMin,
	                boolean equalMax)
	{
		if(value == null)
			return true;

		boolean res = true;

		res &= (minimum == null) ? true : isGreater(value, minimum, equalMin);
		res &= (maximum == null) ? true : isLess(value, maximum, equalMax);

		return res;
	}

	public static <T extends Number> char[] toCharArray(T value)
	{
		return (value != null) ? value.toString().toCharArray() : EMPTY_CHAR_ARR;
	}

	private static <T extends Number> boolean isGreater(T value, T minimum, boolean equalMin)
	{
		if(minimum == null || value == null)
			return false;
		if(equalMin && value.equals(minimum))
			return true;

		if(value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long)
			return value.longValue() > minimum.longValue();
		if(value instanceof Float || value instanceof Double)
			return value.doubleValue() > minimum.doubleValue();

		assert false : "Unsupported Template";

		return false;
	}

	private static <T extends Number> boolean isLess(T value, T maximum, boolean equalMax)
	{
		if(maximum == null || value == null)
			return false;
		if(equalMax && value.equals(maximum))
			return true;

		if(value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long)
			return value.longValue() < maximum.longValue();
		if(value instanceof Float || value instanceof Double)
			return value.doubleValue() < maximum.doubleValue();

		assert false : "Unsupported Template";

		return false;
	}
}
