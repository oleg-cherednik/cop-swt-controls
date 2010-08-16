package cop.swt.widgets.tmp.vogella;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class PersonFilter extends ViewerFilter
{

	private String searchString;

	public void setSearchText(String s)
	{
		// Search must be a substring of the existing value
		this.searchString = ".*" + s + ".*";
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element)
	{
		//System.out.println("----------------------------------- Vogela. PersonFilter.select()");
		if(searchString == null || searchString.length() == 0)
			return true;
		
		Person p = (Person)element;
		if(p.getFirstName().matches(searchString))
		{
			return true;
		}
		if(p.getLastName().matches(searchString))
		{
			return true;
		}

		return false;
	}
}
