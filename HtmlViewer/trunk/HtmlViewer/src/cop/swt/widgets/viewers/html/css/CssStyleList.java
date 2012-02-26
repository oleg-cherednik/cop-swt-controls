/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.css;

import static cop.swt.widgets.viewers.html.HtmlExtension.printBuffer;

import java.util.ArrayList;
import java.util.List;

import cop.swt.widgets.viewers.html.interfaces.IAppendable;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public class CssStyleList implements IAppendable {
	private final List<CssGroup> groups = new ArrayList<CssGroup>();

	public CssStyleList() {}

	public void add(CssGroup group) {
		if (group != null && !group.isEmpty())
			groups.add(group);
	}

	public boolean isEmpty() {
		if (groups.isEmpty())
			return true;

		for (CssGroup group : groups)
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

		for (CssGroup group : groups) {
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
