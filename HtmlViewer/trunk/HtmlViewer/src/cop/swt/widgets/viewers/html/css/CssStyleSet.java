/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.css;

import static cop.swt.widgets.viewers.html.HtmlExtension.printBuffer;

import java.util.HashSet;
import java.util.Set;

import cop.swt.widgets.viewers.html.interfaces.IAppendable;

/**
 * @author Oleg Cherednik
 * @since 05.01.2011
 */
public class CssStyleSet implements IAppendable {
	private final Set<CssSet> groups = new HashSet<CssSet>();

	public CssStyleSet() {}

	public void add(CssSet group) {
		if (group != null && !group.isEmpty())
			groups.add(group);
	}

	public boolean isEmpty() {
		for (CssSet group : groups)
			if (!group.isEmpty())
				return false;

		return true;
	}

	/*
	 * IAppendable
	 */

	@Override
	public StringBuilder append(StringBuilder buf) {
		if (buf == null || groups.isEmpty())
			return buf;

		boolean newLine = false;

		for (CssSet group : groups) {
			if (group.isEmpty())
				continue;

			if (newLine)
				buf.append("\n");
			else
				newLine = true;

			group.append(buf);
		}

		return buf;
	}

	@Override
	public String toString() {
		return printBuffer(this);
	}
}
