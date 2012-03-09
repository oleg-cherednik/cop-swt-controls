/**
 * @licence GNU Leser General Public License
 *
 * $Id: AnnotationExtension.java 144 2010-10-17 19:16:51Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.common/src/cop/common/extensions/AnnotationExtension.java $
 */
package cop.extensions;

import static cop.extensions.ArrayExt.isEmpty;
import static cop.extensions.CollectionExt.isArraysEqual;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class AnnotationExt {
	private AnnotationExt() {}

	private static Method[] getMethods(Class<?> cls) {
		if (cls == null)
			return new Method[0];

		Set<Method> res = new HashSet<Method>();

		for (Method[] methods : new Method[][] { cls.getDeclaredMethods(), cls.getMethods() })
			for (Method method : methods)
				res.add(method);

		return res.toArray(new Method[0]);
	}

	public static Method[] getAnnotatedMethods(Class<?> cls) {
		return getAnnotatedMethods(cls, null, (Class<?>)null);
	}

	public static <T extends Annotation> Method[] getAnnotatedMethods(Class<?> cls, Class<T> annotationClass) {
		return getAnnotatedMethods(cls, annotationClass, (Class<?>)null);
	}

	public static <T extends Annotation> Method[] getAnnotatedMethods(Class<?> cls, Class<T> annotationClass,
	                Class<?>... parameterTypes) {
		if (cls == null)
			return null;

		List<Method> methods = new ArrayList<Method>();

		for (Method method : getMethods(cls))
			if (isAnnotated(method, annotationClass) && isSameParameters(method, parameterTypes))
				methods.add(method);

		return methods.toArray(new Method[0]);
	}

	public static Field[] getAnnotatedFields(Class<?> cls) {
		return getAnnotatedFields(cls, null);
	}

	public static <T extends Annotation> Field[] getAnnotatedFields(Class<?> cls, Class<T> annotationClass) {
		if (cls == null)
			return new Field[0];

		List<Field> fields = new ArrayList<Field>();

		for (Field field : cls.getDeclaredFields())
			if (isAnnotated(field, annotationClass))
				fields.add(field);

		return fields.toArray(new Field[0]);
	}

	private static <T extends Annotation> boolean isAnnotated(AccessibleObject obj, Class<T> annotationClass) {
		if (obj == null)
			return false;
		if (annotationClass != null)
			return obj.getAnnotation(annotationClass) != null;
		return isEmpty(obj.getAnnotations());
	}

	private static boolean isSameParameters(Method method, Class<?>... parameterTypes) {
		if (method == null)
			return false;
		if (isEmpty(parameterTypes))
			return true;
		return isArraysEqual(method.getParameterTypes(), parameterTypes);
	}
}
