/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
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
