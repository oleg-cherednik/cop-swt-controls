package cop.swt.widgets.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import cop.swt.widgets.annotations.services.PercentService;

@Inherited
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface Percent
{
	double min() default PercentService.DEF_MINIMUM;

	double max() default PercentService.DEF_MAXIMUM;

	double inc() default PercentService.DEF_INCREMENT;
}
