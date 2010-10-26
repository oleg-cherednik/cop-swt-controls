import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * this example executes dynamically a method on a class, the class is dynamically choosen via a string, and so is the
 * method
 * 
 * @author Giulio
 */
public class Reflection
{

	private static final String NAME = "Giulio"; //$NON-NLS-1$

	private static final String METHOD = "method"; //$NON-NLS-1$

	private static final String CLASS = "DynamicallyPointedClass"; //$NON-NLS-1$

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		Class<?> class1;
		try
		{
			class1 = Class.forName(CLASS);
			Method method = class1.getMethod(METHOD, String.class);
			Object o = method.invoke(null, NAME);
			System.out.println(o);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch(InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch(SecurityException e)
		{
			e.printStackTrace();
		}
		catch(NoSuchMethodException e)
		{
			e.printStackTrace();
		}

	}

}
