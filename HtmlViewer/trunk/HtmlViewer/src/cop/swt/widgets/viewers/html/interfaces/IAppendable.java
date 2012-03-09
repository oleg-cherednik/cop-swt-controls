/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.interfaces;

import java.io.IOException;

/**
 * @author Oleg Cherednik
 * @since 05.01.2011
 */
public interface IAppendable {
	boolean isEmpty();

	StringBuilder append(StringBuilder buf) throws IOException;
}