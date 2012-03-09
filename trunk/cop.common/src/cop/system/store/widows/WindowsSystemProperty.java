package cop.system.store.widows;

import static cop.extensions.StringExt.isEmpty;

import java.util.HashMap;
import java.util.Map;

import cop.system.store.interfaces.SystemPropertyPath;
import cop.system.store.interfaces.SystemPropertyTemplate;

abstract class WindowsSystemProperty<T extends Enum<T>> extends WindowsRegistry implements SystemPropertyPath<T>,
                SystemPropertyTemplate<T>
{
	@Override
	public Map<T, String> getSystemProperties()
	{
		Map<T, String> res = new HashMap<T, String>();

		for(T name : getValues())
		{
			String value = getSystemProperty(name);

			if(!isEmpty(value))
				res.put(name, value);
		}

		return res;
	}

	@Override
	public String getSystemProperty(T name)
	{
		return getSystemProperty(name, null);
	}

	@Override
	public String getSystemProperty(T name, String defaultValue)
	{
		String[] pathName = getPropertyPath(name);
		String value = getProperty(pathName[0], pathName[1]);

		return doAfter(name, value, defaultValue);
	}

	protected abstract T[] getValues();

	/**
	 * @param name 
	 * @param defaultValue  
	 */
	protected String doAfter(T name, String value, String defaultValue)
	{
		return value;
	}
}
