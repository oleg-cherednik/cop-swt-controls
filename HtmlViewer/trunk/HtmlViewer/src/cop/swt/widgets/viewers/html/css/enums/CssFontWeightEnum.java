/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.css.enums;

import cop.swt.widgets.viewers.html.interfaces.IName;

/**
 * @author Oleg Cherednik
 * @since 05.01.2011
 */
public enum CssFontWeightEnum implements IName {
	NORMAL("normal"),
	BOLD("bold"),
	BOLDER("bolder"),
	LIGHTER("lighter"),
	W100("100"),
	W200("200"),
	W300("300"),
	W400("400"),
	W500("500"),
	W600("600"),
	W700("700"),
	W800("800"),
	W900("900"),
	INHERIT("inherit");

	private String name;

	private CssFontWeightEnum(String name) {
		this.name = name;
	}

	/*
	 * IName
	 */

	@Override
	public String getName() {
		return name;
	}
}
