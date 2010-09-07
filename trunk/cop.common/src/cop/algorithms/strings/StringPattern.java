/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.algorithms.strings;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 07.09.2010
 */
public final class StringPattern
{
	/**
	 * Replace giving String with specific pattern
	 * 
	 * @param str
	 * @return
	 */
	public static String quoteCharacters(final String str)
	{
		if(str == null || str.isEmpty())
			return str;

		final String template = "\"\r\n\\\'";
		final char[][] prefix = { { '\\', '\"' }, { '\\', 'r' }, { '\\', 'n' }, { '\\', '\\' }, { '\\', '\'' } };

		int total = 0;
		int size = str.length();

		for(int i = 0; i < size; i++)
			if(template.indexOf(str.charAt(i)) >= 0)
				total++;

		char[] buf = new char[total * 2 + size - total];
		char ch;

		for(int i = 0, j = 0, k = 0; i < size; i++)
		{
			ch = str.charAt(i);
			k = template.indexOf(ch);

			if(k >= 0)
				for(int kk = 0, length = prefix[k].length; kk < length; kk++)
					buf[j++] = prefix[k][kk];
			else
				buf[j++] = ch;
		}

		return String.copyValueOf(buf);
	}
}
