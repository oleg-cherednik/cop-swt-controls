/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.i18.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class AnnotationExt {
	private static final Method[] NO_METHODS = new Method[0];

	private AnnotationExt() {}

	public static <T extends Annotation> Method[] getAnnotatedMethods(Class<?> cls, Class<T> annotationClass) {
		return getAnnotatedMethods(cls, annotationClass, (Class<?>)null);
	}

	public static <T extends Annotation> Method[] getAnnotatedMethods(Class<?> cls, Class<T> annotationClass,
	                Class<?>... parameterTypes) {
		if (cls == null)
			return NO_METHODS;

		List<Method> methods = new ArrayList<Method>();

		for (Method method : getMethods(cls))
			if (isAnnotated(method, annotationClass) && isSameParameters(method, parameterTypes))
				methods.add(method);

		return methods.isEmpty() ? NO_METHODS : methods.toArray(new Method[methods.size()]);
	}

	private static Method[] getMethods(Class<?> cls) {
		if (cls == null)
			return NO_METHODS;

		Set<Method> methods = new HashSet<Method>();

		for (Method method : cls.getDeclaredMethods())
			methods.add(method);

		for (Method method : cls.getMethods())
			methods.add(method);

		return methods.toArray(new Method[methods.size()]);
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

		return Arrays.equals(method.getParameterTypes(), parameterTypes);
	}

	private static <T> boolean isEmpty(T[] arr) {
		return arr == null || arr.length == 0;
	}
}
