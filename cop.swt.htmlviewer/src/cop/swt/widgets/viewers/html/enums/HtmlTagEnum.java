package cop.swt.widgets.viewers.html.enums;

import static cop.common.extensions.ArrayExtension.copyOfRange;
import static cop.common.extensions.HtmlExtension.HTML_TAG_CLOSE;
import static cop.common.extensions.HtmlExtension.HTML_TAG_END;
import static cop.common.extensions.HtmlExtension.HTML_TAG_OPEN;
import static cop.common.extensions.StringExtension.isEmpty;
import cop.common.extensions.ArrayExtension;
import cop.swt.widgets.viewers.html.interfaces.IHtmlTag;

public enum HtmlTagEnum implements IHtmlTag
{
	HTML_TAG_BOLD("b"),
	HTML_TAG_ITALIC("i"),
	HTML_TAG_UNDERLINE("u"),
	HTML_TAG_STRIKEOUT("s"),
	HTML_TAG_BIG("big"),
	HTML_TAG_SMALL("small"),
	HTML_TAG_INDEX_SUP("sup"),
	HTML_TAG_INDEX_SUB("sub"),
	HTML_TGA_MONO("tt"),
	HTML_TAG_PARAGRAPH("p"),
	HTML_TAG_CENTER("center"),
	HTML_TAG_NEW_LINE("br");

	private String tag;

	private HtmlTagEnum(String tag)
	{
		this.tag = tag;
	}

	@Override
	public String open()
	{
		return HTML_TAG_OPEN + tag + HTML_TAG_CLOSE;
	}

	@Override
	public String close()
	{
		return HTML_TAG_OPEN + HTML_TAG_END + tag + HTML_TAG_CLOSE;
	}

	public static String makeEffect(String text, IHtmlTag... effects)
	{
		if(isEmpty(text) || ArrayExtension.isEmpty(effects))
			return text;

		IHtmlTag effect = effects[0];
		IHtmlTag[] tt = copyOfRange(effects, 1);

		return makeEffect(effect.open() + text + effect.close(), tt);
	}
}
