/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.common.extensions;

public final class BitExtension
{
	public static final int BIT0 = 0x1;
	public static final int BIT1 = 0x2;
	public static final int BIT2 = 0x4;
	public static final int BIT3 = 0x8;
	
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
