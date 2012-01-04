/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.columns.settings;

import static cop.swt.extensions.ColorExtension.getColor;
import static cop.swt.widgets.comparators.RgbComparator.compareRgb;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColorCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.viewers.table.columns.ColumnSettingsContext;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 03.09.2010
 */
public class ColorColumn<T> extends AbstractColumnSettings<T>
{
	protected ColorColumn(ColumnSettingsContext context)
	{
		super(context);
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
		if(editor == null)
			editor = new ColorCellEditor(parent, SWT.NONE);
		return editor;
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

	@Override
	protected void check()
	{
		if(!type.isAssignableFrom(RGB.class))
			throw new IllegalArgumentException("Given object is not RGB");
	}
}
