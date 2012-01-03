/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CompareExtension.compareNumbers;
import static cop.swt.widgets.annotations.services.ImageTextViewService.DEF_IMAGETEXTVIEW;
import static cop.swt.widgets.enums.ImageTextViewEnum.TEXT_ONLY;
import static org.eclipse.swt.SWT.CHECK;
import static org.eclipse.swt.SWT.READ_ONLY;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import cop.swt.images.ImageProvider;
import cop.swt.widgets.annotations.contents.ImageTextViewContent;
import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;
import cop.swt.widgets.annotations.services.ImageTextViewService;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 03.01.2012
 */
public class BooleanColumn<T> extends ColumnDescription<T>
{
	public static final String CHECKED_MARKER = "checked";
	public static final String UNCHECKED_MARKER = "unchecked";

	private final ImageProvider imageProvider;
	private final ImageTextViewContent viewContent;

	protected BooleanColumn(AccessibleObject obj, ImageProvider imageProvider, Locale locale)
	{
		super(obj, locale);

		this.imageProvider = imageProvider;
		this.viewContent = getImageTextViewContent(obj);
	}

	private ImageTextViewContent getImageTextViewContent(AccessibleObject obj)
	{
		try
		{
			return ImageTextViewService.getImageTextViewContent(obj);
		}
		catch(AnnotationDeclarationException e)
		{
			return new ImageTextViewContent((imageProvider != null) ? DEF_IMAGETEXTVIEW : TEXT_ONLY);
		}
	}

	/*
	 * Comparator
	 */

	@Override
	public int compare(T item1, T item2)
	{
		try
		{
			return compareNumbers(Boolean.TYPE, getValue(item1), getValue(item2));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	/*
	 * ColumnDescription
	 */

	@Override
	public CellEditor getCellEditor(Composite parent)
	{
		return new CheckboxCellEditor(parent, CHECK | READ_ONLY);
	}

	@Override
	protected Image getColumnImage(Object res)
	{
		switch(viewContent.getView())
		{
		case IMAGE_ONLY:
		case IMAGE_AND_TEXT:
			if(imageProvider == null)
				return null;
			return imageProvider.getImage((Boolean)res ? CHECKED_MARKER : UNCHECKED_MARKER);
		default:
			return null;
		}
	}

	@Override
	protected String getText(Object obj)
	{
		switch(viewContent.getView())
		{
		case TEXT_ONLY:
		case IMAGE_AND_TEXT:
			return super.getText(obj);
		default:
			return "";
		}
	}
}
