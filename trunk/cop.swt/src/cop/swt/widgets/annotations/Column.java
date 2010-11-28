/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.annotations;

import static cop.swt.widgets.annotations.services.ColumnService.*;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_MOVABLE;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_NAME;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_ORDER;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_READONLY;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_RISIZABLE;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_SORTABLE;
import static cop.swt.widgets.annotations.services.ColumnService.DEF_TOOLTIP;
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
public @interface Column
{
	String name() default DEF_NAME;

	String toolTip() default DEF_TOOLTIP;

	int order() default DEF_ORDER; // order by name

	/**
	 * Sets the movable attribute. A column that is movable can be reordered by the user by dragging the header. A
	 * column that is not movable cannot be dragged by the user but may be reordered by the programmer.
	 * 
	 * @see org.eclipse.swt.widgets.TableColumn#setMoveable(boolean)
	 */
	boolean movabale() default DEF_MOVABLE;

	boolean resizable() default DEF_RISIZABLE;

	/**
	 * Instances of this class represent a column in a table widget.
	 * <p>
	 * Note: Only one of the styles <code>LEFT</code>, <code>RIGHT</code> and <code>CENTER</code> may be specified.
	 * 
	 * @see org.eclipse.swt.widgets.TableColumn#setAlignment(int)
	 */
	int alignment() default DEF_ALIGNMENT;

	boolean readonly() default DEF_READONLY;

	boolean sortable() default DEF_SORTABLE;

	/**
	 * Relative column width in percents from table with.
	 */
	int width() default DEF_WIDTH;

	boolean visible() default DEF_VISIBLE;

	boolean hideable() default DEF_HIDEABLE;

	boolean emptyable() default DEF_EMPTYABLE;
}
