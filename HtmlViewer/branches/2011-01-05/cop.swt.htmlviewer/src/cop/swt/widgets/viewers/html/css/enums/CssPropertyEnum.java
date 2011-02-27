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
public enum CssPropertyEnum implements IName
{
	CSS_TEXT_ALIGN("text-align"),
	CSS_TEXT_COLOR("color"),
	CSS_TEXT_DECORATION("text-decoration"),

	CSS_FONT_SIZE("font-size"),
	CSS_FONT_WEIGHT("font-weight"),
	CSS_FONT_STYLE("font-style"),
	
	CSS_ID("id");

	private String name;

	private CssPropertyEnum(String name)
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
