/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.common.enums;

public enum HtmlColorEnum
{
	HTML_COLOR_BLACK("black", 0x0),
	HTML_COLOR_SILVER("silver", 0xC0C0C0),
	HTML_COLOR_GRAY("gray", 0x808080),
	HTML_COLOR_WHITE("white", 0xFFFFFF),
	HTML_COLOR_GREEN("green", 0x008000),
	HTML_COLOR_LIME("lime", 0x00FF00),
	HTML_COLOR_OLIVE("olive", 0x808000),
	HTML_COLOR_YELLOW("yellow", 0xFFFF00),
	HTML_COLOR_MAROON("maroon", 0x800000),
	HTML_COLOR_RED("red", 0xFF0000),
	HTML_COLOR_PURPLE("purple", 0x800080),
	HTML_COLOR_FUCHSIA("fuchsia", 0xFF00FF),
	HTML_COLOR_NAVY("navy", 0x000080),
	HTML_COLOR_BLUE("blue", 0x0000FF),
	HTML_COLOR_TEAL("teal", 0x008080),
	HTML_COLOR_AQUA("aqua", 0x00FFFF),
	HTML_COLOR_DEEPPINK("deeppink", 0xFF1493),
	HTML_COLOR_ALICEBLUE("aliceblue", 0xF0F8FF),
	HTML_COLOR_AZURE("azure", 0xF0FFFF),
	HTML_COLOR_BLANCHEDALMOND("blanchedalmond", 0xFFEBCD),
	HTML_COLOR_BURLYWOOD("burlywood", 0xFFEBCD),
	HTML_COLOR_CORAL("coral", 0xFF7F50),
	HTML_COLOR_CYAN("cyan", 0xFFEBCD),
	HTML_COLOR_DARKGRAY("darkgray", 0xA9A9A9),
	HTML_COLOR_DARKOLIVEGREEN("darkolivegreen", 0x556B2F),
	HTML_COLOR_DARKSALMON("darksalmon", 0xE9967A),
	HTML_COLOR_DARKTURQUOISE("darkturquoised", 0x00CED1),
	HTML_COLOR_DIMGRAY("dimgray", 0x696969),
	HTML_COLOR_FORESTGREEN("forestgreen", 0x228B22),
	HTML_COLOR_GOLD("gold", 0xFFD700),
	HTML_COLOR_GREENYELLOW("greenyellow", 0xADFF2F),
	HTML_COLOR_INDIGO("indigo", 0x4B0082),
	HTML_COLOR_LAVENDERBLUSH("lavenderblush", 0xFFF0F5),
	HTML_COLOR_LIGHTCORAL("lightcoral", 0xF08080),
	HTML_COLOR_LIGHTGREY("lightgrey", 0xD3D3D3),
	HTML_COLOR_LIGHTSKYBLUE("lightskyblue", 0x87CEFA),
	HTML_COLOR_MEDIUMPURPLE("mediumpurple", 0x9370D8),
	HTML_COLOR_MEDIUMTURQUOISE("mediumturquoise", 0x48D1CC),
	HTML_COLOR_MISTYROSE("mistyrose", 0xFFE4E1),
	HTML_COLOR_OLDLACE("oldlace", 0xFDF5E6),
	HTML_COLOR_ORANGERED("orangered", 0xFF4500),
	HTML_COLOR_PALETURQUOISE("paleturquoise", 0xAFEEEE),
	HTML_COLOR_PERU("peru", 0xCD853F),
	HTML_COLOR_SADDLEBROWN("saddlebrown", 0x8B4513),
	HTML_COLOR_SEASHELL("seashell", 0xFFF5EE),
	HTML_COLOR_SLATEBLUE("slateblue", 0x6A5ACD),
	HTML_COLOR_STEELBLUE("steelblue", 0x4682B4),
	HTML_COLOR_TOMATO("tomato", 0xFF6347);

	private String name;
	private int number;

	private HtmlColorEnum(String name, int number)
	{
		this.name = name;
		this.number = number;
	}

	public String getName()
	{
		return name;
	}

	public int getNumber()
	{
		return number;
	}
}
