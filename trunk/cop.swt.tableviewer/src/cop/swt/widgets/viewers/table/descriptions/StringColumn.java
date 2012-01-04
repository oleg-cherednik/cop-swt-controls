/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.descriptions;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 03.09.2010
 */
public class StringColumn<T> extends ColumnDescription<T>
{
	private CellEditor editor;

	protected StringColumn(AccessibleObject obj, Locale locale)
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
			if((obj1 == null) ^ (obj2 == null))
				return (obj2 == null) ? 1 : -1;

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
		if(editor == null)
			editor = new TextCellEditor(parent);
		return editor;
	}
}
