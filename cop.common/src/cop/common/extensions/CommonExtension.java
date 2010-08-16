/**
 * @licence GNU Leser General Public License
 *
 * $Id: CommonExtension.java 47 2010-08-16 12:19:28Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.common/cop.common/src/cop/common/extensions/CommonExtension.java $
 */
package cop.common.extensions;


/**
 * Class provides common methods that can't be placed into other named extension classes.<br>
 * This class contains only <i><u>static</u></i> methods. It can't be instantiated or inherited.
 * 
 * @author cop (Cherednik, Oleg)
 */
public final class CommonExtension
{
	/**
	 * Closed constructor
	 */
	private CommonExtension()
	{}

	/**
	 * Checks <i>value</i> for <i>null</i>. If it's <b>not null</b>, than <i>value</i> itself will be returnd,<br>
	 * otherwise <i>defaultValue</i> will be returnd.
	 * 
	 * @param <T> template parameter
	 * @param value this value will be returned if it's <b>not null</b>
	 * @param defaultValue this value will be returndd if <i>value</i> is <b>null</b>
	 * @return <i>value</i> if it's <b>not null</b>, otherwise - <i>defaultValue</i>
	 */
	public static <T> T getValue(T value, T defaultValue)
	{
		return (value != null) ? value : defaultValue;
	}

	/**
	 * Checks if <b>obj1</b> equals <b>obj2</b> or not.<br>
	 * 
	 * @param <T> template parameter
	 * @param obj1 first comparison object
	 * @param obj2 seconds comparison object
	 * @return <b>true</b> in case of <code>obj1.equals(bij2)</code>
	 */
	public static <T> boolean isEqual(T obj1, T obj2)
	{
		return isNull(obj1) ? isNull(obj2) : obj1.equals(obj2);
	}

	public static <T> boolean isNotEqual(T obj1, T obj2)
	{
		return !isEqual(obj1, obj2);
	}

	public static <T> boolean isNull(T obj)
	{
		return obj == null;
	}

	public static <T> boolean isNotNull(T obj)
	{
		return obj != null;
	}
}
