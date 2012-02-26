/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.css;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public final class CssId extends CssGroup {
	private static final Map<String, CssId> map = new HashMap<String, CssId>();

	public static CssId createCssId(String name) {
		CssId obj = map.get(name);

		if (obj == null)
			map.put(name, obj = new CssId(name));

		return obj;
	}

	private CssId(String name) {
		super(name, "#");
	}
}
