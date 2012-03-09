/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.columns.settings;

import static cop.extensions.CompareExt.compareNumbers;
import static cop.extensions.ReflectionExt.isBoolean;

import java.lang.reflect.AccessibleObject;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import cop.swt.images.ImageProvider;
import cop.swt.widgets.annotations.contents.ImageTextViewContent;
import cop.swt.widgets.annotations.services.ImageTextViewService;
import cop.swt.widgets.viewers.table.columns.ColumnContext;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 03.09.2010
 */
public class BooleanColumn<T> extends AbstractColumnSettings<T> {
	public static final String CHECKED_MARKER = "checked";
	public static final String UNCHECKED_MARKER = "unchecked";

	private final ImageProvider imageProvider;
	private final ImageTextViewContent viewContent;

	protected BooleanColumn(AccessibleObject obj, ColumnContext context) {
		super(obj, context);

		this.imageProvider = context.getImageProvider();
		this.viewContent = getImageTextViewContent(obj);
	}

	private static ImageTextViewContent getImageTextViewContent(AccessibleObject obj) {
		return ImageTextViewService.getImageTextViewContent(obj);
	}

	/*
	 * Comparator
	 */

	@Override
	public int compare(T item1, T item2) {
		try {
			return compareNumbers(Boolean.TYPE, getValue(item1), getValue(item2));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/*
	 * ColumnDescription
	 */

	@Override
	public CellEditor getCellEditor(Composite parent) {
		if (editor == null)
			editor = new CheckboxCellEditor(parent, SWT.CHECK | SWT.READ_ONLY);
		return editor;
	}

	@Override
	protected Image getColumnImage(Object res) {
		switch(viewContent.getView())
		{
		case IMAGE_ONLY:
		case IMAGE_AND_TEXT:
			if (imageProvider == null)
				return null;
			return imageProvider.getImage((Boolean)res ? CHECKED_MARKER : UNCHECKED_MARKER);
		default:
			return null;
		}
	}

	@Override
	protected String getText(Object obj) {
		switch(viewContent.getView())
		{
		case TEXT_ONLY:
		case IMAGE_AND_TEXT:
			return super.getText(obj);
		default:
			return "";
		}
	}

	@Override
	protected void check() {
		if (!isBoolean(type))
			throw new IllegalArgumentException("Given object is not Boolean");
	}
}
