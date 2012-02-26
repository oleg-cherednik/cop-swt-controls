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
import cop.swt.widgets.viewers.html.css.CssGroup;
import cop.swt.widgets.viewers.html.css.CssId;
import cop.swt.widgets.viewers.html.css.CssStyleList;
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

	private static final String MACRO_SECTION = "section";
	private static final String MACRO_REPORTER = "reporter";
	private static final String MACRO_TITLE = "title";
	private static final String MACRO_DATE = "date";
	private static final String MACRO_NOTE = "note";
	private static final String MACRO_BODY = "text";

	private static final DateFormat df = DateFormat.getDateTimeInstance(SHORT, SHORT);

	/*
	 * AbstractHtmlTemplate
	 */

	@Override
	protected String getMacroValue(String macro, NewsTO news) {
		if (isEmpty(macro) || news == null)
			return macro;

		if (macro.equalsIgnoreCase(MACRO_REPORTER))
			return news.getReporter();
		if (macro.equalsIgnoreCase(MACRO_TITLE))
			return news.getTitle();
		if (macro.equalsIgnoreCase(MACRO_DATE))
			return df.format(news.getDate().getTime());
		if (macro.equalsIgnoreCase(MACRO_NOTE))
			return news.getNote();
		if (macro.equalsIgnoreCase(MACRO_BODY))
			return news.getBody();
		if (macro.equalsIgnoreCase(MACRO_SECTION))
			return news.getSection();

		return macro;
	}

	@Override
	protected String getTemplate() {
		StringBuilder buf = new StringBuilder(255);
		HtmlContext context = new HtmlContext();

		cssDate(buf, context).append(", ");
		cssSection(buf, context).append(" ");
		cssReporter(buf, context).append(HTML_TAG_NEW_LINE + "\n");
		cssTitle(buf, context).append(HTML_TAG_NEW_LINE + "\n");
		cssNote(buf, context).append("\n" + HTML_TAG_PARAGRAPH + "\n");
		buf.append(getMacro(MACRO_BODY));

		return buf.toString();
	}

	private static CssStyleList createStyles() {
		CssStyleList styles = new CssStyleList();

		styles.add(styleSection());
		styles.add(styleReporter());
		styles.add(styleTitle());
		styles.add(styleDate());
		styles.add(styleNote());
		styles.add(styleBody());

		return styles;
	}

	private static CssGroup styleSection() {
		CssId cssSection = createCssId(MACRO_SECTION);

		cssSection.add(CSS_FONT_WEIGHT, BOLD);
		cssSection.add(CSS_TEXT_COLOR, RED);

		return cssSection;
	}

	private static CssGroup styleReporter() {
		CssId cssReporter = createCssId(MACRO_REPORTER);

		cssReporter.add(CSS_FONT_STYLE, ITALIC);
		cssReporter.add(CSS_FONT_SIZE, "12", PT);

		return cssReporter;
	}

	private static CssGroup styleTitle() {
		CssId cssTitle = createCssId(MACRO_TITLE);

		cssTitle.add(CSS_FONT_SIZE, "16", PT);

		return cssTitle;
	}

	private static CssGroup styleDate() {
		CssId cssDate = createCssId(MACRO_DATE);

		cssDate.add(CSS_FONT_STYLE, ITALIC);
		cssDate.add(CSS_TEXT_COLOR, BLACK);
		cssDate.add(CSS_TEXT_DECORATION, UNDERLINE);

		return cssDate;
	}

	private static CssGroup styleNote() {
		CssId cssNote = createCssId(MACRO_NOTE);

		cssNote.add(CSS_FONT_STYLE, ITALIC);
		cssNote.add(CSS_TEXT_COLOR, GRAY);
		cssNote.add(CSS_FONT_SIZE, "12", PT);

		return cssNote;
	}

	private static CssGroup styleBody() {
		return createCssId(MACRO_BODY);
	}

	private StringBuilder cssDate(StringBuilder buf, HtmlContext context) {
		context.clear();

		CssContext cssDate = context.getStyle();

		cssDate.add(CSS_FONT_STYLE, ITALIC);
		cssDate.add(CSS_TEXT_COLOR, BLACK);
		cssDate.add(CSS_TEXT_DECORATION, UNDERLINE);

		HTML_TAG_SPAN.append(buf, getMacro(MACRO_DATE), context);

		return buf;
	}

	private StringBuilder cssSection(StringBuilder buf, HtmlContext context) {
		context.clear();
		context.setStyleId(MACRO_SECTION);
		HTML_TAG_SPAN.append(buf, getMacro(MACRO_SECTION), context);
		return buf;
	}

	private StringBuilder cssReporter(StringBuilder buf, HtmlContext context) {
		context.clear();
		context.setStyleId(MACRO_REPORTER);
		HTML_TAG_SPAN.append(buf, "(by " + getMacro(MACRO_REPORTER) + ")", context);
		return buf;
	}

	private StringBuilder cssTitle(StringBuilder buf, HtmlContext context) {
		context.clear();
		context.setStyleId(MACRO_TITLE);
		HTML_TAG_SPAN.append(buf, getMacro(MACRO_TITLE), context);
		return buf;
	}

	private StringBuilder cssNote(StringBuilder buf, HtmlContext context) {
		context.clear();
		context.setStyleId(MACRO_NOTE);
		HTML_TAG_SPAN.append(buf, "(" + getMacro(MACRO_NOTE) + ")", context);
		return buf;
	}

	/*
	 * HtmlTemplate
	 */

	@Override
	public HtmlDocument getHtmlDocument() {
		return new HtmlDocument(createStyles());
	}
}
