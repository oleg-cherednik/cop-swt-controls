/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.example.news;

import static cop.common.extensions.StringExtension.isEmpty;
import static cop.swt.widgets.viewers.html.css.CssId.createCssId;
import static cop.swt.widgets.viewers.html.css.enums.CssFontSizeEnum.PT;
import static cop.swt.widgets.viewers.html.css.enums.CssFontStyleEnum.ITALIC;
import static cop.swt.widgets.viewers.html.css.enums.CssFontWeightEnum.BOLD;
import static cop.swt.widgets.viewers.html.css.enums.CssPropertyEnum.CSS_FONT_SIZE;
import static cop.swt.widgets.viewers.html.css.enums.CssPropertyEnum.CSS_FONT_STYLE;
import static cop.swt.widgets.viewers.html.css.enums.CssPropertyEnum.CSS_FONT_WEIGHT;
import static cop.swt.widgets.viewers.html.css.enums.CssPropertyEnum.CSS_TEXT_COLOR;
import static cop.swt.widgets.viewers.html.css.enums.CssPropertyEnum.CSS_TEXT_DECORATION;
import static cop.swt.widgets.viewers.html.css.enums.CssTextDecorationEnum.UNDERLINE;
import static cop.swt.widgets.viewers.html.enums.HtmlColorEnum.BLACK;
import static cop.swt.widgets.viewers.html.enums.HtmlColorEnum.GRAY;
import static cop.swt.widgets.viewers.html.enums.HtmlColorEnum.RED;
import static java.text.DateFormat.SHORT;

import java.text.DateFormat;

