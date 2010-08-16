/**
 * $Id: LabelService.java 51 2010-08-16 12:25:56Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.swt/cop.swt/src/cop/swt/widgets/annotations/services/LabelService.java $
 */
package cop.swt.widgets.annotations.services;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.ReflectionExtension.getFieldValue;
import static cop.common.extensions.ReflectionExtension.getType;
import static cop.common.extensions.ReflectionExtension.invokeMethod;
import static cop.common.extensions.ReflectionExtension.isNumeric;
import static cop.common.extensions.ReflectionExtension.isString;
import static cop.common.extensions.StringExtension.isEmpty;
import static cop.common.extensions.StringExtension.isEqual;
import static cop.swt.widgets.viewers.list.descriptions.AbstractLabelDescription.createLabelDescription;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;

import cop.common.extensions.AnnotationExtension;
import cop.swt.widgets.annotations.Label;
import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;
import cop.swt.widgets.annotations.exceptions.WrongReturnValueException;
import cop.swt.widgets.viewers.list.descriptions.ILabelDescription;

/**
 * Class contains static methods that are used for working with <b>Label</b> annotation.<br>
 * 
 * @author cop (Cherednik, Oleg)
 * @see Label
 * @see ILabelName
 */
public final class LabelService
{
	/**
	 * Closed constructor
	 */
	private LabelService()
	{}

	public static <T> ILabelDescription<T> getDescription(Class<?> item, String labelName)
	                throws AnnotationDeclarationException
	{
		if(isNull(item))
			throw new NullPointerException("item == null");

		checkItemType(item);

		AccessibleObject obj = getAnnotatedItem(item, labelName);

		if(obj instanceof Field)
			return getAnnotationInfo((Field)obj, labelName);
		if(obj instanceof Method)
			return getAnnotationInfo((Method)obj, labelName, item);

		return null;
	}

	private static <T> ILabelDescription<T> getAnnotationInfo(Method method, String labelName, Class<?> cls)
	                throws AnnotationDeclarationException
	{
		Assert.isNotNull(method);
		Assert.isNotNull(cls);

		//		checkMethodParameterNumber(method.getParameterTypes());
		//		checkMethodReturnType(method.getReturnType());
		//		checkOrderValue(method.getAnnotation(Column.class));

		ILabelDescription<T> column = createLabelDescription(method);
		
		column.setLabelName(labelName);

		//		if(isEmpty(column.getName()))
		//			column.getContent().setName(getPropertyNameByMethodName(method.getName()));
		//
		//		checkSetterExists(method, cls);

		return column;
	}

	private static <T> ILabelDescription<T> getAnnotationInfo(Field field, String labelName)
	                throws AnnotationDeclarationException
	{
		Assert.isNotNull(field);

		//		checkOrderValue(field.getAnnotation(Column.class));
		//		checkFieldType(field.getType());

		ILabelDescription<T> column = createLabelDescription(field);
		
		column.setLabelName(labelName);

		//		if(isEmpty(column.getName()))
		//			column.getContent().setName(field.getName());

		return column;
	}

	/**
	 * Use this method to get representation string for specified <b>item</b> with default <b>labelName = ""</b>.<br>
	 * <br>
	 * If <code>item == null</code> then <u>NullPointerException</u> will be thrown.<br>
	 * If <b>item</b> is <u>String</u> or <u>Number</u> then <code>toString()</code> method will be used.<br>
	 * In other situation <b><font color=red>only one</font></b> <b>Label</b> annotation will be read and invoked. If
	 * there're no <b>Label</b> annotation or on any exception <code>toString()</code> method will be used.<br>
	 * If method, that is marked with annotation doesn't return value, then <b>AnnotationDeclarationException</b> will
	 * be thrown to outputs stream and <code>toString()</code> method will be used.
	 * 
	 * @param <T> can be any valid java class
	 * @param item this value will be used to get a name
	 * @return <ul>
	 *         <li><code>item.toString</code> if item is String or Number
	 *         <li>string representation o the item using <b>Label</b> annotation or standard <code>toString()</code>
	 *         method if annotation is not specified or if any error occurred.
	 *         </ul>
	 * @throws AnnotationDeclarationException if ther're any problems with using <b>Label</b> annotation
	 * @throws NullPointerException if <code>item == null</code>
	 * @see Label
	 * @see ILabelName
	 */
	public static <T> String getItemName(T item) throws WrongReturnValueException
	{
		return getItemName(item, null);
	}

