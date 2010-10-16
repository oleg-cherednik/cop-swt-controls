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

	private BitExtension()
	{}

	/**
	 * Checks if all bits of giving bit set are set or not
	 * 
	 * @param value checked value
	 * @param bits checked bit or bit set.
	 * @return <code>true</code> if all selected bit(s) is set
	 */
	public static boolean isBitSet(int value, int bits)
	{
		return (value & bits) == bits;
	}

	/**
	 * Checks if any bits of giving bit set are set or not
	 * 
	 * @param value checked value
	 * @param bit checked bit or bit set.
	 * @return <code>true</code> if any of giving bit(s) is set
	 */
	public static boolean isAnyBitSet(int value, int bits)
	{
		return (value & bits) != 0;
	}

	/**
	 * Checks if all bits of giving bit set are clear or not
	 * 
	 * @param value checked value
	 * @param bit checked bit or bit set.
	 * @return <code>true</code> if all selected bit(s) is clear
	 */
	public static boolean isBitClear(int value, int bits)
	{
		return (value & bits) == 0;
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
