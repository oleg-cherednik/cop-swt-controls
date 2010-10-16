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
import static cop.common.extensions.BitExtension.clearBits;
import static cop.common.extensions.BitExtension.isAnyBitClear;
import static cop.common.extensions.BitExtension.isAnyBitSet;
import static cop.common.extensions.BitExtension.isBitClear;
import static cop.common.extensions.BitExtension.isBitSet;
import static cop.common.extensions.BitExtension.setBits;
import static org.junit.Assert.assertEquals;
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
		assertTrue(isBitSet(BIT1, BIT1));
		assertTrue(isBitSet(BIT3, BIT3));
		assertTrue(isBitSet(BIT1 | BIT3, BIT1 | BIT3));

		assertFalse(isBitSet(BIT1 | BIT3, BIT0));
		assertFalse(isBitSet(BIT1 | BIT3, BIT2));
		assertFalse(isBitSet(BIT1 | BIT3, BIT2 | BIT4));
		assertFalse(isBitSet(BIT1 | BIT3, BIT1 | BIT2));
	}

	@Test
	public void testIsAnyBitSet()
	{
		assertTrue(isAnyBitSet(BIT1, BIT1));
		assertTrue(isAnyBitSet(BIT3, BIT3));
		assertTrue(isAnyBitSet(BIT1 | BIT3, BIT1 | BIT3));
		assertTrue(isAnyBitSet(BIT1 | BIT3, BIT1 | BIT2));

		assertFalse(isAnyBitSet(BIT1 | BIT3, BIT0));
		assertFalse(isAnyBitSet(BIT1 | BIT3, BIT2));
		assertFalse(isAnyBitSet(BIT1 | BIT3, BIT0 | BIT2));
	}

	@Test
	public void testIsBitClear()
	{
		assertTrue(isBitClear(BIT1, BIT0));
		assertTrue(isBitClear(BIT1 | BIT3, BIT2));
		assertTrue(isBitClear(BIT1 | BIT3, BIT0 | BIT2));

		assertFalse(isBitClear(BIT1 | BIT3, BIT1));
		assertFalse(isBitClear(BIT1 | BIT3, BIT1 | BIT3));
		assertFalse(isBitClear(BIT1 | BIT3, BIT1 | BIT2));
	}

	@Test
	public void testIsAnyBitClear()
	{
		assertTrue(isAnyBitClear(BIT1, BIT0));
		assertTrue(isAnyBitClear(BIT1 | BIT3, BIT2));
		assertTrue(isAnyBitClear(BIT1 | BIT3, BIT0 | BIT2));
		assertTrue(isAnyBitClear(BIT1 | BIT3, BIT0 | BIT1));

		assertFalse(isAnyBitClear(BIT1 | BIT3, BIT1));
		assertFalse(isAnyBitClear(BIT1 | BIT3, BIT1 | BIT3));
	}

	@Test
	public void testSetBits()
	{
		assertEquals(32 | BIT1, setBits(32, BIT1));
		assertEquals(32 | BIT1 | BIT2, setBits(32, BIT1 | BIT2));
	}

	@Test
	public void testClearBits()
	{
		assertEquals(45, clearBits(45, BIT1));
		assertEquals(44, clearBits(45, BIT0));
		assertEquals(33, clearBits(45, BIT2 | BIT3));
	}
}
