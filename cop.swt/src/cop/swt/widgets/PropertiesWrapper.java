/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

import java.util.Properties;

import cop.extensions.StringExt;

public class PropertiesWrapper extends Properties
{
	private static final long serialVersionUID = -2022506773991003936L;

	public PropertiesWrapper()
	{}

	public PropertiesWrapper(Properties properties)
	{
		if(properties != null && !properties.isEmpty())
			this.putAll(properties);
	}

	public void replaceExistingProperty(String key, String value)
	{
		if(StringExt.isEmpty(key))
			return;

		if(isExists(key))
			setProperty(key, value);
	}

	public void setOnlyNewProperty(String key, String value)
	{
		if(StringExt.isEmpty(key))
			return;

		if(!isExists(key))
			setProperty(key, value);
	}

	public boolean isExists(String key)
	{
		if(StringExt.isEmpty(key))
			return false;

		String notExists = "not_exists";

		return !notExists.equals(super.getProperty(key, notExists));
	}

	public Integer getIntegerProperty(String key) throws NumberFormatException
	{
		return getIntegerProperty(key, 0);
	}

	public Integer getIntegerProperty(String key, Integer defaultValue) throws NumberFormatException
	{
		return parseInt(super.getProperty(key, "" + defaultValue));
	}

	public Boolean getBooleanProperty(String key)
	{
		return getBooleanProperty(key, false);
	}

	public Boolean getBooleanProperty(String key, Boolean defaultValue)
	{
		return parseBoolean(super.getProperty(key, "" + defaultValue));
	}
}
