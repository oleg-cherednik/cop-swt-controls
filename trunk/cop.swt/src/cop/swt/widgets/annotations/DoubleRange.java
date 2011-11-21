package cop.swt.widgets.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import cop.swt.widgets.annotations.services.RangeService;

@Inherited
@Documented
@Retention(RUNTIME)
@Target( { FIELD, METHOD })
public @interface DoubleRange
{
	double minimum() default RangeService.DEF_MINIMUM;
	double maximum() default RangeService.DEF_MAXIMUM;
}
