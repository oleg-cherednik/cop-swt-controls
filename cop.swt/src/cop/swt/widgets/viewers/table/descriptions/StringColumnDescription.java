package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CommonExtension.isNull;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

public class StringColumnDescription<T> extends ColumnDescription<T>
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

	protected int _compare(Object obj1, Object obj2)
	{
		return getCollator().compare(obj1, obj2);
	}

	/*
	 * Comparator
	 */

	@Override
	public int compare(T item1, T item2)
	{
		try
		{
			Object obj1 = getValue(item1);
			Object obj2 = getValue(item2);

			if(obj1 == obj2)
				return 0;
			if(isNull(obj1) ^ isNull(obj2))
				return isNull(obj2) ? 1 : -1;

			return _compare(obj1, obj2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	/*
	 * ColumnDescription
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
