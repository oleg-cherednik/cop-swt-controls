/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.example.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public final class SimpleGenerator extends Thread {
	private static final int SIZE = 200;
	public static final List<String> lines = new ArrayList<String>(SIZE);

	static {
		for (int i = 0; i < SIZE; i++)
			lines.add("This is a line number " + (i + 1));
	}

	/*
	 * Runnable
	 */

	@Override
	public void run() {}
}
