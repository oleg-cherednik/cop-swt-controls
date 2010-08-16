package cop.swt.widgets.viewers.table;

import static cop.swt.extensions.ColorExtension.YELLOW;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;

import cop.swt.widgets.tmp.vogella.SearchUtil;
import cop.swt.widgets.viewers.table.descriptions.IColumnDescription;

public class PTableLabelProvider<T> extends StyledCellLabelProvider
{
	private PTableColumnInfo<T>[] viewerColumns;
	private String searchText;
	private Color systemColor = YELLOW;

	public PTableLabelProvider(PTableColumnInfo<T>[] viewerColumns)
	{
		this.viewerColumns = viewerColumns;
	}

	public void setSearchText(String text)
	{
		this.searchText = text;
	}
	
	/*
	 * StyledCellLabelProvider
	 */

	@Override
	@SuppressWarnings("unchecked")
	public void update(ViewerCell cell)
	{
		try
		{
			IColumnDescription description = viewerColumns[cell.getColumnIndex()].getDescription();
			T element = (T)cell.getElement();

			description.update(cell, element);

			String str = description.getTextValue(element);

			if(isNull(str) || isEmpty(searchText))
				cell.setStyleRanges(null);
			else
			{
				int intRangesCorrectSize[] = SearchUtil.getSearchTermOccurrences(searchText, str);
				List<StyleRange> styleRange = new ArrayList<StyleRange>();

				for(int i = 0; i < intRangesCorrectSize.length / 2; i++)
				{
					StyleRange myStyleRange = new StyleRange(0, 0, null, systemColor);
					myStyleRange.start = intRangesCorrectSize[i];
					myStyleRange.length = intRangesCorrectSize[++i];
					styleRange.add(myStyleRange);
				}

				cell.setStyleRanges(styleRange.toArray(new StyleRange[styleRange.size()]));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			super.update(cell);
		}
	}
}
