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
public abstract class CssGroup extends CssSet
{
	private final String name;
	private final String marker;

	public CssGroup(String name, String marker)
	{
		this.name = name;
		this.marker = marker;
	}

	/*
	 * IAppendable
	 */

	@Override
	public Appendable append(Appendable buf) throws IOException
	{
		if(buf == null || isEmpty())
			return buf;

		buf.append(marker).append(name).append("{");
		super.append(buf).append("}");

		return buf;
	}
}
