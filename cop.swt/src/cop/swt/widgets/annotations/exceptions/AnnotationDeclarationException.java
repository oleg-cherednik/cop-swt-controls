/**
 * $Id: AnnotationDeclarationException.java 51 2010-08-16 12:25:56Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.swt/cop.swt/src/cop/swt/widgets/annotations/exceptions/AnnotationDeclarationException.java $
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
