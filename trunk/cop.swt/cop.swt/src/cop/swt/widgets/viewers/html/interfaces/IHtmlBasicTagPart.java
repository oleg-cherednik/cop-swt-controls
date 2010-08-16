package cop.swt.widgets.viewers.html.interfaces;

public interface IHtmlBasicTagPart
{
	String HTML_TAG_OPEN = "<";
	String HTML_TAG_CLOSE = ">";
	String HTML_TAG_END = "/";
	
	String open();

	String close();
}
