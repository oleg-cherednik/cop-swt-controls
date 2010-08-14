package cop.swt.widgets.enums;

public enum AmPmEnum
{
	AM(0),
	PM(1);

	private int val;

	private AmPmEnum(int val)
	{
		this.val = val;
	}

	public int getVal()
	{
		return val;
	}

	public static AmPmEnum parseAmPmEnum(int val)
	{
		for(AmPmEnum key : AmPmEnum.values())
			if(key.val == val)
				return key;

		return null;
	}
}
