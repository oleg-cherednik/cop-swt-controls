package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.swt.widgets.annotations.services.ImageTextViewService.DEF_IMAGETEXTVIEW;
import static cop.swt.widgets.annotations.services.ImageTextViewService.getImageTextViewContent;
import static cop.swt.widgets.enums.ImageTextViewEnum.TEXT_ONLY;
import static org.eclipse.swt.SWT.CHECK;
import static org.eclipse.swt.SWT.READ_ONLY;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import cop.swt.images.ImageProvider;
import cop.swt.widgets.annotations.contents.ImageTextViewContent;
import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;

public class BooleanColumnDescription<T> extends AbstractColumnDescription<T>
{
	public static final String CHECKED_MARKER = "checked";
	public static final String UNCHECKED_MARKER = "unchecked";

	private ImageTextViewContent viewContent;
	private ImageProvider imageProvider;

	protected BooleanColumnDescription(AccessibleObject obj, ImageProvider imageProvider, Locale locale)
	{
		super(obj, locale);

		try
		{
			this.imageProvider = imageProvider;
			viewContent = getImageTextViewContent(obj);
		}
		catch(AnnotationDeclarationException e)
		{
			viewContent = new ImageTextViewContent(isNotNull(imageProvider) ? DEF_IMAGETEXTVIEW : TEXT_ONLY);
		}
	}

	/*
	 * AbstractColumnDescription
	 */

	@Override
	protected int _compare(Object obj1, Object obj2)
	{
		return ((Boolean)obj1).compareTo((Boolean)obj2);
	}

	/*
	 * IColumnDescription
	 */

	@Override
	public CellEditor getCellEditor(Composite parent)
	{
		return new CheckboxCellEditor(parent, CHECK | READ_ONLY);
	}

	/*
	 * AbstractColumnDescription
	 */

	@Override
	protected Image getColumnImage(Object res)
	{
		switch(viewContent.getView())
		{
		case IMAGE_ONLY:
		case IMAGE_AND_TEXT:
			Assert.isNotNull(imageProvider);

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
