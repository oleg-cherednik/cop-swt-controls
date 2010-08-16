/**
 * @licence GNU Leser General Public License
 *
 * $Id: AnnotationExtension.java 47 2010-08-16 12:19:28Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.common/cop.common/src/cop/common/extensions/AnnotationExtension.java $
 */
package cop.common.extensions;

import static cop.common.extensions.CollectionExtension.isArraysEqual;
import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.common.extensions.CollectionExtension.isNotEmpty;
import static cop.common.extensions.CollectionExtension.toCollection;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class AnnotationExtension
{
	private AnnotationExtension()
	{}

	private static Method[] getMethods(Class<?> cls)
	{
		if(isNull(cls))
			return new Method[0];

		Set<Method> res = new HashSet<Method>();

		for(Method[] methods : new Method[][] { cls.getDeclaredMethods(), cls.getMethods() })
			res.addAll(toCollection(methods, HashSet.class));

		return res.toArray(new Method[0]);
	}

	public static Method[] getAnnotatedMethods(Class<?> cls)
	{
		return getAnnotatedMethods(cls, null, (Class<?>)null);
	}

	public static <T extends Annotation> Method[] getAnnotatedMethods(Class<?> cls, Class<T> annotationClass)
	{
		return getAnnotatedMethods(cls, annotationClass, (Class<?>)null);
	}

	public static <T extends Annotation> Method[] getAnnotatedMethods(Class<?> cls, Class<T> annotationClass,
	                Class<?>... parameterTypes)
	{
		if(isNull(cls))
			return null;

		List<Method> methods = new ArrayList<Method>();

		for(Method method : getMethods(cls))
			if(isAnnotated(method, annotationClass) && isSameParameters(method, parameterTypes))
				methods.add(method);

		return methods.toArray(new Method[0]);
	}

	public static Field[] getAnnotatedFields(Class<?> cls)
	{
		return getAnnotatedFields(cls, null);
	}

	public static <T extends Annotation> Field[] getAnnotatedFields(Class<?> cls, Class<T> annotationClass)
	{
		if(isNull(cls))
			return new Field[0];

		List<Field> fields = new ArrayList<Field>();

		for(Field field : cls.getDeclaredFields())
			if(isAnnotated(field, annotationClass))
				fields.add(field);

		return fields.toArray(new Field[0]);
	}

	private static <T extends Annotation> boolean isAnnotated(AccessibleObject obj, Class<T> annotationClass)
	{
		if(isNull(obj))
			return false;

		if(isNotNull(annotationClass))
			return isNotNull(obj.getAnnotation(annotationClass));

		return isEmpty(obj.getAnnotations());
	}

	private static boolean isSameParameters(Method method, Class<?>... parameterTypes)
	{
		if(isNull(method))
			return false;

		return isNotEmpty(parameterTypes) ? isArraysEqual(method.getParameterTypes(), parameterTypes) : true;
	}
}
