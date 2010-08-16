/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.common.predicates.comparators;

import java.util.Comparator;


/**
 * @author <a href="mailto:abba-bestl@mail.ru">Cherednik, Oleg</a>
 */
public final class LessOrEqual<T> extends AbstractComparatorPredicate<T>
{
	public LessOrEqual(Comparator<T> cmp)
	{
		super(cmp);
	}

	/*
	 * ComparatorPredicate
	 */

	@Override
	public boolean check(T obj1, T obj2)
	{
		return cmp.compare(obj1, obj2) <= 0;
	}
}
