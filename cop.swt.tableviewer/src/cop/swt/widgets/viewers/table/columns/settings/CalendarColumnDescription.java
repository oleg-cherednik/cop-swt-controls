package cop.swt.widgets.viewers.table.columns.settings;

import static cop.extensions.CommonExt.isNotNull;
import static cop.extensions.CompareExt.compareObjects;
import static cop.swt.widgets.viewers.table.celleditors.CalendarCellEditor.getDateFormat;

import java.lang.reflect.AccessibleObject;
import java.util.Calendar;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.viewers.table.celleditors.CalendarCellEditor;
import cop.swt.widgets.viewers.table.columns.ColumnContext;

public class CalendarColumnDescription<T> extends AbstractColumnSettings<T>
{
	private CalendarCellEditor cellEditor;

	protected CalendarColumnDescription(AccessibleObject obj, ColumnContext context)
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
			return compareObjects((Calendar)getValue(item1), (Calendar)getValue(item2));
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
		return null;// cellEditor = new CalendarCellEditor(parent, NONE, locale);
	}

	@Override
	protected String getText(Object obj)
	{
		return isNotNull(obj) ? getDateFormat(locale).format(((Calendar)obj).getTime()) : "";
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		if(isNotNull(cellEditor))
			cellEditor.setLocale(locale);
	}
}
