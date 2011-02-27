/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id:$
 * $HeadURL:$
 */
package cop.swt.widgets.viewers.html.templates;

import java.util.Collection;

import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.viewers.html.HtmlViewer;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 16.08.2010
 */
public class TemplateHtmlViewer<T> extends HtmlViewer<T>
{
	private final HtmlTemplate<T> template;

	public TemplateHtmlViewer(Composite parent, int style, HtmlTemplate<T> template)
	{
		super(parent, style, template.getHtmlDocument());

		this.template = template;
	}

	/*
	 * HtmlViewer
	 */

	@Override
	public void println(T obj)
	{
		addTextLn(template.getHtml(obj));
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
	public void print(Collection<T> objs)
	{
		int i = 0;

		for(T obj : objs)
		{
			if(i++ > 0)
				addText("\n" + template.getDelimeter() + "\n");

			addText(template.getHtml(obj));
		}

		refresh();
	}
}
