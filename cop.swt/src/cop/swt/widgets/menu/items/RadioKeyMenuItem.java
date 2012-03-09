package cop.swt.widgets.menu.items;

import static cop.swt.widgets.menu.enums.MenuItemEnum.MI_COLUMN_DESCRIPTION;

import java.util.Locale;

import org.eclipse.swt.widgets.Listener;

import cop.i18.annotations.i18nService;
import cop.i18.exceptions.i18nDeclarationException;
import cop.swt.widgets.menu.interfaces.PropertyProvider;
import cop.swt.widgets.menu.items.basics.AbstractRadioMenuItem;

public class RadioKeyMenuItem<T> extends AbstractRadioMenuItem
{
	private Class<T> cls;
	private String localKey;

	public RadioKeyMenuItem(Class<T> cls, String localKey, Listener listener)
	{
		this(cls, localKey, null, null, null, listener);
	}

	public RadioKeyMenuItem(Class<T> cls, String localKey, PropertyProvider<Boolean> visibleProvider,
	                PropertyProvider<Boolean> enabledProvider, PropertyProvider<Boolean> selectionProvider,
	                Listener listener)
	{
		super(MI_COLUMN_DESCRIPTION);

		this.cls = cls;
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
			return i18nService.getTranslation(cls, localKey, locale);
		}
		catch(i18nDeclarationException e)
		{
			e.printStackTrace();
			return _getKey();
		}
	}
}
