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

import cop.swt.widgets.viewers.html.css.enums.CssPropertyEnum;
import cop.swt.widgets.viewers.html.interfaces.IAppendable;
import cop.swt.widgets.viewers.html.interfaces.IName;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public class CssStyle implements IAppendable {
	private final Map<CssPropertyEnum, String> properties = new HashMap<CssPropertyEnum, String>();

	public void add(CssPropertyEnum property, IName value) {
		properties.put(property, value.getName());
	}

	public void add(CssPropertyEnum property, String value1, IName value2) {
		properties.put(property, value1 + value2.getName());
	}

	public String get(CssPropertyEnum property) {
		return properties.get(property);
	}

	public void clear() {
		properties.clear();
	}

	/*
	 * Object
	 */

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();

		for (Map.Entry<CssPropertyEnum, String> one : properties.entrySet())
			buf.append(one.getKey().getName()).append(":").append(one.getValue()).append(";");

		return buf.toString();
	}

	/*
	 * IAppendable
	 */

	@Override
	public boolean isEmpty() {
		return properties.isEmpty();
	}

	@Override
	public StringBuilder append(StringBuilder buf) {
		if (buf == null || isEmpty())
			return buf;

		for (Map.Entry<CssPropertyEnum, String> one : properties.entrySet())
			buf.append(one.getKey().getName()).append(":").append(one.getValue()).append(";");

		return buf;
	}
}
