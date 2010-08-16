/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.numeric.interfaces;

/**
 * Use this interface for objects, that can return it's integer value
 * 
 * @author cop (Cherednik, Oleg)
 */
public interface INumeric<T>
{
	/**
	 * Return value
	 * 
	 * @return value
	 */
	T getValue();

	/**
	 * Set <i>not null</i> value.<br>
	 * 
	 * @param value
	 */
	void setValue(T value);
}
