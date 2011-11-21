package cop.swt.widgets.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import cop.swt.widgets.annotations.services.CurrencyService;

@Inherited
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface Currency
{
	double min() default CurrencyService.DEF_MINIMUM;

	double max() default CurrencyService.DEF_MAXIMUM;

	double inc() default CurrencyService.DEF_INCREMENT;
}
