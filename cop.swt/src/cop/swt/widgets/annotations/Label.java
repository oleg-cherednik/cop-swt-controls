/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import cop.swt.widgets.annotations.services.LabelService;

/**
 * This annotation is used to mark method or property in the class.<br>
 * Usually it used to mark class' field or method to get representation string in templated viewers.<br>
 * <br>
 * This annotation can be used only for fields or methods that return not null value.<br>
 * <br>
 * In some cases you can use <b>name</b> field to set <b>label name</b>. To set this name <b>ILabelName</b> can be used.
 * 
 * @author cop (Cherednik, Oleg)
 * @see ILabelName
 * @see LabelService
 */
@Inherited
@Documented
@Retention(RUNTIME)
@Target( { FIELD, METHOD })
public @interface Label
{
	/**
	 * Use to set special name for the label.
	 * 
	 * @return label name string
	 */
	String name() default "";
}
