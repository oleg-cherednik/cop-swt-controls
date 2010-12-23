package cop.swt.widgets.numeric.interfaces;

import cop.swt.widgets.numeric.enums.BoundTypeEnum;

/**
 * @author cop (Cherednik, Oleg)
 */
public interface IBoundNumeric<T> extends INumeric<T>
{
	BoundTypeEnum getBoundType();

	T getMinimum();

	T getMaximum();

	boolean isWrap();

	void setMinimum(T minimum) throws IllegalArgumentException;

	void setMaximum(T minimum) throws IllegalArgumentException;

	void setBounds(T minimum, T maximum) throws IllegalArgumentException;

	void setWrap(boolean wrap);
}
