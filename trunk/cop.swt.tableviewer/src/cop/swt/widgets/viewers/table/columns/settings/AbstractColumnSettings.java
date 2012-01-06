/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.columns.settings;

import static cop.common.beans.JavaBean.getPropertyName;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_TYPE;

import java.lang.reflect.AccessibleObject;
import java.text.Collator;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import cop.common.extensions.ReflectionExtension;
import cop.common.extensions.StringExtension;
import cop.swt.widgets.annotations.Column;
import cop.swt.widgets.annotations.contents.ColumnContent;
import cop.swt.widgets.viewers.table.PTableViewer;
import cop.swt.widgets.viewers.table.columns.ColumnContext;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 03.09.2010
 */
public abstract class AbstractColumnSettings<T> implements ColumnSettings<T>
{
	protected ColumnContent content;
	protected AccessibleObject obj;
	protected Class<?> type = DEF_TYPE;
	protected Locale locale;
	protected CellEditor editor;

	protected AbstractColumnSettings(AccessibleObject obj, ColumnContext context)
	{
		this.obj = obj;
		this.type = ReflectionExtension.getType(obj, DEF_TYPE);
		this.locale = (context.getLocale() != null) ? context.getLocale() : Locale.getDefault();
		this.content = new ColumnContent(obj.getAnnotation(Column.class), locale);

		check();
	}

	protected void check()
	{}

	public Class<?> getType()
	{
		return type;
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
	public int compareTo(AbstractColumnSettings<T> obj)
	{
		return isNotNull(obj) ? content.compareTo(obj.content) : 1;
	}

	public void handleEvent(Event event, TableViewer tableViewer, TableViewerColumn columnViewer)
	{}
}
