package cop.swt.widgets.interfaces;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author cop (Cherednik, Oleg)
 */
public interface Creatable<T extends Control>
{
	T create(Composite parent) throws NullPointerException;
}
