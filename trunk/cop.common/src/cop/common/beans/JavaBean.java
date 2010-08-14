/**
 * @licence GNU Leser General Public License
 * @author Cherednik, Oleg <sa-pm17@mail.ru>
 *
 * $Id$
 * $HeadURL$
 */
package cop.common.beans;

import static java.lang.Character.toLowerCase;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class JavaBean
{
	private JavaBean()
	{}

	public static String getPropertyName(AccessibleObject obj)
	{
		if(obj instanceof Field)
			return ((Field)obj).getName();

		if(obj instanceof Method)
			return getPropertyNameByMethodName(((Method)obj).getName());

		throw new IllegalArgumentException("AccessibleObject is not Field or Method");
	}

	public static String getSetterMethodName(AccessibleObject obj)
	{
		if(obj instanceof Field)
			return getSetterMethodNameByGetterMethodName(((Field)obj).getName());

		if(obj instanceof Method)
			return getSetterMethodNameByGetterMethodName(((Method)obj).getName());

		throw new IllegalArgumentException("AccessibleObject is not Field or Method");
	}

	public static Method getSetterMethod(AccessibleObject obj, Class<?> cls)
	{
		String methodName;
		Class<?> type;

		if(obj instanceof Field)
		{
			methodName = getSetterMethodNameByGetterMethodName(((Field)obj).getName());
			type = ((Field)obj).getType();
		}
		else if(obj instanceof Method)
		{
			methodName = getSetterMethodNameByGetterMethodName(((Method)obj).getName());
			type = ((Method)obj).getReturnType();
		}
		else
			throw new IllegalArgumentException("AccessibleObject is not Field or Method");

		try
		{
			return cls.getDeclaredMethod(methodName, type);
		}
		catch(NoSuchMethodException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static String getPropertyNameByMethodName(String methodName)
	{
		int offs = 0;

		if(methodName.startsWith("get") || methodName.startsWith("set"))
			offs = 3;
		else if(methodName.startsWith("is"))
			offs = 2;
		else
			throw new IllegalArgumentException("'" + methodName + "' is not a JavaBean method");

		if(methodName.length() < offs + 1)
			throw new IllegalArgumentException("'" + methodName + "' is not a JavaBean method");

		char letter = toLowerCase(methodName.charAt(offs));

		if(methodName.length() < offs + 2)
			return "" + letter;

		return letter + methodName.substring(offs + 1);
	}

	public static String getSetterMethodNameByGetterMethodName(String getMethodName)
	{
		int offs = 0;

		if(getMethodName.startsWith("get"))
			offs = 3;
		else if(getMethodName.startsWith("is"))
			offs = 2;
		else
			throw new IllegalArgumentException("'" + getMethodName + "' is not a JavaBean get method");

		if(getMethodName.length() < offs + 1)
			throw new IllegalArgumentException("'" + getMethodName + "' is not a JavaBean get method");

		return "set" + getMethodName.substring(offs);
	}

	public static String getSetterMethodNameByPropertyName(String propertyName)
	{
		char letter = toLowerCase(propertyName.charAt(0));

		if(propertyName.length() < 2)
			return "" + letter;

		return "" + letter + propertyName.substring(1);
	}
}
