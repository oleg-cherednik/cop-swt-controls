/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.annotations.services;

import static cop.common.beans.JavaBean.getPropertyNameByMethodName;
import static cop.common.beans.JavaBean.getSetterMethodNameByGetterMethodName;
import static cop.common.extensions.AnnotationExtension.getAnnotatedFields;
import static cop.common.extensions.AnnotationExtension.getAnnotatedMethods;
import static cop.common.extensions.ArrayExtension.isNotEmpty;
import static cop.common.extensions.CollectionExtension.removeDuplicatesAndSort;
import static cop.common.extensions.StringExtension.isEmpty;
import static org.eclipse.swt.SWT.LEFT;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.eclipse.core.runtime.Assert;

import cop.swt.widgets.annotations.Column;
import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;
import cop.swt.widgets.viewers.table.columns.ColumnContext;
import cop.swt.widgets.viewers.table.columns.PTableColumnProviderImpl;
import cop.swt.widgets.viewers.table.columns.settings.ColumnSettings;
import cop.swt.widgets.viewers.table.interfaces.PTableColumnProvider;

public final class ColumnService
{
	public static final String DEF_NAME = "";
	public static final String DEF_TOOLTIP = "";
	public static final String DEF_UNIT = "";
	public static final int DEF_ORDER = -1;
	public static final int DEF_ALIGNMENT = LEFT;
	public static final boolean DEF_MOVABLE = false;
	public static final boolean DEF_RISIZABLE = true;
	public static final boolean DEF_READONLY = false;
	public static final boolean DEF_SORTABLE = true;
	public static final boolean DEF_VISIBLE = true;
	public static final boolean DEF_HIDEABLE = true;
	public static final boolean DEF_EMPTYABLE = true;
	public static final int DEF_WIDTH = -1;
	public static final Class<?> DEF_TYPE = String.class;

	/**
	 * Closed constructor
	 */
	private ColumnService()
	{}

	public static <T> List<ColumnSettings<T>> getColumnsSettings(Class<T> item, PTableColumnProvider columnProvider,
	                ColumnContext columnContext) throws AnnotationDeclarationException
	{
		checkItemType(item);

		List<ColumnSettings<T>> infos = new ArrayList<ColumnSettings<T>>();
		Field[] fields = getAnnotatedFields(item, Column.class);
		Method[] methods = getAnnotatedMethods(item, Column.class);

		// if no @Column annotation, throw an Exception
		if(fields.length == 0 && methods.length == 0)
			throw new AnnotationDeclarationException("No @Column annotated fields or methods found");

		for(Field field : fields)
			infos.add(ColumnService.<T> getAnnotationInfo(field, columnProvider, columnContext));

		for(Method method : methods)
			infos.add(ColumnService.<T> getAnnotationInfo(method, item, columnProvider, columnContext));

		removeDuplicatesAndSort(infos);

		return infos;
	}

	private static <T> ColumnSettings<T> getAnnotationInfo(Method method, Class<?> cls,
	                PTableColumnProvider columnProvider, ColumnContext columnContext)
	                throws AnnotationDeclarationException
	{
		Assert.isNotNull(method);
		Assert.isNotNull(cls);

		checkMethodParameterNumber(method.getParameterTypes());
		checkMethodReturnType(method.getReturnType());
		checkOrderValue(method.getAnnotation(Column.class));

		ColumnSettings<T> column = PTableColumnProviderImpl.createColumnSettings(method, columnProvider, columnContext);

		if(isEmpty(column.getName()))
			column.getContent().setName(getPropertyNameByMethodName(method.getName()));

		checkSetterExists(method, cls);

		return column;
	}

	private static <T> ColumnSettings<T> getAnnotationInfo(Field field, PTableColumnProvider columnProvider,
	                ColumnContext columnContext) throws AnnotationDeclarationException
	{
		Assert.isNotNull(field);

		checkOrderValue(field.getAnnotation(Column.class));
		checkFieldType(field.getType());

		ColumnSettings<T> column = PTableColumnProviderImpl.createColumnSettings(field, columnProvider, columnContext);

		if(isEmpty(column.getName()))
			column.getContent().setName(field.getName());

		return column;
	}

	private static void checkOrderValue(Column column) throws AnnotationDeclarationException
	{
		Assert.isNotNull(column);

		int order = column.order();

		if(order != DEF_ORDER && order < 0)
			throw new AnnotationDeclarationException("Parameter 'order' in @Column annotation must not be negative");
	}

	private static <T> void checkSetterExists(Method method, Class<?> cls) throws AnnotationDeclarationException
	{
		Assert.isNotNull(method);
		Assert.isNotNull(cls);

		try
		{
			String setMethodName = getSetterMethodNameByGetterMethodName(method.getName());

			cls.getDeclaredMethod(setMethodName, method.getReturnType());
		}
		catch(Exception e)
		{
			throw new AnnotationDeclarationException(e);
		}
	}

	private static void checkMethodParameterNumber(Class<?>[] types) throws AnnotationDeclarationException
	{
		Assert.isNotNull(types);

		if(isNotEmpty(types))
			throw new AnnotationDeclarationException("Method annotatated with @Column must not have arguments");
	}

	private static void checkMethodReturnType(Class<?> type) throws AnnotationDeclarationException
	{
		Assert.isNotNull(type);

		if(type.isAssignableFrom(void.class))
			throw new AnnotationDeclarationException("Method annotatated with @Column must return value");

		checkType(type, "Field annotatated with @Column must not be array/collection/map");
	}

	private static void checkFieldType(Class<?> type) throws AnnotationDeclarationException
	{
		checkType(type, "Field annotatated with @Column must not be array/collection/map");
	}

	private static void checkType(Class<?> type, String message) throws AnnotationDeclarationException
	{
		Assert.isNotNull(type);
		Assert.isNotNull(message);

		if(type.isAssignableFrom(Set.class) || type.isAssignableFrom(List.class) || type.isAssignableFrom(Queue.class))
			throw new AnnotationDeclarationException(message);
		if(type.isAssignableFrom(Map.class) || type.isArray())
			throw new AnnotationDeclarationException(message);
	}

	private static void checkItemType(Class<?> type)
	{
		Assert.isNotNull(type);

		if(type.isAssignableFrom(String.class))
			throw new IllegalArgumentException("item can't be String type");
		if(type.isEnum())
			throw new IllegalArgumentException("item can't be Enum type");
		if(type.isPrimitive())
			throw new IllegalArgumentException("item can't be primitive type");
		if(type.isAssignableFrom(Number.class))
			throw new IllegalArgumentException("item must not be Number type");
	}
}
