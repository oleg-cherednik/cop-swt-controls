/**
 * GNU Lesser General Public License
 */
package cop.swt.widgets.viewers.html;

import static cop.common.extensions.BitExtension.clearBits;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isEmpty;
import static cop.swt.widgets.viewers.html.enums.HtmlTagEnum.HTML_TAG_NEW_LINE;
import static org.eclipse.swt.SWT.H_SCROLL;
import static org.eclipse.swt.SWT.V_SCROLL;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

public abstract class HtmlViewer<T> extends Browser
{
	private static final String AUTO_SCROLL = "<body style='overflow:auto'/>";

	private boolean autoIndent = true;

	private StringBuilder html = new StringBuilder(AUTO_SCROLL);

	public HtmlViewer(Composite parent, int style)
	{
		super(parent, checkStyle(style));
	}

	public boolean isAutoIndent()
	{
		return autoIndent;
	}

	public void setAutoIndent(boolean autoIndent)
	{
		this.autoIndent = autoIndent;
	}

	protected static int checkStyle(int style)
	{
		return clearBits(style, H_SCROLL | V_SCROLL);
	}

	public void clear()
	{
		clearHtml();
		refresh();
	}

	public boolean isClear()
	{
		return html.toString().equalsIgnoreCase(AUTO_SCROLL);
	}

	public void println()
	{
		html.append(HTML_TAG_NEW_LINE.open());
		refresh();
	}

	private void clearHtml()
	{
		html.replace(0, html.length(), AUTO_SCROLL);
	}

	/**
	 * Widget
	 */

	@Override
	protected void checkSubclass()
	{}

	/**
	 * Browser
	 */

	@Override
	public boolean setText(String html)
	{
		clearHtml();
		this.html.append(html);
		refresh();

		return true;
	}

	@Override
	public String getText()
	{
		return html.substring(AUTO_SCROLL.length() + 1);
	}

	@Override
	public void refresh()
	{
		super.setText("" + html);
	}

	/**
	 * Control
	 */

	@Override
	@Deprecated
	public void setBackground(Color color)
	{}

	@Override
	@Deprecated
	public void setForeground(Color color)
	{}

	@Override
	@Deprecated
	public Color getBackground()
	{
		return super.getBackground();
	}

	@Override
	@Deprecated
	public Color getForeground()
	{
		return super.getForeground();
	}

	public abstract void println(T html);

	public abstract void print(T html);

	protected void addTextLn(String html)
	{
		addText(html, true);
	}

	protected void addText(String html)
	{
		addText(html, false);
	}

	private void addText(String html, boolean newLine)
	{
		if(isNull(html))
			return;

		checkAutoIndent();
		addHtml(html);
		addNewLine(newLine);
		refresh();
	}

	private void addHtml(String html)
	{
		if(!isEmpty(html))
			this.html.append(html.trim());
	}

	private void addNewLine(boolean newLine)
	{
		if(newLine)
			this.html.append(HTML_TAG_NEW_LINE.open());
	}

	private void checkAutoIndent()
	{
		if(!autoIndent || isClear())
			return;

		String tagNewLine = HTML_TAG_NEW_LINE.open();
		int lengthTagNewLine = tagNewLine.length();
		int lengthHtml = html.length();
		int tagPrePreStart = lengthHtml - (2 * lengthTagNewLine);
		int tagPreStart = tagPrePreStart + lengthTagNewLine;
		String tagPrePre = html.substring(tagPrePreStart, tagPreStart);
		String tagPre = html.substring(tagPreStart);

		if(!tagPre.equalsIgnoreCase(tagNewLine))
			this.html.append(tagNewLine + tagNewLine);
		else if(!tagPrePre.equalsIgnoreCase(tagNewLine))
			this.html.append(tagNewLine);
	}
}
