package cop.swt.widgets.dirty.interfaces;


/**
 * @author cop (Cherednik, Oleg)
 */
public interface IDirtyObserver extends IDirty
{
	void addDirtyListener(IDirtyListener listener);

	void removeDirtyListener(IDirtyListener listener);
	
	void removeAllDirtyListener();
}
