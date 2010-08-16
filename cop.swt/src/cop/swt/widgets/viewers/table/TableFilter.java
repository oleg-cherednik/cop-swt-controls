package cop.swt.widgets.viewers.table;

import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isEmpty;
import static cop.swt.enums.CaseSensitivityEnum.CASE_INSENSITIVE;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import cop.swt.enums.CaseSensitivityEnum;

public class TableFilter<T> extends ViewerFilter
{
	private PTableColumnInfo<T>[] viewerColumns;
	private String regex;
	private CaseSensitivityEnum caseSensitivity = CASE_INSENSITIVE;

	public TableFilter(PTableColumnInfo<T>[] viewerColumns)
	{
		this.viewerColumns = viewerColumns;
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

			for(PTableColumnInfo<T> viewerColumn : viewerColumns)
			{
				obj = viewerColumn.getDescription().getTextValue((T)element);

				if(isNull(obj))
					continue;

				if(caseSensitivity.matches(obj.toString(), regex))
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
