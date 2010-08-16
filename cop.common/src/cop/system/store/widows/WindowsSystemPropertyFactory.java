package cop.system.store.widows;

import cop.system.store.enums.DatePropertyEnum;
import cop.system.store.enums.TimePropertyEnum;
import cop.system.store.interfaces.SystemPropertyFactory;
import cop.system.store.interfaces.SystemPropertyTemplate;

public final class WindowsSystemPropertyFactory implements SystemPropertyFactory
{
	@Override
	public SystemPropertyTemplate<TimePropertyEnum> getTimeSystemPropertyTemplate()
	{
		return new TimeSystemProperty();
	}

	@Override
	public SystemPropertyTemplate<DatePropertyEnum> getDateSystemPropertyTemplate()
	{
		return new DateSystemProperty();
	}
}
