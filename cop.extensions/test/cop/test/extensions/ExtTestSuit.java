/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id: ExtensionsTestSuit.java 177 2010-11-28 20:54:58Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.common/test/cop/common/extensions/ExtensionsTestSuit.java $
 */
package cop.test.extensions;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ArrayExtTest.class, BitExtTest.class, MatrixExtTest.class,
                NumericExtTest.class })
public class ExtTestSuit
{}
