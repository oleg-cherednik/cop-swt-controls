package cop.swt.widgets.viewers.list.descriptions;

import static cop.algorithms.search.LinearSearch.linearSearch;
import static cop.common.extensions.ArrayExtension.convertToStringArray;
import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.ArrayExtension.isNotEmpty;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.swt.widgets.annotations.services.i18nService.getTranslations;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;

public class EnumLabelDescription<T> extends AbstractLabelDescription<T>
{
	private Object[] constatns;
	private String[] i18n;

	/**
	 * Closed constructor
	 * 
	 * @param obj
	 * @param locale
	 */
	protected EnumLabelDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);

		readI18nAnnotation();
	}

	private void readI18nAnnotation()
	{
		try
		{
			if(!getType().isEnum())
				return;

			constatns = getType().getEnumConstants();
			i18n = getTranslations(getType(), locale);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Object[] getConstatns()
	{
		return constatns;
	}

	public String[] getI18n()
	{
		return i18n;
	}

	public String[] getStringItems()
	{
		return isNotEmpty(i18n) ? i18n : convertToStringArray(constatns);
	}

	public String translate(Object obj)
	{
		if(isNull(obj) || isEmpty(i18n))
			return "" + obj;

		int pos = linearSearch(constatns, obj);

		return (pos >= 0) ? i18n[pos] : ("" + obj);
	}

	/*
	 * AbstractColumnDescription
	 */

	@Override
	@SuppressWarnings("unchecked")
	protected int _compare(Object obj1, Object obj2)
	{
		return ((Enum)obj1).compareTo((Enum)obj2);
	}

	/*
	 * IColumnDescription
	 */

	// @Override
	// public CellEditor getCellEditor(Composite parent)
	// {
	// return new ComboBoxCellEditor(parent, getStringItems(), READ_ONLY);
	// }

	@Override
	public Object getValue(T item) throws Exception
	{
		Object str = invoke(item);
		Object[] values = type.getEnumConstants();

		// ILocalProvider
		int pos = linearSearch(values, str);

		return (pos < 0) ? 0 : pos;
	}

	@Override
	public void setValue(T item, Object value) throws Exception
	{
		Object[] values = type.getEnumConstants();
		Integer index = (Integer)value;

		invoke(item, values[index]);
	}

	/*
	 * AbstractColumnDescription
	 */

	@Override
	protected String getText(Object obj)
	{
		return isNotNull(obj) ? translate(obj) : "";
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		try
		{
			if(isNotNull(locale))
				i18n = getTranslations(getType(), locale);
		}
		catch(AnnotationDeclarationException e)
		{
			e.printStackTrace();
		}
	}
}
