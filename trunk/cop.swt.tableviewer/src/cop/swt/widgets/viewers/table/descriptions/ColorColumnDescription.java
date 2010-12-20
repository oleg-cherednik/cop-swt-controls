package cop.swt.widgets.viewers.table.descriptions;

import static cop.swt.extensions.ColorExtension.getColor;
import static cop.swt.widgets.comparators.RgbComparator.compareRgb;
import static org.eclipse.swt.SWT.NONE;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColorCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;

public class ColorColumnDescription<T> extends ColumnDescription<T>
{
	protected ColorColumnDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);
	}

	/*
	 * Comparator
	 */

	@Override
	public int compare(T item1, T item2)
	{
		try
		{
			return compareRgb((RGB)getValue(item1), (RGB)getValue(item2));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	/*
	 * ColumnDescription
	 */

	@Override
	public CellEditor getCellEditor(Composite parent)
	{
		return new ColorCellEditor(parent, NONE);
	}

	@Override
	public Object getValue(T item) throws Exception
	{
		return invoke(item);
	}

	@Override
	public void update(ViewerCell cell, T item) throws Exception
	{
		cell.setBackground(getColor((RGB)invoke(item)));
	}
}
