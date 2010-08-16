/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.annotations.exceptions;

/**
 * Exception declares any error while using annotations that are self-defined. 
 * 
 * @author cop (Cherednik, Oleg)
 */
public class AnnotationDeclarationException extends Exception
{
	private static final long serialVersionUID = 1907237273650425166L;

	public AnnotationDeclarationException()
	{}

	public AnnotationDeclarationException(String message)
	{
		super(message);
	}

	public AnnotationDeclarationException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public AnnotationDeclarationException(Throwable cause)
	{
		super(cause);
	}
}
