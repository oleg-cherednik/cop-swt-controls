package cop.system.store;

enum OSEnum
{
	WINDOWS(".*Windows.*"), LINUX(".*Linux.*");

	private String pattern;

	private OSEnum(String pattern)
	{
		this.pattern = pattern;
	}

	public String getPattern()
	{
		return pattern;
	}

	public static OSEnum parseOSEnum(String osName) throws Exception
	{
		for(OSEnum os : values())
		{
			if(os.getPattern().equals(osName))
				return os;
		}

		throw new Exception("Can't convert '" + osName + "' to OSEnum");
	}
}
