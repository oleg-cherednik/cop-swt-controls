package cop.swt.widgets.viewers.table;

import static cop.extensions.CommonExt.isNull;
import static cop.extensions.StringExt.isEmpty;
import static cop.swt.extensions.ColorExtension.YELLOW;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

import cop.swt.utils.SearchUtil;
import cop.swt.widgets.viewers.table.columns.PTableColumn;
import cop.swt.widgets.viewers.table.columns.PTableColumnSet;
import cop.swt.widgets.viewers.table.columns.settings.ColumnSettings;

public class PTableLabelProvider<T> extends StyledCellLabelProvider implements IColorProvider, IFontProvider
{
	private final PTableColumnSet<T> columns;
	private String searchText;
	private Color systemColor = YELLOW;

	public PTableLabelProvider(PTableColumnSet<T> columns)
	{
		this.columns = columns;
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
			PTableColumn<T> viewerColumn = columns.get(cell.getColumnIndex());

			if(viewerColumn == null)
				return;

			ColumnSettings<T> settings = viewerColumn.getSettings();
			T element = (T)cell.getElement();

			settings.update(cell, element);

			String str = settings.getTextValue(element);

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

	/*
	 * IColorProvider
	 */

	@Override
	public Color getForeground(Object element)
	{
		System.out.println("PTableLabelProvider.getForeground()");
		return null;
	}

	/*
	 * IFontProvider
	 */

	@Override
	public Color getBackground(Object element)
	{
		System.out.println("PTableLabelProvider.getBackground()");
		return null;
	}

	@Override
	public Font getFont(Object element)
	{
		System.out.println("PTableLabelProvider.getFont()");
		return null;
	}
}
