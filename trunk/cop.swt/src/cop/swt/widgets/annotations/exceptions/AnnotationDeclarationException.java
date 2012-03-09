/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id: AnnotationDeclarationException.java 371 2012-03-04 20:57:55Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/i18n/trunk/i18n/src/cop/common/annotations/exceptions/AnnotationDeclarationException.java $
 */
package cop.swt.widgets.annotations.exceptions;

/**
 * Exception declares any error while using self-defined annotations.
 * 
 * @author Oleg Cherednik
 * @since 16.08.2010
 */
public class AnnotationDeclarationException extends Exception {
	private static final long serialVersionUID = 1907237273650425166L;

	public AnnotationDeclarationException() {}

	public AnnotationDeclarationException(String message) {
		super(message);
	}

	public AnnotationDeclarationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AnnotationDeclarationException(Throwable cause) {
		super(cause);
	}
}
