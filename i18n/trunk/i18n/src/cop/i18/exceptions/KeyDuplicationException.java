/**
 * $Id$
 * $HeadURL$
 */
package cop.i18.exceptions;

/**
 * @author Oleg Cherednik
 * @since 04.03.2012
 */
public class KeyDuplicationException extends Exception {
	private static final long serialVersionUID = 1907237273650425166L;

	public KeyDuplicationException() {}

	public KeyDuplicationException(String message) {
		super(message);
	}

	public KeyDuplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public KeyDuplicationException(Throwable cause) {
		super(cause);
	}
}
