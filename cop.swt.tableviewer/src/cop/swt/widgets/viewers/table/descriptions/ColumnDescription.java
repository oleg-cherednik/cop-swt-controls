package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.beans.JavaBean.getPropertyName;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.ReflectionExtension.isBoolean;
import static cop.common.extensions.ReflectionExtension.isInteger;
import static cop.common.extensions.ReflectionExtension.isLocalizable;
import static cop.common.extensions.ReflectionExtension.isNumeric;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_TYPE;
import static cop.swt.widgets.annotations.services.CurrencyService.isCurrency;
import static cop.swt.widgets.annotations.services.PercentService.isPercent;

import java.lang.reflect.AccessibleObject;
import java.text.Collator;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import cop.common.extensions.ReflectionExtension;
import cop.common.extensions.StringExtension;
import cop.localization.interfaces.LocaleSupport;
import cop.swt.images.ImageProvider;
import cop.swt.widgets.annotations.Column;
import cop.swt.widgets.annotations.contents.ColumnContent;
import cop.swt.widgets.viewers.table.PTableViewer;

public abstract class ColumnDescription<T> implements LocaleSupport, Comparator<T>, Comparable<ColumnDescription<T>>
{
	protected ColumnContent content;
	protected AccessibleObject obj;
	protected Class<?> type = DEF_TYPE;
	protected Locale locale;
	protected CellEditor editor;

	/*
	 * Static methods
	 */

	public static <T> ColumnDescription<T> createColumnDescription(AccessibleObject obj)
	{
		return createColumnDescription(obj, null);
	}

	public static <T> ColumnDescription<T> createColumnDescription(AccessibleObject obj, ImageProvider imageProvider)
	{
		return createColumnDescription(obj, imageProvider, Locale.getDefault());
	}

	public static <T> ColumnDescription<T> createColumnDescription(AccessibleObject obj, ImageProvider imageProvider,
	                Locale locale)
	{
		Assert.isNotNull(obj, "obj == null");

		Class<?> type = ReflectionExtension.getType(obj, DEF_TYPE);

		Assert.isNotNull(type);

		if(type.isEnum())
			return new EnumColumn<T>(obj, locale);
		if(type.isAssignableFrom(Calendar.class))
			return new CalendarColumnDescription<T>(obj, locale);
		if(isBoolean(type))
			return new BooleanColumn<T>(obj, imageProvider, locale);
		if(isNumeric(type))
		{
			if(isCurrency(obj))
				return new CurrencyColumnDescription<T>(obj, locale);
			if(isPercent(obj))
				return new PercentColumn<T>(obj, locale);
			if(isInteger(type))
				return new IntegerNumberColumn<T>(obj, locale);

			return new NumericColumn<T>(obj, locale);
		}
		if(type.isAssignableFrom(RGB.class))
			return new ColorColumn<T>(obj, locale);
		if(isLocalizable(obj))
			return new LocalizableStringColumn<T>(obj, locale);

		return new StringColumn<T>(obj, locale);
	}

	public ColumnDescription(AccessibleObject obj, Locale locale)
	{
		setObj(obj);
		setType(obj);
		setContent(obj, locale);

		this.locale = (locale != null) ? locale : Locale.getDefault();

		check();
	}

	protected void check()
	{}

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

	public String getKey()
	{
		return getPropertyName(obj);
	}

	public Object getCellEditorValue(T item) throws Exception
	{
		return getValue(item);
	}

	protected String getCellText(Object obj)
	{
		return getText(obj);
	}

	public final String getTextValue(T item) throws Exception
	{
		return getText(invoke(item));
	}

	public Object getValue(T item) throws Exception
	{
		return invoke(item);
	}

	public void setValue(T item, Object value) throws Exception
	{
		invoke(item, value);
	}

	public void update(ViewerCell cell, T item) throws Exception
	{
		Object obj = invoke(item);

		cell.setText(getCellText(obj));
		cell.setImage(getColumnImage(obj));
	}

	public TableViewerColumn createTableViewerColumn(PTableViewer<T> tableViewer, EditingSupport editor)
	{
		TableViewerColumn viewerColumn = content.createTableColumn(tableViewer.getWidget());

		if(editor != null)
			viewerColumn.setEditingSupport(editor);

		return viewerColumn;
	}

	public ColumnContent getContent()
	{
		return content;
	}

	public String getName()
	{
		return content.getName();
	}

	public abstract CellEditor getCellEditor(Composite parent);

	public AccessibleObject getObject()
	{
		return obj;
	}

	public String getToolTip()
	{
		return content.getToolTip();
	}

	public int getOrder()
	{
		return content.getOrder();
	}

	public boolean isMovable()
	{
		return content.isMovable();
	}

	public boolean isResizable()
	{
		return content.isResizable();
	}

	public boolean isReadonly()
	{
		return content.isReadonly();
	}

	public boolean isSortable()
	{
		return content.isSortable();
	}

	public int getAlignment()
	{
		return content.getAlignment();
	}

	public int getWidth()
	{
		return content.getWidth();
	}

	public boolean isVisible()
	{
		return content.isVisible();
	}

	public boolean isHideable()
	{
		return content.isHideable();
	}

	public boolean isEmptyable()
	{
		return content.isEmptyable();
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		if(locale == null)
			return;

		this.locale = locale;
		this.content.setLocale(locale);

		if(editor != null)
			editor.dispose();

		editor = null;
	}

	/*
	 * Comparable
	 */

	@Override
	public int compareTo(ColumnDescription<T> obj)
	{
		return isNotNull(obj) ? content.compareTo(obj.content) : 1;
	}

	public void handleEvent(Event event, TableViewer tableViewer, TableViewerColumn columnViewer)
	{}
}
