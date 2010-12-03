package cop.swt.widgets.viewers.list.descriptions;

import static cop.common.extensions.CommonExtension.isNotNull;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

import cop.swt.widgets.localization.interfaces.EditLocalizable;
import cop.swt.widgets.localization.interfaces.Localizable;

public class LocalizableString<T> extends StringLabelDescription<T>
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
			e.printStackTrace();
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
	@SuppressWarnings("unchecked")
	protected String getText(Object obj)
	{
		return isNotNull(obj) ? ((Localizable<String>)obj).i18n(locale) : "";
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setValue(T item, Object value) throws Exception
	{
		((EditLocalizable)getValue(item)).setI18n(value, locale);
	}
}
