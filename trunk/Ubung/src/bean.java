import java.util.StringTokenizer;

public class bean
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String methodName = "getDate";

		System.out.println(getPropertyNameByMethodName(methodName));

	}

	private static String getPropertyNameByMethodName(String methodName)
	{
		methodName = methodName.trim();

		int offs = 0;

		if(methodName.startsWith("get") || methodName.startsWith("set"))
			offs = 3;
		else if(methodName.startsWith("is"))
			offs = 2;
		else
			return methodName;

		if(methodName.length() < offs + 1)
			return methodName;

		char letter = Character.toLowerCase(methodName.charAt(offs));

		return (methodName.length() < offs + 2) ? ("" + letter) : (letter + methodName.substring(offs + 1));
	}

}
