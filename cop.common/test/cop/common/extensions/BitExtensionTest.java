/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.common.extensions;

import static cop.common.extensions.BitExtension.BIT0;
import static cop.common.extensions.BitExtension.BIT1;
import static cop.common.extensions.BitExtension.BIT2;
import static cop.common.extensions.BitExtension.BIT3;
import static cop.common.extensions.BitExtension.BIT4;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author <a href="mailto:abba-bestl@mail.ru">Cherednik, Oleg</a>
 */
public class BitExtensionTest
{
	@Test
	public void testIsBitSet()
	{
		assertTrue(BitExtension.isBitSet(BIT1, BIT1));
		assertTrue(BitExtension.isBitSet(BIT3, BIT3));
		assertTrue(BitExtension.isBitSet(BIT1 | BIT3, BIT1 | BIT3));

		assertFalse(BitExtension.isBitSet(BIT1 | BIT3, BIT0));
		assertFalse(BitExtension.isBitSet(BIT1 | BIT3, BIT2));
		assertFalse(BitExtension.isBitSet(BIT1 | BIT3, BIT2 | BIT4));
		assertFalse(BitExtension.isBitSet(BIT1 | BIT3, BIT1 | BIT2));
	}

	@Test
	public void testIsAnyBitSet()
	{
		assertTrue(BitExtension.isAnyBitSet(BIT1, BIT1));
		assertTrue(BitExtension.isAnyBitSet(BIT3, BIT3));
		assertTrue(BitExtension.isAnyBitSet(BIT1 | BIT3, BIT1 | BIT3));
		assertTrue(BitExtension.isAnyBitSet(BIT1 | BIT3, BIT1 | BIT2));

		assertFalse(BitExtension.isAnyBitSet(BIT1 | BIT3, BIT0));
		assertFalse(BitExtension.isAnyBitSet(BIT1 | BIT3, BIT2));
		assertFalse(BitExtension.isAnyBitSet(BIT1 | BIT3, BIT0 | BIT2));
	}

	@Test
	public void testIsBitClear()
	{
		assertTrue(BitExtension.isBitClear(BIT1, BIT0));
		assertTrue(BitExtension.isBitClear(BIT1 | BIT3, BIT2));
		assertTrue(BitExtension.isBitClear(BIT1 | BIT3, BIT0 | BIT2));

		assertFalse(BitExtension.isBitClear(BIT1 | BIT3, BIT1));
		assertFalse(BitExtension.isBitClear(BIT1 | BIT3, BIT1 | BIT3));
		assertFalse(BitExtension.isBitClear(BIT1 | BIT3, BIT1 | BIT2));
	}
}
