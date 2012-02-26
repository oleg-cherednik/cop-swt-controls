/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
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
public final class BitExtension {
	/**
	 * Clear selected bit(s) in giving value
	 * 
	 * @param value value
	 * @param bit bit or bit set to clear in the value
	 * @return <code>value</code> with cleared selected bits
	 */
	public static int clearBits(int value, int bits) {
		return value & ~bits;
	}
}
