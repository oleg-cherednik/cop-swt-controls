package cop.swt.widgets.viewers.list;

import java.util.Comparator;

import cop.swt.widgets.comparators.LabelComparator;
import cop.swt.widgets.interfaces.LabelSupport;
import cop.swt.widgets.viewers.table.PViewerSorter;

public class PListSorter<T> extends PViewerSorter<T> implements LabelSupport
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
		return ((LabelComparator<T>)accessibleObject).getLabelName();
	}

	@Override
	public void setLabelName(String labelName)
	{
		((LabelComparator<T>)accessibleObject).setLabelName(labelName);
	}
}
