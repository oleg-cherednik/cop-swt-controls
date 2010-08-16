/**
 * $Id: ImageTextView.java 51 2010-08-16 12:25:56Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.swt/cop.swt/src/cop/swt/widgets/annotations/ImageTextView.java $
 */
package cop.swt.widgets.annotations;

import static cop.swt.widgets.enums.ImageTextViewEnum.IMAGE_ONLY;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import cop.swt.widgets.enums.ImageTextViewEnum;

@Inherited
@Documented
@Retention(RUNTIME)
@Target( { FIELD, METHOD })
public @interface ImageTextView
{
	ImageTextViewEnum view() default IMAGE_ONLY;
}
