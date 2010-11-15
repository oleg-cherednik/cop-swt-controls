package shell.temp;

import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isNull;
import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public final class FlexibleLayout extends Layout
{
	/**
	 * marginWidth specifies the number of pixels of horizontal margin that will be placed along the left and right
	 * edges of the layout. The default value is 0.
	 * 
	 * @since 3.0
	 */
	public int marginWidth = 0;

	/**
	 * marginHeight specifies the number of pixels of vertical margin that will be placed along the top and bottom edges
	 * of the layout. The default value is 0.
	 * 
	 * @since 3.0
	 */
	public int marginHeight = 0;

	/**
	 * spacing specifies the number of pixels between the edge of one cell and the edge of its neighbouring cell. The
	 * default value is 3.
	 */
	public int spacing = 3;

	/**
	 * marginLeft specifies the number of pixels of horizontal margin that will be placed along the left edge of the
	 * layout. The default value is 3.
	 */
	public int marginLeft = 3;

	/**
	 * marginTop specifies the number of pixels of vertical margin that will be placed along the top edge of the layout.
	 * The default value is 3.
	 */
	public int marginTop = 3;

	/**
	 * marginRight specifies the number of pixels of horizontal margin that will be placed along the right edge of the
	 * layout. The default value is 3.
	 */
	public int marginRight = 3;

	/**
	 * marginBottom specifies the number of pixels of vertical margin that will be placed along the bottom edge of the
	 * layout. The default value is 3.
	 */
	public int marginBottom = 3;

	private boolean free = true;

	private int[] rowLength;

	private Map<Control, Rectangle> screenshot = new LinkedHashMap<Control, Rectangle>();
	private Map<Control, Rectangle> map = new LinkedHashMap<Control, Rectangle>();
	private Composite composite;

	public FlexibleLayout()
	{}

	public FlexibleLayout(int[] widths)
	{
		setRowLength(widths);
	}

	public static Menu createMenu(Composite parent, final FlexibleLayout layout)
	{
		Menu menu = new Menu(parent);

		MenuItem item = new MenuItem(menu, SWT.NONE);
		item.setText("restore");
		item.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				layout.restoreScreenshot();
			}
		});
		
		MenuItem item1 = new MenuItem(menu, SWT.NONE);
		item1.setText("show all");
		item1.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				layout.reorder();
			}
		});

		return menu;
	}

	public void reorder()
	{
		if(composite == null)
			return;
		
		Control[] children = getChildren(composite);
		Rectangle bound = composite.getClientArea();
		
		boolean liveMain = children.length == 4;	//see LiveEventMainContent.createContent()
		
		int a = 0;
		a++;
		
		
		
//		int total = 0;
//
//		if(!CollectionExtension.isEmpty(rowLength))
//			for(int length : rowLength)
//				total += length;
//
//		int rows = 1;
//
//		if(total > 0)
//			rows(total > children.length) ?
//
//
//
//
//
//
//		int x = marginLeft + marginWidth;
//		int y = marginTop + marginHeight;
//
//		Point oneRowSize = new Point(0, 0);
//
//
//
//		//marginBottom + marginHeight
//
//		for(int i = 0, size = rowLength.length; i < size; i++)
//		{
//			if(i != 0)
//				y += busy.y + spacing;
//
//			int length = rowLength[i];
//
//			busy = buildRow(children, length, x + clientX, y + clientY, offs, move, flushCache);
//			offs += length;
//			maxX = max(maxX, busy.x);
//			tmpY = busy.y;
//
//			if(offs > children.length)
//				break;
//		}
	}
	
	//private Point calculateRangeForOneRow(Rectangle clientArea)

	public void makeScreenshot()
	{
		layout(composite, false);

		screenshot.clear();

		Rectangle bounds;

		for(Control child : map.keySet())
		{
			bounds = map.get(child);
			screenshot.put(child, new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height));
		}
	}

	public void restoreScreenshot()
	{
		Rectangle bounds;

		for(Control child : screenshot.keySet())
		{
			bounds = screenshot.get(child);
			child.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		}
	}

	public boolean isMemory()
	{
		return !screenshot.isEmpty();
	}

	public void setRowLength(int[] lengths)
	{
		if(isEmpty(lengths))
			this.rowLength = new int[0];
		else
		{
			List<Integer> arr = new ArrayList<Integer>();

			for(int length : lengths)
				if(length > 0)
					arr.add(length);

			if(isEmpty(arr))
				this.rowLength = new int[0];
			else
			{
				this.rowLength = new int[arr.size()];

				for(int i = 0, size = rowLength.length; i < size; i++)
					this.rowLength[i] = arr.get(i);
			}
		}
	}

	@Override
	protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache)
	{
		Control[] children = getChildren(composite);
		Point extent;

		if(isEmpty(children))
			extent = new Point(marginLeft + marginWidth * 2 + marginRight, marginTop + marginHeight * 2 + marginBottom);
		else
		{
			int width = 0;
			int height = 0;
			Point size;
			Rectangle bounds;

			for(Control control : children)
			{
				bounds = control.getBounds();
				size = computeSize(control, flushCache);

				width = Math.max(width, bounds.x + size.x);
				height = Math.max(height, bounds.y + size.y);
			}

			extent = new Point(width, height);
		}

		if(wHint != SWT.DEFAULT)
			extent.x = wHint;
		if(hHint != SWT.DEFAULT)
			extent.y = hHint;

		return extent;
	}

	Point computeSize(Control control, boolean flushCache)
	{
		Assert.isNotNull(control);

		Point size = getLayoutDataSize(control.getLayoutData());
		return control.computeSize(size.x, size.y, flushCache);
	}

	@Override
	protected boolean flushCache(Control control)
	{
		return true;
	}

	String getName()
	{
		String string = getClass().getName();
		int index = string.lastIndexOf('.');
		if(index == -1)
			return string;
		return string.substring(index + 1, string.length());
	}

	@Override
	protected void layout(Composite composite, boolean flushCache)
	{
		this.composite = composite;
		layout(composite, true, composite.getClientArea().width, flushCache);
	}

	private Control[] getChildren(Composite parent)
	{
		Control[] children = parent.getChildren();
		List<Control> res = new ArrayList<Control>();

		if(isEmpty(children))
			return new Control[0];

		for(Control child : children)
			if(!isExclude(child.getLayoutData()))
				res.add(child);

		return res.toArray(new Control[0]);
	}

	private boolean isExclude(Object layoutData)
	{
		if(isNull(layoutData))
			return true;

		if(layoutData instanceof GridData)
			return ((GridData)layoutData).exclude;
		else if(layoutData instanceof RowData)
			return ((RowData)layoutData).exclude;

		Assert.isLegal(false);

		return true;
	}

	private Point getLayoutDataSize(Object layoutData)
	{
		Point size = new Point(SWT.DEFAULT, SWT.DEFAULT);

		if(isNull(layoutData))
			return size;

		if(layoutData instanceof GridData)
		{
			size.x = ((GridData)layoutData).widthHint;
			size.y = ((GridData)layoutData).heightHint;
		}
		else if(layoutData instanceof RowData)
		{
			size.x = ((RowData)layoutData).width;
			size.y = ((RowData)layoutData).height;
		}
		else
			Assert.isLegal(false);

		return size;
	}

	Point layout(Composite composite, boolean move, int width, boolean flushCache)
	{
		Control[] children = getChildren(composite);

		if(isEmpty(children))
			return new Point(marginLeft + marginWidth * 2 + marginRight, marginTop + marginHeight * 2 + marginBottom);

		int clientX = 0;
		int clientY = 0;

		if(move)
		{
			Rectangle rect = composite.getClientArea();

			clientX = rect.x;
			clientY = rect.y;
		}

		int maxX = 0;
		int x = marginLeft + marginWidth;
		int y = marginTop + marginHeight;
		Point busy = new Point(0, 0);
		int offs = 0;
		int tmpY = 0;

		for(int i = 0, size = rowLength.length; i < size; i++)
		{
			if(i != 0)
				y += busy.y + spacing;

			int length = rowLength[i];

			busy = buildRow(children, length, x + clientX, y + clientY, offs, move, flushCache);
			offs += length;
			maxX = max(maxX, busy.x);
			tmpY = busy.y;

			if(offs > children.length)
				break;
		}

		y += tmpY;

		if(offs < children.length)
		{
			if(y != marginTop + marginHeight)
				y += spacing;

			busy = buildRow(children, -1, x + clientX, y + clientY, offs, move, flushCache);
			y += busy.y;
			maxX = max(maxX, busy.x);
		}

		y += marginBottom + marginHeight + clientY;
		maxX += marginRight + marginWidth + clientX;

		busy = new Point(maxX, y);

		return busy;
	}

	/*
	 * Return size of area that is busy for all items on row
	 */
	private Point buildRow(Control[] children, int length, int x, int y, int offs, boolean move, boolean flushCache)
	{
		Control child;
		Point size;
		int maxY = 0;
		int delta = 10;

		if(length <= 0 || children.length < length + offs)
			length = children.length - offs;

		for(int i = 0; i < length; i++)
		{
			child = children[i + offs];
			size = computeSize(child, flushCache);

			if(i != 0)
				x += spacing;

			if(move)
				setBounds(children[i + offs], x, y, size.x + delta, size.y);

			x += size.x + delta;
			maxY = max(maxY, size.y);
		}

		return new Point(x, maxY);
	}
	
//	private Point buildRowOrder(Control[] children, int length, int x, int y, int offs, int width, int height, boolean flushCache)
//	{
//		Control child;
//		Point size;
//		int maxY = 0;
//		int delta = 10;
//
//		if(length <= 0 || children.length < length + offs)
//			length = children.length - offs;
//
//		for(int i = 0; i < length; i++)
//		{
//			child = children[i + offs];
//			size = computeSize(child, flushCache);
//
//			if(i != 0)
//				x += spacing;
//
//			if(move)
//				setBounds(children[i + offs], x, y, size.x + delta, size.y);
//
//			x += size.x + delta;
//			maxY = max(maxY, size.y);
//		}
//
//		return new Point(x, maxY);
//	}

	private void setBounds(Control child, int x, int y, int width, int height)
	{
		if(isNull(child))
			return;

		if(map.get(child) == null || !free)
			child.setBounds(x, y, width, height);

		Rectangle rect = child.getBounds();

		map.put(child, rect);
	}
}
