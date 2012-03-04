/**
 * $Id$
 * $HeadURL$
 */
package cop.i18.exceptions;

/**
 * @author Oleg Cherednik
 * @since 04.03.2012
 */
public class LocaleStoreKeyDupblicationException extends Exception {
	private static final long serialVersionUID = 1907237273650425166L;

	public LocaleStoreKeyDupblicationException() {}

	public LocaleStoreKeyDupblicationException(String message) {
		super(message);
	}

	public LocaleStoreKeyDupblicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public LocaleStoreKeyDupblicationException(Throwable cause) {
		super(cause);
	}
}