	/**
	 * Use this method to get representation string for specified <b>item</b> with specified <b>labelName</b>.<br>
	 * <br>
	 * If <code>item == null</code> then <u>NullPointerException</u> will be thrown.<br>
	 * If <b>item</b> is <u>String</u> or <u>Number</u> then <code>toString()</code> method will be used.<br>
	 * In other situation <b><font color=red>only one</font></b> <b>Label</b> annotation with <b>labelName</b>, if it's
	 * not empty, will be read and invoked. If there're no <b>Label</b> annotation or on any exception
	 * <code>toString()</code> method will be used.<br>
	 * If method, that is marked with annotation doesn't return value or returns <code>null</code>, then
	 * <b>AnnotationDeclarationException</b> will be thrown to outputs stream and <code>toString()</code> method will be
	 * used.<br>
	 * If method <code>toString()</code> doesn't return not empty string, then WrongReturnValueException will be thrown.
	 * 
	 * @param <T> can be any valid java class
	 * @param item this value will be used to get a name
	 * @param labelName used to select annotation only with specified <b>labelName</b>. It can be null orr empty as well
	 *            (by default).
	 * @return <ul>
	 *         <li><code>item.toString</code> if item is String or Number
	 *         <li>string representation o the item using <b>Label</b> annotation or standard <code>toString()</code>
	 *         method if annotation is not specified or if any error occurred.
	 *         </ul>
	 * @throws NullPointerException if <code>item == null</code>
	 * @throws WrongReturnValueException if can detect valid string representation for giving item
	 * @see Label
	 * @see ILabelName
	 */
	public static <T> String getItemName(T item, String labelName) throws WrongReturnValueException
	{
		return getItemName(item, labelName, null);
	}

	public static <T> String getItemName(T item, String labelName, Locale locale) throws WrongReturnValueException
	{
		if(isNull(item))
			throw new NullPointerException("item == null");

		try
		{
			Class<?> cls = item.getClass();

			if(isString(cls) || isNumeric(cls))
				return getItemDefaultName(item);

			AccessibleObject obj = getAnnotatedItem(item.getClass(), labelName);

			return isNotNull(obj) ? getResultString(item, obj, locale) : getItemDefaultName(item);
		}
		catch(AnnotationDeclarationException e)
		{
			e.printStackTrace();
		}

		return getItemDefaultName(item);
	}

	public static <T> Class<?> getItemClass(T item, String labelName) throws WrongReturnValueException
	{
		if(isNull(item))
			throw new NullPointerException("item == null");

		try
		{
			Class<?> cls = item.getClass();

			if(isString(cls) || isNumeric(cls))
				return cls;

			return getType(getAnnotatedItem(item.getClass(), labelName), String.class);
		}
		catch(AnnotationDeclarationException e)
		{
			e.printStackTrace();
		}

		return String.class;
	}

	//	public static <T> Class<?> getItem(T item, String labelName) throws WrongReturnValueException
	//	{
	//		if(isNull(item))
	//			throw new NullPointerException("item == null");
	//
	//		try
	//		{
	//			Class<?> cls = item.getClass();
	//
	//			if(isString(cls) || isNumeric(cls))
	//				return cls;
	//
	//			return getType(findAnnotatedItem(item.getClass(), labelName), String.class);
	//		}
	//		catch(AnnotationDeclarationException e)
	//		{
	//			e.printStackTrace();
	//		}
	//
	//		return String.class;
	//	}

	public static AccessibleObject getAnnotatedItem(Class<?> cls, String labelName)
	                throws AnnotationDeclarationException
	{
		if(isNull(cls))
			return null;

		checkItemType(cls);

		Method[] methods = getAnnotatedMethods(cls, labelName);
		Field[] fields = getAnnotatedFields(cls, labelName);

		int methodsNum = methods.length;
		int fieldsNum = fields.length;
		int total = methodsNum + fieldsNum;

		if(total == 0)
			return null;

		if(total > 1)
			throw new AnnotationDeclarationException("Annotation '@Label' must be declared only once");

		return (methodsNum == 1) ? methods[0] : fields[0];
	}

	/**
	 * Invokes <code>toString()</code> method for item. If returnd value is <code>null</code> or <u>empty string</u>,
	 * then exception will be thrown.
	 * 
	 * @param <T>
	 * @param item not null item
	 * @return not null item representation string
	 * @throws WrongReturnValueException
	 * @see {@link Object#toString()}
	 */
	private static <T> String getItemDefaultName(T item) throws WrongReturnValueException
	{
		Assert.isNotNull(item);

		String name = item.toString();

		if(isEmpty(name))
			throw new WrongReturnValueException("Please, check toString() method for 'item' class."
			                + " It must return not empty string!");

		return name;
	}

