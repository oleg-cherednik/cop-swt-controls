package cop.swt.widgets.viewers.table.descriptions;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

public class StringColumnDescription<T> extends AbstractColumnDescription<T>
{
	/**
	 * Closed constructor
	 * 
	 * @param obj
	 * @param locale
	 */
	protected StringColumnDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);
	}

	/*
	 * AbstractColumnDescription
	 */

	@Override
	protected int _compare(Object obj1, Object obj2)
	{
		return getCollator().compare(obj1, obj2);
	}

	/*
	 * IColumnDescription
	 */

	@Override
	public Object getEditValue(T item) throws Exception
	{
		return getTextValue(item);
	}

	@Override
	public CellEditor getCellEditor(Composite parent)
	{
		return new TextCellEditor(parent);
	}
}
