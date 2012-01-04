package cop.swt.widgets.viewers.table;

import static cop.common.extensions.StringExtension.isEmpty;
import static cop.swt.enums.CaseSensitivityEnum.CASE_INSENSITIVE;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import cop.swt.enums.CaseSensitivityEnum;
import cop.swt.widgets.viewers.table.columns.PTableColumn;
import cop.swt.widgets.viewers.table.columns.PTableColumnSet;

public class TableFilter<T> extends ViewerFilter
{
	private final PTableColumnSet<T> columns;
	private String regex;
	private CaseSensitivityEnum caseSensitivity = CASE_INSENSITIVE;

	public TableFilter(PTableColumnSet<T> columns)
	{
		this.columns = columns;
	}

	public void setSearchText(String text)
	{
		regex = ".*" + text + ".*";
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean select(Viewer viewer, Object parentElement, Object element)
	{
		if(isEmpty(regex))
			return true;

		try
		{
			Object obj;

			for(PTableColumn<T> viewerColumn : columns.getColumns())
			{
				obj = viewerColumn.getSettings().getTextValue((T)element);

				if(obj != null && caseSensitivity.matches(obj.toString(), regex))
					return true;
			}

			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return true;
	}
}
