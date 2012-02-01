package cop.swt.widgets.annotations;

import static cop.swt.widgets.annotations.services.IntegerRangeService.DEF_INCREMENT;
import static cop.swt.widgets.annotations.services.IntegerRangeService.DEF_MAXIMUM;
import static cop.swt.widgets.annotations.services.IntegerRangeService.DEF_MINIMUM;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Inherited
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface IntegerRange
{
	int min() default DEF_MINIMUM;

	int max() default DEF_MAXIMUM;

	int inc() default DEF_INCREMENT;
}
