package cop.swt.widgets.menus.items;

import static cop.swt.widgets.annotations.services.i18nService.getTranslation;

import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.Listener;

import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;
import cop.swt.widgets.menus.interfaces.PropertyProvider;
import cop.swt.widgets.menus.items.basics.AbstractRadioMenuItem;
import cop.swt.widgets.viewers.table.descriptions.IColumnDescription;

public class RadioDescriptionMenuItem<T> extends AbstractRadioMenuItem
{
	private T obj;
	private IColumnDescription description;

	public RadioDescriptionMenuItem(T obj, IColumnDescription description, Listener listener)
	{
		this(obj, description, null, null, null, listener);
	}

	public RadioDescriptionMenuItem(T obj, IColumnDescription description, PropertyProvider<Boolean> visibleProvider,
	                PropertyProvider<Boolean> enabledProvider, PropertyProvider<Boolean> selectionProvider,
	                Listener listener)
	{
		Assert.isNotNull(obj);
		Assert.isNotNull(description);

		this.obj = obj;
		this.description = description;

		setSelectionProvider(selectionProvider);
		setVisibleProvider(visibleProvider);
		setEnabledProvider(enabledProvider);
		setListener(listener);
	}

	/*
	 * AbstractMenuItem
	 */

	@Override
	public String _getKey()
	{
		return description.getKey();
	}

	/*
	 * IMenuItem
	 */

	@Override
	public String getText(Locale locale)
	{
		try
		{
			return getTranslation(obj, description.getKey(), locale);
		}
		catch(AnnotationDeclarationException e)
		{
			e.printStackTrace();
			return _getKey();
		}
	}
}
