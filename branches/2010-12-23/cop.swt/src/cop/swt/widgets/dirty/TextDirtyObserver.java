package cop.swt.widgets.dirty;

import static cop.common.extensions.CommonExtension.isNull;

import org.eclipse.swt.widgets.Text;

public class TextDirtyObserver extends DirtyObserver
{
	private final Text obj;
	private StringBuilder originalText = new StringBuilder();

	public TextDirtyObserver(Text obj) throws NullPointerException
	{
		if(isNull(obj))
			throw new NullPointerException("obj == null");

		this.obj = obj;
	}

	/*
	 * DirtyObserver
	 */

	@Override
	protected boolean getNewDirty()
	{
		return !obj.getText().equals("" + originalText);
	}

	@Override
	protected void setOriginal()
	{
		originalText.replace(0, originalText.length(), obj.getText());
	}

	@Override
	protected void addDirtyListener()
	{
		obj.addModifyListener(checkDirtyOnModify);
	}

	@Override
	protected void removeDirtyListener()
	{
		obj.removeModifyListener(checkDirtyOnModify);
	}
}
