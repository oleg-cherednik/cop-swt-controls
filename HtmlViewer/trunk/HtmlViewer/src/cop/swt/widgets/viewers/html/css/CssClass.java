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
public final class CssClass extends CssGroup {
	private static final Map<String, CssClass> map = new HashMap<String, CssClass>();

	public static CssClass createCssClass(String name) {
		CssClass obj = map.get(name);

		if (obj == null)
			map.put(name, obj = new CssClass(name));

		return obj;
	}

	private CssClass(String name) {
		super(name, "");
	}
}
