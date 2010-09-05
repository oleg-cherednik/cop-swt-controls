package cop.swt.widgets.viewers.list.descriptions;

import static cop.common.beans.JavaBean.getPropertyName;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.ReflectionExtension.isBoolean;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_TYPE;

import java.lang.reflect.AccessibleObject;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

import cop.common.extensions.ReflectionExtension;
import cop.common.extensions.StringExtension;
import cop.swt.images.ImageProvider;

public abstract class AbstractLabelDescription<T> implements Comparator<T>, ILabelDescription<T> //IColumnDescription<T>,
//Comparable<AbstractColumnDescription<T>>
{
	private Collator collator;
	private AccessibleObject obj;
	protected Class<?> type = DEF_TYPE;
	protected Locale locale;
	private ImageProvider imageProvider;
	private String labelName;
	

	/*
	 * Static methods
	 */

	public static <T> ILabelDescription<T> createLabelDescription(AccessibleObject obj)
	{
		return createLabelDescription(obj, null);
	}

	public static <T> ILabelDescription<T> createLabelDescription(AccessibleObject obj, ImageProvider imageProvider)
	{
		return createLabelDescription(obj, imageProvider, Locale.getDefault());
	}

	public static <T> ILabelDescription<T> createLabelDescription(AccessibleObject obj, ImageProvider imageProvider,
	                Locale locale)
	{
		if(isNull(obj))
			throw new NullPointerException("obj == null");

		Class<?> type = ReflectionExtension.getType(obj, String.class);

		if(type.isEnum())
			return new EnumLabelDescription<T>(obj, locale);

		//		if(type.isAssignableFrom(Calendar.class))
		//			return new CalendarColumnDescription<T>(obj, locale);
		if(isBoolean(type))
			return new BooleanLabelDescription<T>(obj, locale);
		//		if(isNumeric(type))
		//		{
		//			if(isCurrency(obj))
		//				return new CurrencyColumnDescription<T>(obj, locale);
		//			if(isPercent(obj))
		//				return new PercentColumnDescription<T>(obj, locale);
		//
		//			return new NumericColumnDescription<T>(obj, locale);
		//		}
		//		if(type.isAssignableFrom(RGB.class))
		//			return new ColorColumnDescription<T>(obj, locale);

		return new StringLabelDescription<T>(obj, locale);
	}

	/**
	 * Constructor
	 * 
	 * @param obj
	 * @param locale
	 */

	public AbstractLabelDescription(AccessibleObject obj, Locale locale)
	{
		if(isNull(obj))
			throw new NullPointerException("obj == null");

		this.obj = obj;
		this.type = ReflectionExtension.getType(obj, DEF_TYPE);
		//this.content = new ColumnContent(obj.getAnnotation(Column.class), locale);
		this.locale = isNotNull(locale) ? locale : Locale.getDefault();
		
		setCollator(locale);
	}

	public Class<?> getType()
	{
		return type;
	}

	protected Collator getCollator()
	{
		return collator;
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
		return StringExtension.getText(obj);
	}
	
	private void setCollator(Locale locale)
	{
		collator = Collator.getInstance(locale);
	}

	protected abstract int _compare(Object obj1, Object obj2);

	/*
	 * IColumnDescription
	 */

	//@Override
	public String getKey()
	{
		return getPropertyName(obj);
	}

	//@Override
	public Object getEditValue(T item) throws Exception
	{
		return getValue(item);
	}

	@Override
	public final String getTextValue(T item) throws Exception
	{
		return getText(invoke(item));
	}

	//@Override
	public Object getValue(T item) throws Exception
	{
		return invoke(item);
	}

	//@Override
	public void setValue(T item, Object value) throws Exception
	{
		invoke(item, value);
	}

	//@Override
	public void update(ViewerCell cell, T item) throws Exception
	{
		Object obj = invoke(item);

		cell.setText(getText(obj));
		cell.setImage(getColumnImage(obj));
	}

	//	@Override
	//	public TableViewerColumn createTableViewerColumn(TableViewer viewer)
	//	{
	//		if(isNull(viewer))
	//			throw new NullPointerException("viewer == null");
	//
	//		return content.createTableColumn(viewer);
	//	}

	//	@Override
	//	public ColumnContent getContent()
	//	{
	//		return content;
	//	}

	//	@Override
	//	public String getName()
	//	{
	//		return content.getName();
	//	}

	// public String getUnit()
	// {
	//
	// }

	//@Override
	public AccessibleObject getObject()
	{
		return obj;
	}

	//	@Override
	//	public String getToolTip()
	//	{
	//		return content.getToolTip();
	//	}

	//	@Override
	//	public int getOrder()
	//	{
	//		return content.getOrder();
	//	}

	//	@Override
	//	public boolean isMovable()
	//	{
	//		return content.isMovable();
	//	}

	//	@Override
	//	public boolean isResizable()
	//	{
	//		return content.isResizable();
	//	}

	//	@Override
	//	public boolean isReadonly()
	//	{
	//		return content.isReadonly();
	//	}

	//	@Override
	//	public boolean isSortable()
	//	{
	//		return content.isSortable();
	//	}

	//	@Override
	//	public int getAlignment()
	//	{
	//		return content.getAlignment();
	//	}

	//	@Override
	//	public int getWidth()
	//	{
	//		return content.getWidth();
	//	}

	//	@Override
	//	public boolean isVisible()
	//	{
	//		return content.isVisible();
	//	}

	//	@Override
	//	public boolean isHideable()
	//	{
	//		return content.isHideable();
	//	}

	/*
	 * Localizable
	 */

	@Override
    public void setLocale(Locale locale)
	{
		if(isNull(locale))
			return;
		this.locale = locale;
		setCollator(locale);
		//this.content.setLocale(locale);
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
	 * LabelSupport
	 */
	
	@Override
    public String getLabelName()
    {
	    return labelName;
    }

	@Override
    public void setLabelName(String labelName)
    {
	    this.labelName = labelName;
    }

	/*
	 * Comparable
	 */

	//	@Override
	//	public int compareTo(AbstractColumnDescription<T> obj)
	//	{
	//		return isNotNull(obj) ? content.compareTo(obj.content) : 0;
	//	}
}