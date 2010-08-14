package cop.swt.widgets.tmp.vogella;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class PersonContentProvider implements IStructuredContentProvider
{

	@Override
	public Object[] getElements(Object inputElement)
	{
		System.out.println("--------- PersonContentProvider.getElements()");
		@SuppressWarnings("unchecked")
		List<Person> persons = (List<Person>)inputElement;
		return persons.toArray();
	}

	@Override
	public void dispose()
	{}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{
		System.out.println("--------- PersonContentProvider.inputChanged()");
	}
}
