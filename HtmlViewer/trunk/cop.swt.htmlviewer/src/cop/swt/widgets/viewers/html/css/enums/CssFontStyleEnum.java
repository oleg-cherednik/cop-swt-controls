/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id:$
 * $HeadURL:$
 */
package cop.swt.widgets.viewers.html.css.enums;

import cop.swt.widgets.viewers.html.interfaces.IName;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public enum CssFontStyleEnum implements IName
{
	NORMAL("normal"),
	ITALIC("italic"),
	OBLIQUE("oblique"),
	INHERIT("inherit");

	private String name;

	private CssFontStyleEnum(String name)
	{
		this.name = name;
	}

	/*
	 * IName
	 */

	@Override
	public String getName()
	{
		return name;
	}
}
