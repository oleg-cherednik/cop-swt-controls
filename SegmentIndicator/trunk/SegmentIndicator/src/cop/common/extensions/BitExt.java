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
public final class BitExt
{
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
}
