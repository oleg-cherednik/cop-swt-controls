/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.common.extensions;

import static cop.common.extensions.StringExtension.isEmpty;

import cop.common.enums.HtmlColorEnum;

/**
 * Class provides common methods that can be used within HTML context.<br>
 * This class contains only <i><u>static</u></i> methods. It can't be instantiated or inherited.<br>
 * For simple html tags {@link HtmlColorEnum} can be used.<br>
 * This class provides methods to use complex html tags with number of parameters.
 * 
 * @author cop (Cherednik, Oleg)
 * @version 1.0.0 (23.12.2009)
 * @see HtmlColorEnum
 */
public final class HtmlExtension
{
/**
	 * HTML <code>open</code> tag definition.<br>
	 * Default value: <b>'<'</b>
	 */
	public static final String HTML_TAG_OPEN = "<";
	/**
	 * HTML <code>close</code> tag definition.<br>
	 * Default value: <b>'>'</b>
	 */
	public static final String HTML_TAG_CLOSE = ">";
	/**
	 * HTML <code>encode</code> tag marker definition.<br>
	 * Default value: <b>'/'</b>
	 */
	public static final String HTML_TAG_END = "/";

	/**
	 * HTML <code>font</code> tag marker definition.<br>
	 * This tag sets properties for current font (color, size).<br>
	 * Default value: <b>'font'</b>
	 */
	public static final String HTML_TAG_FONT = "font";
	/**
	 * HTML <code>font</code> tag part <code>color</code> definition.<br>
	 * This tag defines font color property.<br>
	 * Default value: <b>'color'</b>
	 */
	private static final String HTML_TAG_FONT_COLOR = "color";
	/**
	 * HTML <code>font</code> tag part <code>size</code> definition.<br>
	 * This tag defines font size property. Default value: <b>'font'</b>
	 */
	@SuppressWarnings("unused")
    private static final String HTML_TAG_FONT_SIZE = "size";

	/**
	 * HTML <code>body</code> tag marker definition.<br>
	 * This tag sets properties for hole document. Default value: <b>'body'</b>
	 */
	private static final String HTML_TAG_BODY = "body";
	/**
	 * HTML <code>body</code> tag part <code>background color</code> definition.<br>
	 * This tag defines background color for current document. Default value: <b>'bgcolor'</b>
	 */
	private static final String HTML_TAG_BODY_BGCOLOR = "bgcolor";

	/**
	 * Closed constructor
	 */
	private HtmlExtension()
	{}

	/**
	 * Place <b>str</b> into <i>font</i> html tag with specific <b>color</b>.<br>
	 * If parameter <b>color</b> is not set correctly, than it'll be ignored.<br>
	 * <br>
	 * <code> String res = makeFontEffect("Example string", HTML_COLOR_BLUE);
	 * </code> <br>
	 * As result html browser will print: <font color="blue">Example string</font>
	 * <p>
	 * <b>Equivalents:</b>
	 * <ul>
	 * <li><code>makeFontEffect("Example string", null, ...); //size is not important in this
	 * context</code><br>
	 * </ul>
	 * 
	 * @param str string that will be placed into <b>font</b> html tag
	 * @param color text size
	 * @return html code
	 * @see String
	 * @see HtmlColorEnum
	 * @see #makeFontEffect(String, HtmlColorEnum, int)
	 */
	public static String makeFontEffect(String str, HtmlColorEnum color)
	{
		return makeFontEffect(str, color, -1);
	}

	/**
	 * Place <b>str</b> into <i>font</i> html tag with specific <b>size</b>.<br>
	 * If parameter <b>size</b> is not set correctly, than it'll be ignored.<br>
	 * <br>
	 * <code> String res = makeFontEffect("Example string", 2); </code> <br>
	 * As result html browser will print: <font size=2>Example string</font>
	 * <p>
	 * <b>Equivalents:</b>
	 * <ul>
	 * <li><code>makeFontEffect("Example string", ..., 2); //color is not important in this context</code><br>
	 * </ul>
	 * 
	 * @param str string that will be placed into <b>font</b> html tag
	 * @param size text size
	 * @return html code
	 * @see String
	 * @see #makeFontEffect(String, HtmlColorEnum, int)
	 */
	public static String makeFontEffect(String str, int size)
	{
		return makeFontEffect(str, null, size);
	}

	/**
	 * Place <b>str</b> into <i>font</i> html tag with specific <b>color</b> and <b>size</b>.<br>
	 * If each parameter <b>color</b> or <b>size</b> are not set correctly, than it'll be ignored.
	 * <p>
	 * <code> String res =
	 * makeFontEffect("Example string", HTML_COLOR_BLUE, 2); </code> <br>
	 * As result html browser will print: <font color="blue" size=2>Example string</font>
	 * 
	 * @param str string that will be placed into <b>font</b> html tag
	 * @param color text color
	 * @param size text size
	 * @return html code
	 * @see String
	 * @see HtmlColorEnum
	 */
	public static String makeFontEffect(String str, HtmlColorEnum color, int size)
	{
		if(isEmpty(str))
			return str;

		StringBuilder buf = new StringBuilder();

		buf.append(HTML_TAG_OPEN + HTML_TAG_FONT);

		if(color != null)
			buf.append(" " + HTML_TAG_FONT_COLOR + "=\"" + color.getName() + "\"");

		if(size > 0)
			buf.append(" " + getStyleFontSizePt(size));
		
//		if(size > 0)
//			buf.append(" " + HTML_TAG_FONT_SIZE + "=\"" + size + "px\"");

		buf.append(HTML_TAG_CLOSE);
		buf.append(str);
		buf.append(HTML_TAG_OPEN + HTML_TAG_END + HTML_TAG_FONT + HTML_TAG_CLOSE);

		return "" + buf;
	}

	// font.size is not eqwal with swt.size. We have to use 'style.font-size' to set size in pixels
	private static String getStyleFontSizePt(int size)
	{
		return "style=\"font-size:" + size + "pt;\"";
	}

	/**
	 * Use this method to set <b>background color</b> for html document.<br>
	 * 
	 * @param color background color
	 * @return html code
	 * @see HtmlColorEnum
	 */
	public static String makeBackgroundColor(HtmlColorEnum color)
	{
		if(color == null)
			return "";

		StringBuilder buf = new StringBuilder();

		buf.append(HTML_TAG_OPEN + HTML_TAG_BODY);
		buf.append(" " + HTML_TAG_BODY_BGCOLOR + "=\"" + color.getName() + "\"");
		buf.append(HTML_TAG_CLOSE);
		buf.append(HTML_TAG_OPEN + HTML_TAG_END + HTML_TAG_FONT + HTML_TAG_CLOSE);

		return "" + buf;
	}
}
