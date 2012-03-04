/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.i18.annotations;

import java.lang.reflect.Method;
import java.util.Locale;

import cop.common.annotations.exceptions.AnnotationDeclarationException;

/**
 * @author Oleg Cherednik
 * @since 16.08.2010
 */
public class i18nService {
	private static final String[] NO_STRING = new String[0];

	private i18nService() {}

	public static <T> String getTranslation(Class<T> cls, String key) throws AnnotationDeclarationException {
		return getTranslation(cls, key, null);
	}

	public static <T> String getTranslation(Class<T> cls, String key, Locale locale)
	                throws AnnotationDeclarationException {
		if (isEmpty(key))
			throw new IllegalArgumentException("key is empty");

		Class<?> parameterType = (locale != null) ? Locale.class : null;
		Method[] methods = AnnotationExt.getAnnotatedMethods(cls, i18n.class, String.class, parameterType);

		if (methods.length == 0)
			return "";

		if (methods.length > 1)
			throw new AnnotationDeclarationException("There're to many methods annotatated with @i18n. Can't choose.");

		if (methods[0].getReturnType() != String.class)
			throw new AnnotationDeclarationException("Method annotatated with @i18n must return String or String[]");

		try {
			return (String)invokeMethod(null, methods[0], key, locale);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	// TODO write checks
	// for ENUM
	public static String[] getTranslations(Class<?> item, Locale locale) throws AnnotationDeclarationException {
		if (item == null)
			throw new NullPointerException("item == null");

		if (!item.isEnum())
			return NO_STRING;

		Object[] constants = item.getEnumConstants();

		if (isEmpty(constants))
			return NO_STRING;

		Method[] methods = AnnotationExt.getAnnotatedMethods(item, i18n.class, Locale.class);

		if (isEmpty(methods))
			return NO_STRING;

		if (methods.length > 1)
			throw new AnnotationDeclarationException("There're to many methods annotatated with @i18n. Can't choose.");

		// if(methods.length > 2)
		// throw new AnnotationDeclarationException(
		// "Annotation @i18n can be use maximum 2 time per enum. See annotation description.");

		try {
			return (String[])invokeMethod(null, methods[0], locale);
			// Method method = getRightMethod(methods[0], (methods.length == 2) ? methods[1] : null);

			// return null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return NO_STRING;
	}

	// private static Method getRightMethod(Method method1, Method method2) throws AnnotationDeclarationException
	// {
	// Assert.isNotNull(method1);
	//
	// Class<?> returnType1 = method1.getReturnType();
	// Class<?> returnType2 = isNotNull(method2) ? method2.getReturnType() : null;
	//
	// for(Class<?> type : new Class<?>[] { returnType1, returnType2 })
	// if(isNotNull(type) && !type.isAssignableFrom(String.class) && !type.isAssignableFrom(String[].class))
	// throw new AnnotationDeclarationException("Method annotatated with @i18n must return String or String[]");
	//
	// if(returnType1.equals(returnType2))
	// throw new AnnotationDeclarationException("Methods annotatated with @i18n must return String or String[]");
	//
	// if(isNull(returnType2))
	// return method1;
	//
	// return method1.getReturnType().isAssignableFrom(String[].class) ? method1 : method2;
	// }

	// private static String[] getTranslations(Method method, Object[] constants) throws Exception
	// {
	// Assert.isNotNull(method);
	// Assert.isTrue(isNotEmpty(constants));
	//
	// Class<?> returnType = method.getReturnType();
	//
	// if(returnType.isAssignableFrom(String[].class))
	// return (String[])invokeMethod(null, method);
	//
	// if(returnType.isAssignableFrom(String.class))
	// {
	// String[] i18n = getTranslationsSingle(method, constants);
	//
	// if(isEmpty(i18n) || i18n.length != constants.length)
	// throw new AnnotationDeclarationException("Method with @i18n annotation and return type "
	// + "String[] must return exactly the same number of elements"
	// + "as enumeration constant number");
	//
	// return i18n;
	// }
	//
	// Assert.isTrue(false, "Method annotatated with @i18n must return String or String[]");
	//
	// return new String[0];
	// }

	// private static String[] getTranslationsSingle(Method method, Object[] constants) throws Exception
	// {
	// assert method != null;
	// assert !isEmpty(constants);
	//
	// String[] i18n = new String[constants.length];
	//
	// for(int i = 0, size = constants.length; i < size; i++)
	// i18n[i] = invokeMethod(constants[i], method).toString();
	//
	// return i18n;
	// }
	
	/*
	 * static
	 */
	
	private static boolean isEmpty(String str)
	{
		return str == null || str.trim().isEmpty();
	}
	
	private static <T> boolean isEmpty(T[] arr) {
		return arr == null || arr.length == 0;
	}
	
//	private static <T> Object invokeMethod(T item, Method method) throws Exception
//	{
//		if(method == null)
//			return null;
//
//		method.setAccessible(true);
//
//		return method.invoke(item);
//	}

	private static <T> Object invokeMethod(T item, Method method, Object... args) throws Exception {
		if (method == null)
			return null;

		method.setAccessible(true);

		return method.invoke(item, args);
	}
}
