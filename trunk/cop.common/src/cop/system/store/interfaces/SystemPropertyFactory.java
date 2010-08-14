package cop.system.store.interfaces;

import cop.system.store.enums.DatePropertyEnum;
import cop.system.store.enums.TimePropertyEnum;

public interface SystemPropertyFactory
{
	SystemPropertyTemplate<TimePropertyEnum> getTimeSystemPropertyTemplate();

	SystemPropertyTemplate<DatePropertyEnum> getDateSystemPropertyTemplate();
}
