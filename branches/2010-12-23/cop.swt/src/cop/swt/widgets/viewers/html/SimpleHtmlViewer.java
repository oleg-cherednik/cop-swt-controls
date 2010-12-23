package cop.swt.widgets.viewers.html;

import org.eclipse.swt.widgets.Composite;

public class SimpleHtmlViewer extends HtmlViewer<String>
{
	public SimpleHtmlViewer(Composite parent, int style)
	{
		super(parent, style);
	}

	/**
	 * HtmlViewer
	 */

	@Override
	public void println(String html)
	{
		super.addTextLn(html);
	}

	@Override
	public void print(String html)
	{
		super.addText(html);
	}
}
