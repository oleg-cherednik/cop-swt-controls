package cop.system.store.widows;

import static cop.extensions.StringExt.isEmpty;
import static java.util.regex.Pattern.compile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cop.system.store.interfaces.SystemProperty;

class WindowsRegistry implements SystemProperty, PropertyPath
{
	static class StreamReader extends Thread
	{
		private final InputStream is;
		private String str;

		StreamReader(InputStream is)
		{
			this.is = is;
		}

		@Override
		public void run()
		{
			try
			{
				int c = 0;
				List<Byte> list = new LinkedList<Byte>();

				while((c = is.read()) != -1)
				{
					list.add((byte)c);
				}

				str = new String(toArray(list));
			}
			catch(IOException e)
			{}
		}

		private static byte[] toArray(List<Byte> list)
		{
			if(list == null)
				return null;

			if(list.isEmpty())
				return new byte[0];

			byte[] arr = new byte[list.size()];
			int i = 0;

			for(Byte val : list)
				arr[i++] = val.byteValue();

			return arr;
		}

		String getResult()
		{
			return str;
		}
	}

	@Override
	public String getProperty(String path, String name)
	{
		if(isEmpty(path) || isEmpty(name))
			return null;

		try
		{
			final String query = REGQUERY_UTIL + " \"" + path + "\" " + ParamTypeEnum.SINGLE + " " + name;

			return getPropertyFromResult(exec(query));
		}
		catch(Exception e)
		{}

		return null;
	}

	@Override
	public Properties getProperties(String path)
	{
		if(isEmpty(path))
			return null;

		try
		{
			final String query = REGQUERY_UTIL + " \"" + path + "\" " + ParamTypeEnum.ALL;
			return getPropertiesFromResult(exec(query));
		}
		catch(Exception e)
		{}

		return null;
	}

	private static String exec(String query) throws IOException, InterruptedException
	{
		Process process = Runtime.getRuntime().exec(query);
		StreamReader reader = new StreamReader(process.getInputStream());

		reader.start();
		process.waitFor();
		reader.join();

		return reader.getResult();
	}

	private static Properties getPropertiesFromResult(String result)
	{
		if(result == null || result.isEmpty())
			return null;

		Properties properties = new Properties();
		Matcher m = compile(REGEX_TOKEN).matcher(selectMainVolume(result));

		while(m.find())
		{
			String record = m.group();
			String[] parts = record.split("\\t");

			if(parts.length < 3)
				properties.put(parts[0].trim(), "");
			else
				properties.put(parts[0].trim(), parts[2].trim());
		}

		return properties;
	}

	private static String getPropertyFromResult(String result)
	{
		if(isEmpty(result))
			return null;

		Matcher m = compile(REGEX_TOKEN).matcher(result);

		if(m.find())
		{
			String[] parts = m.group().split("\\t");

			return (parts.length < 3) ? "" : parts[2].trim();
		}

		return null;
	}

	private static String selectMainVolume(String result)
	{
		if(isEmpty(result))
			return result;

		Matcher m = Pattern.compile(REGEX_VOLUME).matcher(result);
		List<Integer> pos = new ArrayList<Integer>();

		while(m.find())
		{
			pos.add(m.start());
		}

		if(pos.size() < 2)
			return result;

		return result.substring(pos.get(0), pos.get(1));
	}
}
