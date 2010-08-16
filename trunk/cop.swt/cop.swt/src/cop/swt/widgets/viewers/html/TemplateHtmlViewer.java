package cop.swt.widgets.viewers.html;

import static cop.common.extensions.CommonExtension.isNull;

import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.viewers.html.templates.HtmlTemplate;

public class TemplateHtmlViewer<T> extends HtmlViewer<T>
{
	private HtmlTemplate<T> template;

	public TemplateHtmlViewer(Composite parent, int style, HtmlTemplate<T> template) throws NullPointerException
	{
		super(parent, style);

		if(isNull(template))
			throw new NullPointerException("Template must be defined");

		this.template = template;
	}

	/**
	 * HtmlViewer
	 */

	@Override
	public void println(T obj)
	{
		try
		{
			super.addTextLn(template.getHtml(obj));
		}
		catch(Exception e)
		{}

		if(isNull(obj))
			return;
	}

	@Override
	public void print(T obj)
	{
		try
		{
			super.addText(template.getHtml(obj));
		}
		catch(Exception e)
		{}
	}

	@Override
	@Deprecated
	public final boolean setText(String html)
	{
		return false;
	}
}
