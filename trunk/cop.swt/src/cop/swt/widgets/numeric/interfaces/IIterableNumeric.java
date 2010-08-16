/**
 * $Id: IIterableNumeric.java 51 2010-08-16 12:25:56Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.swt/cop.swt/src/cop/swt/widgets/numeric/interfaces/IIterableNumeric.java $
 */
package cop.swt.widgets.numeric.interfaces;

/**
 * Use this interface for objects, that can iterate it's value
 * 
 * @author cop (Cherednik, Oleg)
 */
public interface IIterableNumeric<T> extends INumeric<T>
{
	/**
	 * Return increment value
	 * 
	 * @return increment value. It's always is not less than 1 and not more than distanse between minimum and maximum
	 */
	T getInc();

	/**
	 * Set increment value.<br>
	 * Increment is used in {@link #setNext()} and {@link #setPrevious()}
	 * 
	 * @param inc increment value
	 * @throws IllegalArgumentException in case of increment is less than 1 or greater than distanse between minimum and
	 *             maximum
	 * @throws NullPointerException in case of increment is <b>null</b>
	 */
	void setInc(T inc) throws IllegalArgumentException, NullPointerException;

	/**
	 * Set next value using increment.<br>
	 * <b>Example: <code>current = current + inc</code></b>
	 */
	void setNext();

	/**
	 * Set previous value using increment.<br>
	 * <b>Example: <code>current = current - inc</code></b>
	 */
	void setPrevious();
}
