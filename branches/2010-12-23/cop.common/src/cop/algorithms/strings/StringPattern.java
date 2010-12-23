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

		if(total == 0)
			return str;

		char[] buf = new char[total * 2 + size - total];
		char ch;
		int tmpl_i;

		for(int i = 0, buf_i = 0; i < size; i++)
		{
			ch = str.charAt(i);

			if(total == 0)
				buf[buf_i++] = ch;
			else
			{
				tmpl_i = template.indexOf(ch);

				if(tmpl_i >= 0)
				{
					for(int j = 0; j < prefix[tmpl_i].length; j++)
						buf[buf_i++] = prefix[tmpl_i][j];

					total--;
				}
				else
					buf[buf_i++] = ch;
			}
		}

		return String.copyValueOf(buf);
	}
}
