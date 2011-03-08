/**
 * @licence GNU Leser General Public License
 *
 * $Id: CommonExtension.java 250 2011-01-09 22:44:01Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.swt.segmentindicator/src/cop/common/extensions/CommonExtension.java $
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

	public static <T> boolean isNull(T obj)
	{
		return obj == null;
	}
}
