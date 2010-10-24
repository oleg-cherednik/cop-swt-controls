package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.beans.JavaBean.getPropertyName;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.ReflectionExtension.isBoolean;
import static cop.common.extensions.ReflectionExtension.isNumeric;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_TYPE;
import static cop.swt.widgets.annotations.services.CurrencyService.isCurrency;
import static cop.swt.widgets.annotations.services.PercentService.isPercent;

import java.lang.reflect.AccessibleObject;
import java.text.Collator;
import java.util.Calendar;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import cop.common.extensions.ReflectionExtension;
import cop.common.extensions.StringExtension;
import cop.swt.images.ImageProvider;
import cop.swt.widgets.annotations.Column;
import cop.swt.widgets.annotations.contents.ColumnContent;
import cop.swt.widgets.localization.interfaces.Localizable;

public abstract class AbstractColumnDescription<T> implements IColumnDescription<T>,
                Comparable<AbstractColumnDescription<T>>
{
	protected ColumnContent content;
	protected AccessibleObject obj;
	protected Class<?> type = DEF_TYPE;
	protected Locale locale;

	/*
	 * Static methods
	 */

	public static <T> IColumnDescription<T> createColumnDescription(AccessibleObject obj)
	{
		return createColumnDescription(obj, null);
	}

	public static <T> IColumnDescription<T> createColumnDescription(AccessibleObject obj, ImageProvider imageProvider)
	{
		return createColumnDescription(obj, imageProvider, Locale.getDefault());
	}

	public static <T> IColumnDescription<T> createColumnDescription(AccessibleObject obj, ImageProvider imageProvider,
	                Locale locale)
	{
		Assert.isNotNull(obj, "obj == null");

		Class<?> type = ReflectionExtension.getType(obj, DEF_TYPE);

		Assert.isNotNull(type);

		if(type.isEnum())
			return new EnumColumnDescription<T>(obj, locale);
		if(type.isAssignableFrom(Calendar.class))
			return new CalendarColumnDescription<T>(obj, locale);
		if(isBoolean(type))
			return new BooleanColumnDescription<T>(obj, imageProvider, locale);
		if(isNumeric(type))
		{
			if(isCurrency(obj))
				return new CurrencyColumnDescription<T>(obj, locale);
			if(isPercent(obj))
				return new PercentColumnDescription<T>(obj, locale);

			return new NumericColumnDescription<T>(obj, locale);
		}
		if(type.isAssignableFrom(RGB.class))
			return new ColorColumnDescription<T>(obj, locale);

		try
		{
			type.asSubclass(Localizable.class);
			return new LocalizableString<T>(obj, locale);
		}
		catch(Exception e)
		{}

		return new StringColumnDescription<T>(obj, locale);
	}

	/**
	 * Constructor
	 * 
	 * @param obj
	 * @param locale
	 */

	public AbstractColumnDescription(AccessibleObject obj, Locale locale)
	{
		if(isNull(obj))
			throw new NullPointerException("obj == null");

		setObj(obj);
		setType(obj);
		setContent(obj, locale);

		this.locale = isNotNull(locale) ? locale : Locale.getDefault();
	}

	private void setObj(AccessibleObject obj)
	{
		this.obj = obj;
	}

	public Class<?> getType()
	{
		return type;
	}

	private void setType(AccessibleObject obj)
	{
		this.type = ReflectionExtension.getType(obj, DEF_TYPE);
	}

	private void setContent(AccessibleObject obj, Locale locale)
	{
		this.content = new ColumnContent(obj.getAnnotation(Column.class), locale);
	}

	protected Collator getCollator()
	{
		return content.getCollator();
	}

	public Object invoke(T item) throws Exception
	{
		return ReflectionExtension.invoke(item, obj);
	}

	public Object invoke(T item, Object... args) throws Exception
	{
		return ReflectionExtension.invoke(item, obj, args);
	}

	protected Image getColumnImage(Object res)
	{
		return null;
	}

	protected String getText(Object obj)
	{
		return StringExtension.getText(obj, "");
	}

	protected abstract int _compare(Object obj1, Object obj2);

	/*
	 * IColumnDescription
	 */

	@Override
	public String getKey()
	{
		return getPropertyName(obj);
	}

	@Override
	public Object getEditValue(T item) throws Exception
	{
		return getValue(item);
	}

	@Override
	public final String getTextValue(T item) throws Exception
	{
		return getText(invoke(item));
	}

	@Override
	public Object getValue(T item) throws Exception
	{
		return invoke(item);
	}

	@Override
	public void setValue(T item, Object value) throws Exception
	{
		invoke(item, value);
	}

	@Override
	public void update(ViewerCell cell, T item) throws Exception
	{
		Object obj = invoke(item);

		cell.setText(getText(obj));
		cell.setImage(getColumnImage(obj));
	}

	@Override
	public TableViewerColumn createTableViewerColumn(TableViewer viewer)
	{
		if(isNull(viewer))
			throw new NullPointerException("viewer == null");

		return content.createTableColumn(viewer);
	}

	@Override
	public ColumnContent getContent()
	{
		return content;
	}

	@Override
	public String getName()
	{
		return content.getName();
	}

	// public String getUnit()
	// {
	//
	// }

	@Override
	public AccessibleObject getObject()
	{
		return obj;
	}

	@Override
	public String getToolTip()
	{
		return content.getToolTip();
	}

	@Override
	public int getOrder()
	{
		return content.getOrder();
	}

	@Override
	public boolean isMovable()
	{
		return content.isMovable();
	}

	@Override
	public boolean isResizable()
	{
		return content.isResizable();
	}

	@Override
	public boolean isReadonly()
	{
		return content.isReadonly();
	}

	@Override
	public boolean isSortable()
	{
		return content.isSortable();
	}

	@Override
	public int getAlignment()
	{
		return content.getAlignment();
	}

	@Override
	public int getWidth()
	{
		return content.getWidth();
	}

	@Override
	public boolean isVisible()
	{
		return content.isVisible();
	}

	@Override
	public boolean isHideable()
	{
		return content.isHideable();
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		if(isNull(locale))
			return;

		this.locale = locale;
		this.content.setLocale(locale);
	}

	/*
	 * Comparator
	 */

	@Override
	public int compare(T item1, T item2)
	{
		try
		{
			Object obj1 = getValue(item1);
			Object obj2 = getValue(item2);

			if(obj1 == obj2)
				return 0;
			if(isNull(obj1) ^ isNull(obj2))
				return isNull(obj2) ? 1 : -1;

			return _compare(obj1, obj2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	/*
	 * Comparable
	 */

	@Override
	public int compareTo(AbstractColumnDescription<T> obj)
	{
		return isNotNull(obj) ? content.compareTo(obj.content) : 1;
	}
}
