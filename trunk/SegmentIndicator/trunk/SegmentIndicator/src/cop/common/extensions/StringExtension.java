/**
 * @licence GNU Leser General Public License
 *
 * $Id: StringExtension.java 250 2011-01-09 22:44:01Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.swt.segmentindicator/src/cop/common/extensions/StringExtension.java $
 */
package cop.common.extensions;

import static cop.common.extensions.NumericExtension.isInRangeMinMax;

public final class StringExtension
{
	private StringExtension()
	{}
	
	public static boolean isNumber(char ch)
	{
		return isInRangeMinMax(new Integer(ch), 48, 57);
	}
}
