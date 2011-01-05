package cop.swt.widgets.viewers;

import static cop.swt.widgets.enums.SortDirectionEnum.SORT_ASC;
import static cop.swt.widgets.enums.SortDirectionEnum.SORT_DESC;
import static cop.swt.widgets.enums.SortDirectionEnum.SORT_OFF;

import java.util.Comparator;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import cop.swt.widgets.enums.SortDirectionEnum;

public class PViewerSorter<T> extends ViewerSorter
{
	public static final SortDirectionEnum DEFAULT_SORT_DIRECTION = SORT_ASC;

	private SortDirectionEnum direction = SORT_OFF;
	private Comparator<T> comparator;
	private final StructuredViewer viewer;

	public PViewerSorter(StructuredViewer viewer, Comparator<T> accessibleObject)
	{
		if(viewer == null || accessibleObject == null)
			throw new NullPointerException();

		this.viewer = viewer;
		this.comparator = accessibleObject;
	}

	public void setComparator(Comparator<T> comparator)
	{
		if(comparator == null)
			throw new NullPointerException();

		this.comparator = comparator;
	}

	@Override
	public Comparator<T> getComparator()
	{
		return comparator;
	}

	public SortDirectionEnum getDirection()
	{
		return direction;
	}

	public void setDirection(SortDirectionEnum direction)
	{
		if(direction == null)
			throw new NullPointerException();

		this.direction = direction;
		viewer.setSorter((direction == SORT_OFF) ? null : this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public int compare(Viewer viewer, Object e1, Object e2)
	{
		if(direction == SORT_OFF)
			return 0;

		try
		{
			int res = comparator.compare((T)e1, (T)e2);

			return (direction == SORT_DESC) ? -res : res;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return 0;
	}
}
