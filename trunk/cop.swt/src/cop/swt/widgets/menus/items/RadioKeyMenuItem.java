package cop.swt.widgets.menus.items;

import static cop.swt.widgets.annotations.services.i18nService.getTranslation;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MI_COLUMN_DESCRIPTION;

import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.Listener;

import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;
import cop.swt.widgets.menus.interfaces.PropertyProvider;
import cop.swt.widgets.menus.items.basics.AbstractRadioMenuItem;

public class RadioKeyMenuItem<T> extends AbstractRadioMenuItem
{
	private T obj;
	private String localKey;

	public RadioKeyMenuItem(T obj, String localKey, Listener listener)
	{
		this(obj, localKey, null, null, null, listener);
	}

	public RadioKeyMenuItem(T obj, String localKey, PropertyProvider<Boolean> visibleProvider,
	                PropertyProvider<Boolean> enabledProvider, PropertyProvider<Boolean> selectionProvider,
	                Listener listener)
	{
		super(MI_COLUMN_DESCRIPTION);

		Assert.isNotNull(obj);
		Assert.isNotNull(localKey);

		this.obj = obj;
		this.localKey = localKey;

		setSelectionProvider(selectionProvider);
		setVisibleProvider(visibleProvider);
		setEnabledProvider(enabledProvider);
		setListener(listener);
	}

	/*
	 * AbstractMenuItem
	 */

	@Override
	protected String _getKey()
	{
		return localKey;
	}

	/*
	 * IMenuItem
	 */

	@Override
	public String getText(Locale locale)
	{
		try
		{
			return getTranslation(obj, localKey, locale);
		}
		catch(AnnotationDeclarationException e)
		{
			e.printStackTrace();
			return _getKey();
		}
	}
}
