package cop.swt.widgets.viewers.html.example.exceptions;

public class TestException extends BaseException {
	private static final long serialVersionUID = -6543426254334832340L;

	public TestException() {}

	public TestException(String message) {
		super(message);
	}

	public TestException(String message, Throwable t) {
		super(message, t);
	}
}
