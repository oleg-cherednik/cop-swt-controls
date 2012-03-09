package cop.swt.widgets.dirty;

import static cop.extensions.CommonExt.isEqual;
import static cop.extensions.CommonExt.isNull;

import cop.swt.widgets.numeric.AbstractNumeric;

public class NumericDirtyObserver<T extends Number> extends DirtyObserver
{
	private final AbstractNumeric<T> obj;
	private T originalValue;

	public NumericDirtyObserver(AbstractNumeric<T> obj) throws NullPointerException
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
		return !isEqual(obj.getValue(), originalValue);
	}

	@Override
	protected void setOriginal()
	{
		originalValue = obj.getValue();
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
