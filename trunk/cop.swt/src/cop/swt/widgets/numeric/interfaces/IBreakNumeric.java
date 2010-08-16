/**
 * $Id: IBreakNumeric.java 51 2010-08-16 12:25:56Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.swt/cop.swt/src/cop/swt/widgets/numeric/interfaces/IBreakNumeric.java $
 */
package cop.swt.widgets.numeric.interfaces;

import java.util.Set;

/**
 * Use this interface for objects, that can have invisible values within its range
 * 
 * @author cop (Cherednik, Oleg)
 */
public interface IBreakNumeric<T> extends INumeric<T>
{
	/**
	 * Add new <i>not null</i> invisible value.<br>
	 * If value is set as invisible, it'll not be shown and will be missed during iteration.<br>
	 * If this value is shown at this time, it'll will be change to next visible value.
	 * 
	 * @param value new invisible value
	 * @throws NullPointerException in case of value is <b>null</b>
	 */
	void addBreak(T value) throws NullPointerException;

	/**
	 * Remove existed invisible value.<br>
	 * 
	 * @param value existed invisible value
	 * @throws NullPointerException in case of value is <b>null</b>
	 */
	void removeBreak(T value) throws NullPointerException;

	/**
	 * Get list of all invisible value.
	 * 
	 * @return <b>Set of Integers</b> that consists all current invisible values.<br>
	 *         If there no invisible values at this time, than empty set wil be returnd.
	 */
	Set<T> getBreakList();

	/**
	 * Remove all invisible values.<br>
	 */
	void clearBreakList();
}
