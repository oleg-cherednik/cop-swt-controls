package cop.swt.widgets.viewers.list;

import java.util.Comparator;

import org.eclipse.core.runtime.Assert;

import cop.swt.widgets.comparators.LabelComparator;
import cop.swt.widgets.interfaces.LabelSupport;
import cop.swt.widgets.viewers.table.AbstractViewerSorter;

public class PListSorter<T> extends AbstractViewerSorter<T> implements LabelSupport
{
	public PListSorter(Comparator<T> comparator)
	{
		super(comparator);
	}

	/*
	 * LabelSupport
	 */

	@Override
	public String getLabelName()
	{
		Assert.isNotNull(accessibleObject);

		return ((LabelComparator<T>)accessibleObject).getLabelName();
	}

	@Override
	public void setLabelName(String labelName)
	{
		Assert.isNotNull(accessibleObject);

		((LabelComparator<T>)accessibleObject).setLabelName(labelName);
	}
}
