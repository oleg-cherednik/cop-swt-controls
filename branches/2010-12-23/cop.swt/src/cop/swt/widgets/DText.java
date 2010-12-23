package cop.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import cop.swt.widgets.dirty.TextDirtyObserver;
import cop.swt.widgets.dirty.interfaces.IDirtyListener;
import cop.swt.widgets.dirty.interfaces.IDirtyObserver;

public class DText extends Text implements IDirtyObserver
{
	private TextDirtyObserver dirtyObserver;

	public static Text createText(Composite parent, int style)
	{
		return new DText(parent, style);
	}

	public static DText createDirtyText(Composite parent, int style)
	{
		DText obj = new DText(parent, style);

		obj.createDirtyObserver();

		return obj;
	}

	protected DText(Composite parent, int style)
	{
		super(parent, style);
	}

	public TextDirtyObserver getDirtyObserver()
	{
		return dirtyObserver;
	}

	public void createDirtyObserver()
	{
		if(dirtyObserver != null)
			return;

		dirtyObserver = new TextDirtyObserver(this);
		setDirty(false);
	}

	/*
	 * Widget
	 */

	@Override
	protected void checkSubclass()
	{}

	/*
	 * IDirtyListener
	 */

	@Override
	public void addDirtyListener(IDirtyListener listener)
	{
		if(dirtyObserver != null)
			dirtyObserver.addDirtyListener(listener);
	}

	@Override
	public void removeDirtyListener(IDirtyListener listener)
	{
		if(dirtyObserver != null)
			dirtyObserver.removeDirtyListener(listener);
	}

	@Override
	public void removeAllDirtyListener()
	{
		if(dirtyObserver != null)
			dirtyObserver.removeAllDirtyListener();
	}

	/*
	 * IDirty
	 */

	@Override
	public boolean isDirty()
	{
		return (dirtyObserver != null) ? dirtyObserver.isDirty() : false;
	}

	@Override
	public void setDirty(boolean dirty)
	{
		dirtyObserver.setDirty(dirty);
	}
}
