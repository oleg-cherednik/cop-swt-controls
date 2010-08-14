package cop.swt.widgets.annotations.exceptions;

public class AnnotationMissingException extends Exception
{
	private static final long serialVersionUID = -4067144286600739579L;

	public AnnotationMissingException()
	{}

	public AnnotationMissingException(String message)
	{
		super(message);
	}

	public AnnotationMissingException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public AnnotationMissingException(Throwable cause)
	{
		super(cause);
	}
}
