/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.common.extensions;

/**
 * Class provides different methods to work with bits.
 * 
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 16.08.2010
 */
public final class BitExtension
{
	public static final int BIT0 = 0x1;
	public static final int BIT1 = 0x2;
	public static final int BIT2 = 0x4;
	public static final int BIT3 = 0x8;
	public static final int BIT4 = 0x10;
	public static final int BIT5 = 0x20;
	public static final int BIT6 = 0x40;
	public static final int BIT7 = 0x80;

	public static final int BIT8 = 0x100;
	public static final int BIT9 = 0x200;
	public static final int BIT10 = 0x400;
	public static final int BIT11 = 0x800;
	public static final int BIT12 = 0x1000;
	public static final int BIT13 = 0x2000;
	public static final int BIT14 = 0x4000;
	public static final int BIT15 = 0x8000;

	private static final int[] TMP_LOWEST = { 0xFFFF, 0x00FF, 0x000F, 0x0003, 0x0001 };
	private static final int[] TMP_HIGHEST = { 0xFFFF0000, 0x0000FF00, 0x000000F0, 0x0000000C, 0x00000002 };

	private static final int G31 = 0x49249249; // 0100_1001_0010_0100_1001_0010_0100_1001
	private static final int G32 = 0x381c0e07; // 0011_1000_0001_1100_0000_1110_0000_0111

	private BitExtension()
	{}

	/**
	 * Checks if all bits of giving bit set are set or not
	 * 
	 * @param value checked value
	 * @param bits checked bit or bit set
	 * @return <code>true</code> if all selected bit(s) are set
	 */
	public static boolean isBitSet(int value, int bits)
	{
		return (value & bits) == bits;
	}

	/**
	 * Checks if any bits of giving bit set are set or not
	 * 
	 * @param value checked value
	 * @param bit checked bit or bit set
	 * @return <code>true</code> if any of giving bit(s) are set
	 */
	public static boolean isAnyBitSet(int value, int bits)
	{
		return (value & bits) != 0;
	}

	/**
	 * Checks if all bits of giving bit set are clear or not
	 * 
	 * @param value checked value
	 * @param bit checked bit or bit set
	 * @return <code>true</code> if all selected bit(s) are clear
	 */
	public static boolean isBitClear(int value, int bits)
	{
		return (value & bits) == 0;
	}

	/**
	 * Checks if any bits of giving bit set are clear or not
	 * 
	 * @param value checked value
	 * @param bit checked bit or bit set
	 * @return <code>true</code> if any selected bit(s) are clear
	 */
	public static boolean isAnyBitClear(int value, int bits)
	{
		return (~value & bits) != 0;
	}

	/**
	 * Set selected bit(s) in giving value
	 * 
	 * @param value value
	 * @param bit bit or bit set to set in the value
	 * @return <code>value</code> with set selected bits
	 */
	public static int setBits(int value, int bits)
	{
		return value | bits;
	}

	/**
	 * Clear selected bit(s) in giving value
	 * 
	 * @param value value
	 * @param bit bit or bit set to clear in the value
	 * @return <code>value</code> with cleared selected bits
	 */
	public static int clearBits(int value, int bits)
	{
		return value & ~bits;
	}

	/**
	 * Get lowest set bit number of the given value
	 * 
	 * @param value value
	 * @return number of the lowest set bit
	 */
	public static int getLowestSetBitNumber(int value)
	{
		int k = 0;

		for(int i = 0, j = 16; i < 5; i++, j /= 2)
		{
			if((value & TMP_LOWEST[i]) != 0)
				continue;

			k += j;
			value >>= j;
		}

		return k;
	}

	/**
	 * Get highest set bit number of the given value
	 * 
	 * @param value value
	 * @return number of the highest set bit
	 */
	public static int getHighestSetBitNumber(int value)
	{
		int k = 0;

		for(int i = 0, j = 16; i < 5; i++, j /= 2)
		{
			if((value & TMP_HIGHEST[i]) == 0)
				continue;

			k += j;
			value >>= j;
		}

		return k;
	}

	/**
	 * Get amount of set bits of the giving value.<br>
	 * Performance: log2(value)
	 * 
	 * @param value value
	 * @return amount of the set bits
	 */
	public static int getSetBitAmount(int value)
	{
		value = (value & G31) + ((value >> 1) & G31) + ((value >> 2) & G31);
		value = ((value + (value >> 3)) & G32) + ((value >> 6) & G32);

		return (value + (value >> 9) + (value >> 18) + (value >> 27)) & 0x3f;
	}

	/**
	 * Returns lowest set bit of the giving value
	 * 
	 * @param value value
	 * @return lowest set bit
	 */
	public static int getLowestSetBit(int value)
	{
		return ~(value - 1) & value;
	}

	/**
	 * Clears lowest set bit in giving value
	 * 
	 * @param value value
	 * @return <code>value</code> where lowest set bit is clearesd
	 */
	public static int clearLowestSetBit(int value)
	{
		return (value - 1) & value;
	}

	/**
	 * Checks if giving value is power of 2 or not
	 * 
	 * @param value value
	 * @return <code>true</code> if giving value is power of 2
	 */
	public static boolean isPowerOf2(int value)
	{
		return ((value - 1) & value) == 0;
	}
}
