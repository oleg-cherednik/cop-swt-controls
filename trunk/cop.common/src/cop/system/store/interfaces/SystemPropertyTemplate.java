package cop.system.store.interfaces;

import java.util.Map;

public interface SystemPropertyTemplate<T extends Enum<T>>
{
	Map<T, String> getSystemProperties();

	String getSystemProperty(T name);

	String getSystemProperty(T name, String defaultvalue);
}
