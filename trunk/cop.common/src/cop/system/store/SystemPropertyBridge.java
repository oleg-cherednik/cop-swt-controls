package cop.system.store;

import static cop.system.store.OSEnum.WINDOWS;
import static cop.system.store.OSEnum.parseOSEnum;
import cop.system.store.interfaces.SystemPropertyFactory;
import cop.system.store.widows.WindowsSystemPropertyFactory;

public final class SystemPropertyBridge
{
	private SystemPropertyBridge()
	{}

	private static OSEnum getOSName()
	{
		try
		{
			return parseOSEnum(System.getProperty("os.name", "Windows"));
		}
		catch(Exception e)
		{
			return WINDOWS;
		}
	}

	public static SystemPropertyFactory getSystemPropertyFactory()
	{
		switch(getOSName())
		{
		case WINDOWS:
			return new WindowsSystemPropertyFactory();
		default:
			return null;
		}
	}
}
