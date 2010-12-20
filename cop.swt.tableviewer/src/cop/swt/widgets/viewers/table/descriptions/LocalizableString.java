package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CommonExtension.isNotNull;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

import cop.swt.widgets.localization.interfaces.EditLocalizable;
import cop.swt.widgets.localization.interfaces.Localizable;

public class LocalizableString<T> extends StringColumnDescription<T>
{
	protected LocalizableString(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);

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
		return isNotNull(obj) ? ((Localizable)obj).i18n(locale) : "";
	}

	@Override
	public void setValue(T item, Object value) throws Exception
	{
		((EditLocalizable)getValue(item)).setI18n((String)value, locale);
	}
}
