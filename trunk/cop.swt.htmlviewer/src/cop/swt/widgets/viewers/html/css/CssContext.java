/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id:$
 * $HeadURL:$
 */
package cop.swt.widgets.viewers.html.css;

import java.io.IOException;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public final class CssContext extends CssSet
{
	/*
	 * IAppendable
	 */

	@Override
	public Appendable append(Appendable buf) throws IOException
	{
		if(buf == null || isEmpty())
			return buf;

		buf.append("style=\"");
		super.append(buf).append("\"");

		return buf;
	}
}