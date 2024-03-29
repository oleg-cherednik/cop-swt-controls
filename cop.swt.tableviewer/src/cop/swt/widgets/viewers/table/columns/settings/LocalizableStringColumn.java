/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.columns.settings;

import static cop.extensions.CompareExt.compareStrings;
import static cop.i18n.LocalizationExt.isLocalizable;

import java.lang.reflect.AccessibleObject;

import cop.i18n.EditLocalizable;
import cop.i18n.Localizable;
import cop.swt.widgets.viewers.table.columns.ColumnContext;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 03.09.2010
 */
public class LocalizableStringColumn<T> extends StringColumn<T>
{
	protected LocalizableStringColumn(AccessibleObject obj, ColumnContext context)
	{
		super(obj, context);
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
	 * Comparator
	 */

	@Override
	public int compare(T item1, T item2)
	{
		try
		{
			String str1 = getText(getValue(item1));
			String str2 = getText(getValue(item2));

			return compareStrings(str1, str2, getCollator());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	/*
	 * ColumnDescription
	 */

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
