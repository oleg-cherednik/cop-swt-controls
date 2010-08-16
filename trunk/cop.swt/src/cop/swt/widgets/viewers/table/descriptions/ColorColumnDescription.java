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

public class ColorColumnDescription<T> extends AbstractColumnDescription<T>
{
	protected ColorColumnDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);
	}

	/*
	 * AbstractColumnDescription
	 */

	@Override
	protected int _compare(Object obj1, Object obj2)
	{
		return compareRgb((RGB)obj1, (RGB)obj2);
	}

	/*
	 * IColumnDescription
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
