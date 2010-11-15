/**
 * this is the dummy class containing a method it serves as example for the reflection the class implements the method
 * <b>method</b> that will be called by the reflection procedures
 * 
 * @author Giulio
 */
public class DynamicallyPointedClass
{
	private static final String HELLO = "Hello "; //$NON-NLS-1$

	/**
	 * simply gets a String and returns another String
	 * 
	 * @param name
	 * @return a String
	 */
	public static String method(String name)
	{
		return HELLO + name;
	}
}
