package cop.swt.widgets.exceptions;

public class WidgetBufferOverloadException extends Exception
{
	private static final long serialVersionUID = -8868444571541781912L;

	public WidgetBufferOverloadException()
	{}

	public WidgetBufferOverloadException(String message)
	{
		super(message);
	}

	public WidgetBufferOverloadException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public WidgetBufferOverloadException(Throwable cause)
	{
		super(cause);
	}
}
