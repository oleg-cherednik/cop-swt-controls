package cop.swt.widgets.annotations.contents;

import static cop.extensions.CommonExt.isNotNull;
import static cop.extensions.CommonExt.isNull;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_ALIGNMENT;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_EMPTYABLE;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_HIDEABLE;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_MOVABLE;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_NAME;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_ORDER;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_READONLY;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_RISIZABLE;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_SORTABLE;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_TOOLTIP;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_VISIBLE;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_WIDTH;
import static org.eclipse.swt.SWT.CENTER;
import static org.eclipse.swt.SWT.LEFT;
import static org.eclipse.swt.SWT.NONE;
import static org.eclipse.swt.SWT.RIGHT;

import java.text.Collator;
import java.util.Locale;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.TableColumn;

import cop.i18n.LocaleSupport;
import cop.swt.widgets.annotations.Column;

public class ColumnContent implements Comparable<ColumnContent>, LocaleSupport
{
	private String name = DEF_NAME;
	private String toolTip = DEF_TOOLTIP;
	private int order = DEF_ORDER;
	private boolean movable = DEF_MOVABLE;
	private boolean resizable = DEF_RISIZABLE;
	private boolean readonly = DEF_READONLY;
	private boolean sortable = DEF_SORTABLE;
	private int alignment = DEF_ALIGNMENT;
	private int width = DEF_WIDTH;
	private boolean visible = DEF_VISIBLE;
	private boolean hideable = DEF_HIDEABLE;
	private boolean emptyable = DEF_EMPTYABLE;
	private Collator collator;

	public ColumnContent(Column annotation, Locale locale)
	{
		setCollator(locale);
		setName(annotation.name());
		setToolTip(annotation.toolTip());
		setOrder(annotation.order());
		setMovable(annotation.movabale());
		setResizable(annotation.resizable());
		setAlignment(annotation.alignment());
		setReadonly(annotation.readonly());
		setSortable(annotation.sortable());
		setWidth(annotation.width());
		setVisible(annotation.visible());
		setHideable(annotation.hideable());
		setEmptyable(annotation.emptyable());
	}

	public TableViewerColumn createTableColumn(TableViewer viewer)
	{
		TableViewerColumn viewerColumn = new TableViewerColumn(viewer, NONE);
		TableColumn column = viewerColumn.getColumn();

		column.setText(getName());
		column.setToolTipText(getToolTip());
		column.setMoveable(isMovable());
		column.setAlignment(getAlignment());
		column.setResizable(isResizable());

		return viewerColumn;
	}

	public String getName()
	{
		return name;
	}

	public Collator getCollator()
	{
		return collator;
	}

	public String getToolTip()
	{
		return toolTip;
	}

	public int getOrder()
	{
		return order;
	}

	public boolean isMovable()
	{
		return movable;
	}

	public boolean isResizable()
	{
		return resizable;
	}

	public boolean isReadonly()
	{
		return readonly;
	}

	public boolean isSortable()
	{
		return sortable;
	}

	public int getAlignment()
	{
		return alignment;
	}

	public int getWidth()
	{
		return width;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public boolean isHideable()
	{
		return hideable;
	}

	public boolean isEmptyable()
	{
		return emptyable;
	}

	public void setName(String name)
	{
		this.name = isNull(name) ? DEF_NAME : name;
	}

	public void setToolTip(String toolTip)
	{
		this.toolTip = isNull(toolTip) ? DEF_TOOLTIP : toolTip;
	}

	private void setOrder(int order)
	{
		this.order = (order < 0) ? DEF_ORDER : order;
	}

	private void setMovable(boolean movable)
	{
		this.movable = movable;
	}

	private void setResizable(boolean resizable)
	{
		this.resizable = resizable;
	}

	public void setReadonly(boolean readonly)
	{
		this.readonly = readonly;
	}

	public void setSortable(boolean sortable)
	{
		this.sortable = sortable;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public void setHideable(boolean hideable)
	{
		this.hideable = hideable;
	}

	public void setEmptyable(boolean emptyable)
	{
		this.emptyable = emptyable;
	}

	private void setAlignment(int alignment)
	{
		switch(alignment)
		{
		case LEFT:
		case RIGHT:
		case CENTER:
			this.alignment = alignment;
			break;
		default:
			this.alignment = DEF_ALIGNMENT;
			break;
		}
	}

	private void setCollator(Locale locale)
	{
		collator = Collator.getInstance(locale);
	}

	/*
	 * Comparable
	 */

	@Override
	public int compareTo(ColumnContent obj)
	{
		if(isNull(obj))
			return 1;

		if(equals(obj))
			return 0;

		if(order != obj.order)
		{
			if(order == DEF_ORDER)
				return 1;

			if(obj.order == DEF_ORDER)
				return -1;

			return (order > obj.order) ? 1 : -1;
		}

		if(collator.equals(name, DEF_NAME))
			return 1;

		if(collator.equals(obj.name, DEF_NAME))
			return -1;

		return collator.compare(name, obj.name);
	}

	/*
	 * Object
	 */

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + order;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof ColumnContent))
			return false;
		ColumnContent other = (ColumnContent)obj;
		if(name == null)
		{
			if(other.name != null)
				return false;
		}
		else if(!collator.equals(name, other.name))
			return false;
		if(order != other.order)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		String str = null;

		switch(alignment)
		{
		case CENTER:
			str = "center";
			break;
		case LEFT:
			str = "left";
			break;
		case RIGHT:
			str = "right";
			break;
		default:
			str = "unknown";
			break;
		}

		return "[name(" + name + "), order(" + order + "), movable(" + movable + "), alignment(" + str + "), "
		                + "readonly(" + readonly + "), " + "sortable(" + sortable + ")]";
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		if(isNotNull(locale))
			setCollator(locale);
	}
}
