package cop.swt.widgets.annotations.exceptions;

/**
 * @author cop
 */
public class WrongReturnValueException extends Exception
{
	private static final long serialVersionUID = 6376649313867816715L;

	public WrongReturnValueException()
	{}

	public WrongReturnValueException(String message)
	{
		super(message);
	}

	public WrongReturnValueException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public WrongReturnValueException(Throwable cause)
	{
		super(cause);
	}
}
