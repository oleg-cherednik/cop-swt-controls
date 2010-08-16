/**
 * $Id: INumeric.java 51 2010-08-16 12:25:56Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.swt/cop.swt/src/cop/swt/widgets/numeric/interfaces/INumeric.java $
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
