/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.common.extensions;

import static cop.common.extensions.CollectionExtension.*;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.ReflectionExtension.isByte;
import static cop.common.extensions.ReflectionExtension.isDouble;
import static cop.common.extensions.ReflectionExtension.isFloat;
import static cop.common.extensions.ReflectionExtension.isInteger;
import static cop.common.extensions.ReflectionExtension.isLong;
import static cop.common.extensions.ReflectionExtension.isShort;
import static cop.common.extensions.StringExtension.DEFAULT_CHAR;
import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;

import java.text.DecimalFormat;

public final class NumericExtension
{
	private NumericExtension()
	{}

	// public static int setInRange(Integer value, Integer minimum, Integer maximum)
	// {
	// if(value != null && isInRangeMinMax(value, minimum, maximum))
	// return value;
	//
	// return (value <= minimum) ? minimum : maximum;
	// }

	// public static Integer[] getInvertNumberArray(Integer value)
	// {
	// return getNumberArray(value, -1, true);
	// }
	//
	// public static Integer[] getInvertNumberArray(Integer value, int size)
	// {
	// return getNumberArray(value, size, true);
	// }
	//
	// public static Integer[] getNumberArray(Integer value)
	// {
	// return getNumberArray(value, -1, false);
	// }
	//
	// public static Integer[] getNumberArray(Integer value, int size)
	// {
	// return getNumberArray(value, size, false);
	// }
	//
	// public static Integer[] getNumberArray(Integer value, boolean invert)
	// {
	// return getNumberArray(value, -1, invert);
	// }

	// public static <T extends Number> Integer[] getNumberArray(T value, int size, boolean invert)
	// {
	// if(isNull(value))
	// return new Integer[0];
	//
	// String str = expandToLimitWithChar(value, size, DEFAULT_CHAR);
	//
	// //String str = (size < 1) ? ("" + value.intValue()) : StringExtension.resizeString("" + value, size,
	// StringExtension.DEFAULT_CHAR);
	// //expandToLimitWithChar(value.intValue(), size);
	// Integer[] arr = new Integer[str.length()];
	// char[] ch1 = str.toCharArray();
	//
	// if(invert)
	// {
	// //int i = 0;
	// int i = arr.length - 1;
	//
	// for(char ch : ch1)
	// arr[i++] = (ch == DEFAULT_CHAR) ? null : Integer.valueOf(String.valueOf(ch));
	// }
	// else
	// {
	// //int i = arr.length - 1;
	// int i = 0;
	//
	// // for(int j = ch1.length - 1; j >= 0; j--)
	// // arr[i--] = (ch == DEFAULT_CHAR) ? null : Integer.valueOf(String.valueOf(ch));
	// //for(char ch : ch1)
	// // arr[i--] = (ch == DEFAULT_CHAR) ? null : Integer.valueOf(String.valueOf(ch));
	// }
	//
	// return arr;
	// }

	public static int compareNumbers(Class<?> type, Object obj1, Object obj2)
	{
		if(isByte(type))
			return ((Byte)obj1).compareTo((Byte)obj2);
		if(isShort(type))
			return ((Short)obj1).compareTo((Short)obj2);
		if(isShort(type))
			return ((Short)obj1).compareTo((Short)obj2);
		if(isInteger(type))
			return ((Integer)obj1).compareTo((Integer)obj2);
		if(isLong(type))
			return ((Long)obj1).compareTo((Long)obj2);
		if(isFloat(type))
			return ((Float)obj1).compareTo((Float)obj2);
		if(isDouble(type))
			return ((Double)obj1).compareTo((Double)obj2);

		throw new IllegalArgumentException(type + " is not a Number");
	}

	public static <T extends Number> char[] toCharArray(T value)
	{
		return isNotNull(value) ? value.toString().toCharArray() : EMPTY_CHAR_ARR;
	}

	public static <T extends Number> char[] toInvertedCharArray(T value)
	{
		return isNotNull(value) ? invertArray(value.toString().toCharArray()) : EMPTY_CHAR_ARR;
	}

	/*
	 * isInRange
	 */

	public static <T extends Number> boolean isInRange(T value, T minimum, T maximum)
	{
		return isInRange(value, minimum, maximum, false, false);
	}

	public static <T extends Number> boolean isInRangeMinMax(T value, T minimum, T maximum)
	{
		return isInRange(value, minimum, maximum, true, true);
	}

	public static <T extends Number> boolean isInRangeMin(T value, T minimum, T maximum)
	{
		return isInRange(value, minimum, maximum, true, false);
	}

	public static <T extends Number> boolean isInRangeMax(T value, T minimum, T maximum)
	{
		return isInRange(value, minimum, maximum, false, true);
	}

