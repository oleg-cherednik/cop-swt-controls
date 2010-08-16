package cop.swt.widgets.comparators;

import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isNotEmpty;
import static cop.swt.widgets.annotations.services.LabelService.getItemName;

import java.util.Comparator;

import org.eclipse.core.runtime.Assert;

import cop.swt.widgets.annotations.exceptions.WrongReturnValueException;
import cop.swt.widgets.annotations.services.LabelService;
import cop.swt.widgets.interfaces.LabelSupport;

public final class LabelComparator<T> implements LabelSupport, Comparator<T>
{
	private String labelName;

	public static <T> Comparator<T> createLabelComparator(T obj, String labelName)
	{
		Assert.isNotNull(obj);

		Class<?> type;
		
		try
		{
			type = LabelService.getItemClass(obj, labelName);
			
			int a = 0;
			a++;

			if(type.isEnum())
				return new EnumComparator<T>();
			//		if(isBoolean(type))
			//			return new CBooleanColumnDescription<T>(obj, imageProvider, locale);
			//		if(isNumeric(type))
			//		{
			//			if(isCurrency(obj))
			//				return new CurrencyColumnDescription<T>(obj, locale);
			//			if(isPercent(obj))
			//				return new PercentColumnDescription<T>(obj, locale);
			//
			//			return new NumericColumnDescription<T>(obj, locale);
			//		}
			//		if(type.isAssignableFrom(RGB.class))
			//			return new ColorColumnDescription<T>(obj, locale);

			//return new StringLabelDescription<T>(obj, locale);

		}
		catch(WrongReturnValueException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new Comparator<T>()
		{
			@Override
			public int compare(T obj1, T obj2)
			{
				if(obj1 == obj2)
					return 0;
				if(isNull(obj1) ^ isNull(obj2))
					return isNull(obj2) ? 1 : -1;

				return ((String)obj1).compareTo((String)obj2);
			}
		};
	}

	/*
	 * Comparator
	 */

	@Override
    public int compare(T obj1, T obj2)
	{
		if(obj1 == obj2)
			return 0;
		if(isNull(obj1) ^ isNull(obj2))
			return isNull(obj2) ? 1 : -1;

		try
		{
			String str1 = getItemName(obj1, labelName);
			String str2 = getItemName(obj2, labelName);

			if(isNull(str1) ^ isNull(str2))
				return isNull(obj1) ? 1 : -1;

			return str1.compareTo(str2);
		}
		catch(WrongReturnValueException e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	/*
	 * LabelSupport
	 */

	@Override
    public void setLabelName(String labelName)
	{
		this.labelName = isNotEmpty(labelName) ? labelName.trim() : null;
	}

	@Override
    public String getLabelName()
	{
		return labelName;
	}
}
