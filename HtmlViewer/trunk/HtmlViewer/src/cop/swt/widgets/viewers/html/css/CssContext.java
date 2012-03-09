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
public final class CssContext extends CssSet {
	/*
	 * IAppendable
	 */

	@Override
	public StringBuilder append(StringBuilder buf) {
		if (buf == null || isEmpty())
			return buf;

		buf.append("style=\"");
		super.append(buf).append("\"");

		return buf;
	}
}
