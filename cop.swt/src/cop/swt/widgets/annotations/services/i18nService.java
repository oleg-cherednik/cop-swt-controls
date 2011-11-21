/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.annotations.services;

import static cop.common.extensions.AnnotationExtension.getAnnotatedMethods;
import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.ArrayExtension.isNotEmpty;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.ReflectionExtension.invokeMethod;
import static cop.common.extensions.StringExtension.isEmpty;

import java.lang.reflect.Method;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;

import cop.swt.widgets.annotations.i18n;
import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;

public class i18nService
{
	/**
	 * Closed constructor
	 */
	private i18nService()
	{}

	public static <T> String getTranslation(Class<T> cls, String key) throws AnnotationDeclarationException
	{
		return getTranslation(cls, key, null);
	}

	public static <T> String getTranslation(Class<T> cls, String key, Locale locale) throws AnnotationDeclarationException
	{
		if(isEmpty(key))
			throw new IllegalArgumentException("key is empty");

		Method[] methods;

		if(isNotNull(locale))
			methods = getAnnotatedMethods(cls, i18n.class, String.class, Locale.class);
		else
			methods = getAnnotatedMethods(cls, i18n.class, String.class);

		if(isEmpty(methods))
			return "";

		if(methods.length > 1)
			throw new AnnotationDeclarationException("There're to many methods annotatated with @i18n. Can't choose.");

		if(methods[0].getReturnType() != String.class)
			throw new AnnotationDeclarationException("Method annotatated with @i18n must return String or String[]");

		try
		{
			if(isNotNull(locale))
				return (String)invokeMethod(null, methods[0], key, locale);

			return (String)invokeMethod(null, methods[0], key);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return "";
	}

	// TODO write checks
	// for ENUM
	public static String[] getTranslations(Class<?> item, Locale locale) throws AnnotationDeclarationException
	{
		if(isNull(item))
			throw new NullPointerException("item == null");

		if(!item.isEnum())
			return new String[0];

		Object[] constants = item.getEnumConstants();

		if(isEmpty(constants))
			return new String[0];

		Method[] methods = getAnnotatedMethods(item, i18n.class, Locale.class);

		if(isEmpty(methods))
			return new String[0];

		if(methods.length > 1)
			throw new AnnotationDeclarationException("There're to many methods annotatated with @i18n. Can't choose.");

		// if(methods.length > 2)
		// throw new AnnotationDeclarationException(
		// "Annotation @i18n can be use maximum 2 time per enum. See annotation description.");

		try
		{
			return (String[])invokeMethod(null, methods[0], locale);
			// Method method = getRightMethod(methods[0], (methods.length == 2) ? methods[1] : null);

			// return null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return new String[0];
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

	private static String[] getTranslationsSingle(Method method, Object[] constants) throws Exception
	{
		Assert.isNotNull(method);
		Assert.isTrue(isNotEmpty(constants));

		String[] i18n = new String[constants.length];

		for(int i = 0, size = constants.length; i < size; i++)
			i18n[i] = "" + invokeMethod(constants[i], method);

		return i18n;
	}
}
