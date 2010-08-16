package cop.system.store.widows;

enum ParamTypeEnum
{
	SINGLE("/v"), ALL("/s"), DEFAULT("/va");

	private final String value;

	private ParamTypeEnum(String value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return value;
	}
}
