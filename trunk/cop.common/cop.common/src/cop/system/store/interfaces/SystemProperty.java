package cop.system.store.interfaces;

import java.util.Properties;

public interface SystemProperty
{
	Properties getProperties(String path);

	String getProperty(String path, String name);
}
