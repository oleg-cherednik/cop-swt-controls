package cop.swt.widgets.tmp.vogella;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;


@SuppressWarnings("static-access")
// public class PersonLabelProvider extends LabelProvider implements ITableLabelProvider
// {
// // We use icons
// // We use icons
// private static Image CHECKED;// = Activator.getImageDescriptor("icons/checked.gif").createImage();
// private static Image UNCHECKED;// = Activator.getImageDescriptor("icons/unchecked.gif").createImage();
//	
// // static
// // {
// // Activator activator = Activator.getDefault();
// //
// // IPath path = activator.getStateLocation();
// // @SuppressWarnings("unused")
// // ImageDescriptor descr = activator.getImageDescriptor("hecked.gif");
// //
// // int a= 0;
// // a++;
// // }
//
// @Override
// public Image getColumnImage(Object element, int columnIndex)
// {
// // In case you don't like image just return null here
// // if(columnIndex == 3)
// // {
// // if(((Person)element).isMarried())
// // {
// // return CHECKED;
// // }
// // else
// // {
// // return UNCHECKED;
// // }
// // }
// return null;
// }
//
// @Override
// public String getColumnText(Object element, int columnIndex)
// {
// Person person = (Person)element;
// switch(columnIndex)
// {
// case 0:
// return person.getFirstName();
// case 1:
// return person.getLastName();
// case 2:
// return person.getGender();
// case 3:
// return String.valueOf(person.isMarried());
// default:
// throw new RuntimeException("Should not happen");
// }
//
// }
//
// }
public class PersonLabelProvider extends StyledCellLabelProvider
{
	// We use icons
	private final Image CHECKED;
	private final Image UNCHECKED;
	private String searchText;
	private Color systemColor;

	public PersonLabelProvider(Image checked, Image unchecked)
	{
		CHECKED = checked;
		UNCHECKED = unchecked;
		systemColor = Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
	}

	public void setSearchText(String searchText)
	{
		this.searchText = searchText;

	}

	@Override
	public void update(ViewerCell cell)
	{
		System.out.println("PersonLabelProvider.update()");
		Person element = (Person)cell.getElement();
		int index = cell.getColumnIndex();
		String columnText = getColumnText(element, index);
		cell.setText(columnText);
		cell.setImage(getColumnImage(element, index));
		if(searchText != null && searchText.length() > 0)
		{
			int intRangesCorrectSize[] = SearchUtil.getSearchTermOccurrences(searchText, columnText);
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
		else
		{
			cell.setStyleRanges(null);
		}

		super.update(cell);

	}

	private String getColumnText(Object element, int columnIndex)
	{
		System.out.println("PersonLabelProvider.getColumnText()");
		Person person = (Person)element;
		switch(columnIndex)
		{
		case 0:
			return person.getFirstName();
		case 1:
			return person.getLastName();
		case 2:
			return person.getGender();
		case 3:
			return String.valueOf(person.isMarried());
		default:
			throw new RuntimeException("Should not happen");
		}
	}

	private Image getColumnImage(Object element, int columnIndex)
	{
		System.out.println("PersonLabelProvider.getColumnImage()");
		// In case you don't like image just return null here
		if(columnIndex == 3)
		{
			if(((Person)element).isMarried())
			{
				return CHECKED;
			}
			return UNCHECKED;
		}
		return null;
	}

}
