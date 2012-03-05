/**
 * $Id$
 * $HeadURL$
 */
package cop.i18.exceptions;

/**
 * @author Oleg Cherednik
 * @since 04.03.2012
 */
public class LocaleStoreKeyDuplicationException extends Exception {
	private static final long serialVersionUID = 1907237273650425166L;

	public LocaleStoreKeyDuplicationException() {}

	public LocaleStoreKeyDuplicationException(String message) {
		super(message);
	}

	public LocaleStoreKeyDuplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public LocaleStoreKeyDuplicationException(Throwable cause) {
		super(cause);
	}
}
