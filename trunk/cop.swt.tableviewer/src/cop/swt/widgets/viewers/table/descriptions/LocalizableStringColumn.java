/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.ReflectionExtension.isLocalizable;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

import cop.localization.interfaces.EditLocalizable;
import cop.localization.interfaces.Localizable;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 03.09.2010
 */
public class LocalizableStringColumn<T> extends StringColumn<T>
{
	protected LocalizableStringColumn(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);
		checkForEditable();
	}

	private void checkForEditable()
	{
		try
		{
			type.asSubclass(EditLocalizable.class);
		}
		catch(Exception e)
		{
			content.setReadonly(true);
		}
	}

	/*
	 * ColumnDescription
	 */

	@Override
	protected int _compare(Object obj1, Object obj2)
	{
		return super._compare(getText(obj1), getText(obj2));
	}

	@Override
	protected String getText(Object obj)
	{
		return (obj != null) ? ((Localizable)obj).i18n(locale) : "";
	}

	@Override
	public void setValue(T item, Object value) throws Exception
	{
		((EditLocalizable)getValue(item)).setI18n((String)value, locale);
	}

	@Override
	protected void check()
	{
		if(!isLocalizable(obj))
			throw new IllegalArgumentException("Given object does not implement Localizable");
	}
}
