package cop.swt.widgets.annotations.services;

import static cop.common.extensions.CommonExtension.isNotNull;

import java.lang.reflect.AccessibleObject;

import cop.swt.widgets.annotations.Percent;

public final class PercentService
{
	/**
	 * Closed constructor
	 */
	private PercentService()
	{}

	public static boolean isPercent(AccessibleObject obj)
	{
		return isNotNull(obj) ? isNotNull(obj.getAnnotation(Percent.class)) : false;
	}
}
