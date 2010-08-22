/**
 * @licence GNU Leser General Public License
 *
 * $Id: BitExtension.java 47 2010-08-16 12:19:28Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.common/cop.common/src/cop/common/extensions/BitExtension.java $
 */
package cop.common.extensions;

public final class BitExtension
{
	private BitExtension()
	{}

	public static boolean isBitSet(int value, int bit)
	{
		return (value & bit) == bit;
	}

	public static boolean isAnyBitSet(int value, int bits)
	{
		return (value & bits) != 0;
	}

	public static boolean isBitClear(int value, int bit)
	{
		return !isBitSet(value, bit);
	}

	public static int clearBits(int value, int bits)
	{
		return value & ~bits;
	}

	public static int setBits(int value, int bits)
	{
		return value | bits;
	}
}