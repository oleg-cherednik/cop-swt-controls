package cop.swt.widgets.viewers.table.descriptions;

import java.lang.reflect.AccessibleObject;
import java.util.Comparator;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

import cop.swt.widgets.annotations.contents.ColumnContent;
import cop.swt.widgets.localization.interfaces.LocaleSupport;

public interface IColumnDescription<T> extends LocaleSupport, Comparator<T>
{
	ColumnContent getContent();

	String getName();

	String getKey();

	//String getUnit();

	AccessibleObject getObject();

	String getToolTip();

	int getOrder();

	boolean isMovable();

	boolean isResizable();

	boolean isReadonly();

	boolean isSortable();

	int getAlignment();

	int getWidth();

	boolean isVisible();

	boolean isHideable();

	// getTextValue() or getValue()
	Object getEditValue(T item) throws Exception;

	//	// for String - the same as getValue. for Combo - not pos, but string[pos]
	String getTextValue(T item) throws Exception;

	Object getValue(T item) throws Exception;

	void setValue(T item, Object value) throws Exception;

	void update(ViewerCell cell, T item) throws Exception;

	TableViewerColumn createTableViewerColumn(TableViewer viewer);

	CellEditor getCellEditor(Composite parent);

	//int compare(T item1, T item2);
}
