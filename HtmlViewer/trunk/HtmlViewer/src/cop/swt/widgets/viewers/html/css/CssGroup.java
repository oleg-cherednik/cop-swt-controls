/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.css;

/**
 * @author Oleg Cherednik
 * @since 05.01.2011
 */
public abstract class CssGroup extends CssSet {
	private final String name;
	private final String marker;

	public CssGroup(String name, String marker) {
		this.name = name;
		this.marker = marker;
	}

	/*
	 * IAppendable
	 */

	@Override
	public StringBuilder append(StringBuilder buf) {
		if (buf == null || isEmpty())
			return buf;

		buf.append(marker).append(name).append("{");
		super.append(buf).append("}");

		return buf;
	}
}