import cop.swt.widgets.viewers.html.HtmlContext;
import cop.swt.widgets.viewers.html.HtmlTag;
import cop.swt.widgets.viewers.html.css.CssContext;
import cop.swt.widgets.viewers.html.css.CssSet;
import cop.swt.widgets.viewers.html.css.CssStyleSet;
import cop.swt.widgets.viewers.html.document.HtmlDocument;
import cop.swt.widgets.viewers.html.templates.HtmlTemplate;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public class NewsHtmlTemplate extends HtmlTemplate<NewsTO> {
	private static final HtmlTag HTML_TAG_NEW_LINE = HtmlTag.create("br");
	private static final HtmlTag HTML_TAG_PARAGRAPH = HtmlTag.create("p");
	private static final HtmlTag HTML_TAG_SPAN = HtmlTag.create("span");
	private static final DateFormat df = DateFormat.getDateTimeInstance(SHORT, SHORT);

	/*
	 * AbstractHtmlTemplate
	 */

	@Override
	protected boolean setMacroValue(StringBuilder buf, String name, NewsTO obj) {
		MacroEnum macro = MacroEnum.parseString(name);

		if (macro != null)
			macro.setMacroValue(buf, obj);

		return macro != null;
	}

	@Override
	protected String getTemplate() {
		StringBuilder buf = new StringBuilder(255);
		HtmlContext context = new HtmlContext();

		MacroEnum.DATE.add(buf, context, this).append(", ");
		MacroEnum.SECTION.add(buf, context, this).append(" ");
		MacroEnum.REPORTER.add(buf, context, this).append(HTML_TAG_NEW_LINE).append("\n");
		MacroEnum.TITLE.add(buf, context, this).append(HTML_TAG_NEW_LINE).append("\n");
		MacroEnum.NOTE.add(buf, context, this).append("\n").append(HTML_TAG_PARAGRAPH).append("\n");
		MacroEnum.BODY.add(buf, context, this);

		return buf.toString();
	}

	/*
	 * HtmlTemplate
	 */

	@Override
	public HtmlDocument getHtmlDocument() {
		return new HtmlDocument(MacroEnum.createCssStyles());
	}

	/*
	 * enum
	 */

	private enum MacroEnum {
		SECTION("section") {
			@Override
			public CssSet createCss() {
				CssSet css = super.createCss();

				css.add(CSS_FONT_WEIGHT, BOLD);
				css.add(CSS_TEXT_COLOR, RED);

				return css;
			}

			@Override
			public StringBuilder add(StringBuilder buf, HtmlContext context, NewsHtmlTemplate template) {
				context.setStyleId(getName());
				HTML_TAG_SPAN.append(buf, getMacro(template), context);
				return buf;
			}

			@Override
			public StringBuilder setMacroValue(StringBuilder buf, NewsTO obj) {
				return buf.append(obj.getSection());
			}
		},
		REPORTER("reporter") {
			@Override
			public CssSet createCss() {
				CssSet css = super.createCss();

				css.add(CSS_FONT_STYLE, ITALIC);
				css.add(CSS_FONT_SIZE, "12", PT);

				return css;
			}

			@Override
			public StringBuilder add(StringBuilder buf, HtmlContext context, NewsHtmlTemplate template) {
				context.setStyleId(getName());
				HTML_TAG_SPAN.append(buf, "(by " + getMacro(template) + ")", context);
				return buf;
			}

			@Override
			public StringBuilder setMacroValue(StringBuilder buf, NewsTO obj) {
				return buf.append(obj.getReporter());
			}
		},
		TITLE("title") {
			@Override
			public CssSet createCss() {
				CssSet css = super.createCss();

				css.add(CSS_FONT_SIZE, "16", PT);

				return css;
			}

			@Override
			public StringBuilder add(StringBuilder buf, HtmlContext context, NewsHtmlTemplate template) {
				context.setStyleId(getName());
				HTML_TAG_SPAN.append(buf, getMacro(template), context);
				return buf;
			}

			@Override
			public StringBuilder setMacroValue(StringBuilder buf, NewsTO obj) {
				return buf.append(obj.getTitle());
			}
		},
		DATE("date") {
			@Override
			public CssSet createCss() {
				CssSet css = super.createCss();

				css.add(CSS_FONT_STYLE, ITALIC);
				css.add(CSS_TEXT_COLOR, BLACK);
				css.add(CSS_TEXT_DECORATION, UNDERLINE);

				return css;
			}

			@Override
			public StringBuilder add(StringBuilder buf, HtmlContext context, NewsHtmlTemplate template) {
				context.setStyleId(getName());

				CssContext cssDate = context.getStyle();

				cssDate.add(CSS_FONT_STYLE, ITALIC);
				cssDate.add(CSS_TEXT_COLOR, BLACK);
				cssDate.add(CSS_TEXT_DECORATION, UNDERLINE);

				HTML_TAG_SPAN.append(buf, getMacro(template), context);

				return buf;
			}

			@Override
			public StringBuilder setMacroValue(StringBuilder buf, NewsTO obj) {
				return buf.append(df.format(obj.getDate().getTime()));
			}
		},
		NOTE("note") {
			@Override
			public CssSet createCss() {
				CssSet css = super.createCss();

				css.add(CSS_FONT_STYLE, ITALIC);
				css.add(CSS_TEXT_COLOR, GRAY);
				css.add(CSS_FONT_SIZE, "12", PT);

				return css;
			}

			@Override
			public StringBuilder add(StringBuilder buf, HtmlContext context, NewsHtmlTemplate template) {
				context.setStyleId(getName());
				HTML_TAG_SPAN.append(buf, "(" + getMacro(template) + ")", context);
				return buf;
			}

			@Override
			public StringBuilder setMacroValue(StringBuilder buf, NewsTO obj) {
				return buf.append(obj.getNote());
			}
		},
		BODY("text") {
			@Override
			public StringBuilder setMacroValue(StringBuilder buf, NewsTO obj) {
				return buf.append(obj.getBody());
			}
		};

		private final String name;

		MacroEnum(String name) {
			this.name = name;
		}

		public CssSet createCss() {
			return createCssId(name);
		}

		public final String getName() {
			return name;
		}

		protected final String getMacro(NewsHtmlTemplate template) {
			return template.getMacro(name);
		}

		public StringBuilder add(StringBuilder buf, HtmlContext context, NewsHtmlTemplate template) {
			return buf.append(getMacro(template));
		}

		public StringBuilder setMacroValue(StringBuilder buf, NewsTO obj) {
			return buf;
		}

		/*
		 * static
		 */

		public static CssStyleSet createCssStyles() {
			CssStyleSet styles = new CssStyleSet();

			for (MacroEnum macro : values())
				styles.add(macro.createCss());

			return styles;
		}

		public static MacroEnum parseString(String name) {
			if (!isEmpty(name))
				for (MacroEnum macro : values())
					if (macro.name.equals(name))
						return macro;

			return null;
		}
	}
}