	/**
	 * Returns all methods for selected <b>class</b> that are marked with <b>Label</b> annotation with specific
	 * <b>labelName</b>.<br>
	 * 
	 * @param cls class to search
	 * @param labelName <b>Label</b> annotation name
	 * @return all methods with <b>Label</b> annotation
	 * @see Label
	 * @see Method
	 */
	private static Method[] getAnnotatedMethods(Class<?> cls, String labelName)
	{
		Assert.isNotNull(cls);

		List<Method> methods = new ArrayList<Method>();

		for(Method method : AnnotationExtension.getAnnotatedMethods(cls, Label.class))
			if(isSameLabelName(labelName, method.getAnnotation(Label.class)))
				methods.add(method);

		return methods.toArray(new Method[0]);
	}

	private static boolean isSameLabelName(String labelName, Label annotation)
	{
		Assert.isNotNull(annotation);

		return isEqual(labelName, annotation.name());
	}

	/**
	 * Returns all fields for selected <b>class</b> that are marked with <b>Label</b> annotation with specific
	 * <b>labelName</b>.<br>
	 * 
	 * @param cls class to search
	 * @param labelName <b>Label</b> annotation name
	 * @return all fields with <b>Label</b> annotation
	 * @see Label
	 * @see Field
	 */
	private static Field[] getAnnotatedFields(Class<?> cls, String labelName)
	{
		Assert.isNotNull(cls);

		List<Field> fields = new ArrayList<Field>();

		for(Field field : AnnotationExtension.getAnnotatedFields(cls, Label.class))
			if(isSameLabelName(labelName, field.getAnnotation(Label.class)))
				fields.add(field);

		return fields.toArray(new Field[0]);
	}

	public static <T> Object getResultValue(T item, AccessibleObject obj, Locale locale) throws Exception
	{
		Assert.isNotNull(obj);

		if(isNull(item))
			return null;

		if(obj instanceof Field)
			return getResultValue(item, (Field)obj);

		return getResultValue(item, (Method)obj, locale);
	}

	private static <T> String getResultString(T item, AccessibleObject obj, Locale locale)
	                throws AnnotationDeclarationException
	{
		Assert.isNotNull(item);
		Assert.isNotNull(obj);

		try
		{
			Object res = getResultValue(item, obj, locale);

			if(isNull(res))
				throw new AnnotationDeclarationException("Field/metod annotatated with '@Label' returns null");

			String str = res.toString();

			if(isEmpty(res.toString()))
				throw new AnnotationDeclarationException("Field/method annotatated with '@Label' must return not"
				                + " empty string");

			return str;
		}
		catch(Exception e)
		{
			throw new AnnotationDeclarationException(e);
		}
	}

	private static <T> Object getResultValue(T item, Method method, Locale locale) throws Exception
	{
		Assert.isNotNull(item);
		Assert.isNotNull(method);
		//		Assert.isLegal(!(item instanceof String), "item must not be String");
		//		Assert.isLegal(!(item instanceof Number), "item must not be Number");

		checkItemType(item.getClass());
		checkMethodArguments(method);
		checkMethodReturnType(method);

		Object res = null;

		if(isNotNull(locale))
			res = invokeMethod(item, method, locale);
		else if(method.getParameterTypes().length == 1)
			res = invokeMethod(item, method, (Object)null);
		else
			res = invokeMethod(item, method);

		if(isNull(res))
			throw new AnnotationDeclarationException("Method annotatated with '@Label' must return not null value");

		return res;
	}

	private static void checkMethodArguments(Method method) throws AnnotationDeclarationException
	{
		Assert.isNotNull(method);

		Class<?>[] arguments = method.getParameterTypes();

		if(arguments.length > 1 || arguments.length == 1 && !arguments[0].isAssignableFrom(Locale.class))
			throw new AnnotationDeclarationException(
			                "Method annotatated with '@Label' can optionally have only 'Locale' argument");
	}

	private static void checkMethodReturnType(Method method) throws AnnotationDeclarationException
	{
		Assert.isNotNull(method);

		if(method.getReturnType() == void.class)
			throw new AnnotationDeclarationException("Method annotatated with '@Label' must return value");

	}

	private static <T> Object getResultValue(T item, Field field) throws Exception
	{
		Assert.isNotNull(item);
		Assert.isNotNull(field);

		checkItemType(item.getClass());

		Object res = getFieldValue(item, field);

		Assert.isNotNull(res);

		return res;
	}

	private static void checkItemType(Class<?> type)
	{
		Assert.isNotNull(type);

		if(isString(type))
			throw new IllegalArgumentException("item can't be String type");
		if(isNumeric(type))
			throw new IllegalArgumentException("item must not be Number type");
		if(type.isPrimitive())
			throw new IllegalArgumentException("item can't be primitive type");
	}
}
