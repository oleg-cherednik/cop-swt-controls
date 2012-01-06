/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.columns.settings;

import static cop.common.extensions.CompareExtension.compareStrings;

import java.lang.reflect.AccessibleObject;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.viewers.table.columns.ColumnContext;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 03.09.2010
 */
public class StringColumn<T> extends AbstractColumnSettings<T>
{
	protected StringColumn(AccessibleObject obj, ColumnContext context)
	{
		super(obj, context);
	}

	/*
	 * Comparator
	 */

	@Override
	public int compare(T item1, T item2)
	{
		try
		{
			String str1 = (String)getValue(item1);
			String str2 = (String)getValue(item2);

			return compareStrings(str1, str2, getCollator());
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
	public String getCellEditorValue(T item) throws Exception
	{
		return getTextValue(item);
	}

	@Override
	public CellEditor getCellEditor(Composite parent)
	{
		if(editor == null)
			editor = new TextCellEditor(parent);
		return editor;
	}
}