	private static <T extends Number> boolean isInRange(T value, T minimum, T maximum, boolean equalMin,
	                boolean equalMax)
	{
		if(isNull(value))
			return true;

		boolean res = true;

		res &= isNull(minimum) ? true : isGreater(value, minimum, equalMin);
		res &= isNull(maximum) ? true : isLess(value, maximum, equalMax);

		return res;
	}

	public static <T extends Number> boolean isNegative(T value)
	{
		if(isNull(value))
			return false;

		if(value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long)
			return value.longValue() < 0;
		if(value instanceof Float || value instanceof Double)
			return value.doubleValue() < 0;

		assert false : "Unsupported Template";

		return false;
	}

	/*
	 * isGreater
	 */

	public static <T extends Number> boolean isGreater(T value, T minimum)
	{
		return isGreater(value, minimum, false);
	}

	public static <T extends Number> boolean isGreaterOrEqual(T value, T minimum)
	{
		return isGreater(value, minimum, true);
	}

	private static <T extends Number> boolean isGreater(T value, T minimum, boolean equalMin)
	{
		if(isNull(minimum) || isNull(value))
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

	/*
	 * isLess
	 */

	public static <T extends Number> boolean isLess(T value, T maximum)
	{
		return isLess(value, maximum, false);
	}

	public static <T extends Number> boolean isLessOrEqual(T value, T maximum)
	{
		return isLess(value, maximum, true);
	}

	private static <T extends Number> boolean isLess(T value, T maximum, boolean equalMax)
	{
		if(isNull(maximum) || isNull(value))
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

	/*
	 * 
	 */

	public static <T extends Number> int countDigits(T value)
	{
		if(isNull(value))
			throw new NullPointerException("Can't calculate length for 'null' value");

		if(value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long)
			return Long.toString(abs(value.longValue())).length();
		if(value instanceof Float || value instanceof Double)
			return Double.toString(abs(value.doubleValue())).length();

		assert false : "Unsupported Template";
		return 0;
	}

	public static <T extends Number> int countLength(T value)
	{
		if(isNull(value))
			throw new NullPointerException("Can't calculate length for 'null' value");

		if(value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long)
			return Long.toString(value.longValue()).length();
		if(value instanceof Float || value instanceof Double)
			return Double.toString(value.doubleValue()).length();

		assert false : "Unsupported Template";
		return 0;
	}

	public static int getLastDigits(int value, int digitNumber)
	{
		if(digitNumber <= 0)
			return value;

		String str = "" + value;
		int length = str.length();

		if(length <= digitNumber)
			return value;

		return parseInt(str.substring(length - digitNumber));
	}

	public static <T extends Number> double getRange(T minimum, T maximum)
	{
		if(isLessOrEqual(minimum, maximum))
			return maximum.doubleValue() - minimum.doubleValue();

		throw new IllegalArgumentException("mininum and/or maximum is not correct");
	}

	public static <T extends Number> String expandToLimitWithChar(T value)
	{
		return expandToLimitWithChar(value, -1, DEFAULT_CHAR);
	}

	public static <T extends Number> String expandToLimitWithChar(T value, int maxLength)
	{
		return expandToLimitWithChar(value, maxLength, DEFAULT_CHAR);
	}

	public static <T extends Number> String expandToLimitWithChar(T value, int maxLength, char ch)
	{
		if(isNull(value))
			throw new NullPointerException("Can't expand 'null' value");

		String res = null;

		if(value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long)
		{
			long longValue = value.longValue();
			long newValue = isLess(value, 0) ? abs(longValue) : longValue;

			res = StringExtension.resizeString("" + newValue, maxLength, ch);
		}
		else if(value instanceof Float || value instanceof Double)
		{
			double doubleValue = value.doubleValue();
			double newValue = isLess(value, 0) ? abs(doubleValue) : doubleValue;

			res = StringExtension.resizeString("" + newValue, maxLength, ch);
		}
		else
			assert false : "Unsupported Template";

		return (isLess(value, 0) ? "-" : "") + res;
	}

	public static boolean checkValue(Integer value, DecimalFormat df, Integer minimum, Integer maximum)
	{
		if(isNull(value))
			return false;

		if(value < 0)
			return checkNegativeValue(value, minimum, maximum);

		return checkPositiveValue(value, minimum, maximum);
	}

	public static boolean checkNegativeValue(Integer value, Integer minimum, Integer maximum)
	{
		if(isNull(minimum))
			return true;

		String str = "" + value;

		if(str.length() >= countLength(minimum) && !isInRangeMinMax(value, minimum, maximum))
			return false;

		return true;
	}

	public static boolean checkPositiveValue(Integer value, Integer minimum, Integer maximum)
	{
		if(isNull(maximum))
			return true;

		String str = "" + value;

		if(str.length() >= countLength(maximum) && !isInRangeMinMax(value, minimum, maximum))
			return false;

		return true;
	}
}
