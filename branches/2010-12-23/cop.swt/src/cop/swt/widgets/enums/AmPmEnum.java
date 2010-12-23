package cop.swt.widgets.enums;

public enum AmPmEnum
{
	AM,
	PM;

	public static AmPmEnum parseAmPmEnum(int val)
	{
		for(AmPmEnum key : AmPmEnum.values())
			if(key.ordinal() == val)
				return key;

		return null;
	}
}
