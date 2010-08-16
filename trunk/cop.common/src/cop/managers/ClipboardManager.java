package cop.managers;

import static java.lang.Math.max;
import static java.lang.String.format;

import java.util.List;

public final class ClipboardManager
{
	public static String buildOneStringData(List<String[]> data)
	{
		int[] widths = calculateWidths(data);
		String format = createFormat(widths);
		StringBuilder buf = new StringBuilder();

		for(int i = 0, size = data.size(); i < size; i++)
			buf.append(format(format + ((i != size - 1) ? "%n" : ""), (Object[])data.get(i)));

		return "" + buf;
	}

	public static String createFormat(int[] widths)
	{
		StringBuilder buf = new StringBuilder();
		boolean flag = false;

		for(int width : widths)
		{
			buf.append("%" + (width + (flag ? 2 : 0)) + "s");
			flag = true;
		}

		return "" + buf;
	}

	public static int[] calculateWidths(List<String[]> rows)
	{
		int[] widths = new int[rows.get(0).length];

		for(String[] row : rows)
		{
			int col = 0;

			for(String str : row)
			{
				widths[col] = max(widths[col], str.length());
				col++;
			}
		}

		return widths;
	}
}
