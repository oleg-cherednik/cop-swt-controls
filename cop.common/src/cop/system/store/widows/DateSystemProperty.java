package cop.system.store.widows;

import static cop.system.store.enums.DatePropertyEnum.YEAR_MIN2;
import static java.lang.Long.parseLong;
import cop.system.store.enums.DatePropertyEnum;

final class DateSystemProperty extends WindowsSystemProperty<DatePropertyEnum>
{
	@Override
	public String[] getPropertyPath(DatePropertyEnum property)
	{
		if(property == null)
			return null;

		switch(property)
		{
		case DATE_SHORT:
			return new String[] { INTERNATIONAL, "sShortDate" };
		case DATE_LONG:
			return new String[] { INTERNATIONAL, "sLongDate" };
		case DATE_SEPARATOR:
			return new String[] { INTERNATIONAL, "sDate" };
		case YEAR_MIN2:
		case YEAR_MAX2:
			return new String[] { TWO_DIGIT_YEAR_MAX, "1" };
		default:
			return new String[] { "unknown", "unknown" };
		}
	}

	@Override
	protected DatePropertyEnum[] getValues()
	{
		return DatePropertyEnum.values();
	}

	@Override
	protected String doAfter(DatePropertyEnum name, String value, String defaultValue)
	{
		if(name != YEAR_MIN2)
			return (value != null) ? value : defaultValue;

		try
		{
			// value - is a YEAR_MAX2. YEAR_MIN2 = YEAR_MAX2 - 99
			return "" + (parseLong(value) - 99);
		}
		catch(Exception e)
		{
			return defaultValue;
		}
	}
}
