package cop.common.exceptions;

public class MethodNotImplementedException extends Exception
{
	private static final long serialVersionUID = 2794303567508024758L;

	public MethodNotImplementedException()
	{}

	public MethodNotImplementedException(String message)
	{
		super(message);
	}

	public MethodNotImplementedException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public MethodNotImplementedException(Throwable cause)
	{
		super(cause);
	}
}
