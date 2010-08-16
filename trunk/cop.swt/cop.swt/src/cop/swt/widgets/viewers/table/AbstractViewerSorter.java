package cop.swt.widgets.viewers.table;

import static cop.swt.widgets.enums.SortDirectionEnum.SORT_ASC;
import static cop.swt.widgets.enums.SortDirectionEnum.SORT_DESC;
import static cop.swt.widgets.enums.SortDirectionEnum.SORT_OFF;

import java.util.Comparator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import cop.swt.widgets.enums.SortDirectionEnum;

public abstract class AbstractViewerSorter<T> extends ViewerSorter
{
	public static final SortDirectionEnum DEFAULT_SORT_DIRECTION = SORT_ASC;

	private SortDirectionEnum direction = SORT_OFF;
	protected final Comparator<T> accessibleObject;

	public AbstractViewerSorter(Comparator<T> accessibleObject)
	{
		Assert.isNotNull(accessibleObject);

		this.accessibleObject = accessibleObject;
	}

	public SortDirectionEnum getDirection()
	{
		return direction;
	}

	public void setDirection(SortDirectionEnum direction)
	{
		Assert.isNotNull(direction);

		this.direction = direction;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int compare(Viewer viewer, Object e1, Object e2)
	{
		Assert.isNotNull(accessibleObject);
		Assert.isNotNull(direction);

		if(direction == SORT_OFF)
			return 0;

		try
		{
			int res = accessibleObject.compare((T)e1, (T)e2);

			return (direction == SORT_DESC) ? -res : res;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return 0;
	}
}
