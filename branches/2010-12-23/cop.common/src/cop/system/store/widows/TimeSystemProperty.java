package cop.system.store.widows;

import cop.system.store.enums.TimePropertyEnum;

final class TimeSystemProperty extends WindowsSystemProperty<TimePropertyEnum>
{
	@Override
	public String[] getPropertyPath(TimePropertyEnum property)
	{
		if(property == null)
			return null;

		switch(property)
		{
		case TIME_FORMAT:
			return new String[] { INTERNATIONAL, "sTimeFormat" };
		case TIME_SEPARATOR:
			return new String[] { INTERNATIONAL, "sTime" };
		case TIME_AM:
			return new String[] { INTERNATIONAL, "s1159" };
		case TIME_PM:
			return new String[] { INTERNATIONAL, "s2359" };
		default:
			return new String[] { "unknown", "unknown" };
		}
	}

	@Override
	protected TimePropertyEnum[] getValues()
	{
		return TimePropertyEnum.values();
	}
}
