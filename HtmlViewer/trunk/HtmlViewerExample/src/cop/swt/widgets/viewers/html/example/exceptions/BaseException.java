package cop.swt.widgets.viewers.html.example.exceptions;

import org.slf4j.LoggerFactory;

public abstract class BaseException extends Exception {
	private static final long serialVersionUID = 7519436415492309977L;

	protected BaseException() {
		LoggerFactory.getLogger(getClass().getName()).error("");
	}

	protected BaseException(String message) {
		super(message);
		LoggerFactory.getLogger(getClass().getName()).error(message);
	}

	protected BaseException(String message, Throwable t) {
		super(message, t);
		LoggerFactory.getLogger(getClass().getName()).error(message, t);
	}
}
