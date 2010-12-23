/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.annotations.services;

import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.ReflectionExtension.getType;
import static cop.common.extensions.ReflectionExtension.isNotBoolean;
import static cop.swt.widgets.enums.ImageTextViewEnum.IMAGE_ONLY;

import java.lang.reflect.AccessibleObject;

import org.eclipse.core.runtime.Assert;

import cop.swt.widgets.annotations.ImageTextView;
import cop.swt.widgets.annotations.contents.ImageTextViewContent;
import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;
import cop.swt.widgets.enums.ImageTextViewEnum;

public final class ImageTextViewService
{
	public static final ImageTextViewEnum DEF_IMAGETEXTVIEW = IMAGE_ONLY;

	/**
	 * Closed constructor
	 */
	private ImageTextViewService()
	{}

	public static <T> ImageTextViewContent getImageTextViewContent(AccessibleObject obj)
	                throws AnnotationDeclarationException
	{
		if(isNull(obj))
			return new ImageTextViewContent(DEF_IMAGETEXTVIEW);

		checkObj(obj);

		return new ImageTextViewContent(obj.getAnnotation(ImageTextView.class));
	}

	private static void checkObj(AccessibleObject obj)
	{
		Assert.isNotNull(obj);

		//FIXME can be use with boolean or RGB
		if(isNotBoolean(getType(obj, null)))
			throw new IllegalArgumentException(
			                "@ImageTextView annotation can be used only with Boolean or boolean type");
	}
}
