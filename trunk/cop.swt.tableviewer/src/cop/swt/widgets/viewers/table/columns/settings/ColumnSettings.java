/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.columns.settings;

import java.lang.reflect.AccessibleObject;
import java.util.Comparator;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import cop.localization.interfaces.LocaleSupport;
import cop.swt.widgets.annotations.contents.ColumnContent;
import cop.swt.widgets.viewers.table.PTableViewer;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 04.01.2012
 */
public interface ColumnSettings<T> extends LocaleSupport, Comparator<T>, Comparable<AbstractColumnSettings<T>>
{
	Class<?> getType();

	Object invoke(T item) throws Exception;

	Object invoke(T item, Object... args) throws Exception;

	String getKey();

	Object getCellEditorValue(T item) throws Exception;

	String getTextValue(T item) throws Exception;

	Object getValue(T item) throws Exception;

	void setValue(T item, Object value) throws Exception;

	void update(ViewerCell cell, T item) throws Exception;
	
	ColumnContent getContent();

	TableViewerColumn createTableViewerColumn(PTableViewer<T> tableViewer, EditingSupport editor);

	String getName();

	CellEditor getCellEditor(Composite parent);

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

	boolean isEmptyable();

	void handleEvent(Event event, TableViewer tableViewer, TableViewerColumn columnViewer);
}
