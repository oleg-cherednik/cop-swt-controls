package cop.swt.widgets.viewers.descriptions;

import static cop.extensions.CommonExt.isNotNull;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

import cop.i18.EditLocalizable;
import cop.i18.Localizable;

public class LocalizableString<T> extends StringLabelDescription<T>
{
	protected LocalizableString(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);

//		try
//		{
//			type.asSubclass(EditLocalizable.class);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
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
