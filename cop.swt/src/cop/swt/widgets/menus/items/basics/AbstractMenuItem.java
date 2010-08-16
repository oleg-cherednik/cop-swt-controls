package cop.swt.widgets.menus.items.basics;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isNotEmpty;
import static org.eclipse.swt.SWT.Selection;

import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import cop.swt.images.ImageProvider;
import cop.swt.widgets.keys.HotKeyGroup;
import cop.swt.widgets.menus.enums.MenuItemStyleEnum;
import cop.swt.widgets.menus.interfaces.IMenuItem;
import cop.swt.widgets.menus.interfaces.PropertyProvider;

public abstract class AbstractMenuItem implements IMenuItem
{
	private final MenuItemStyleEnum style;
	private HotKeyGroup accelerator;
	protected Listener listener;
	private Locale locale = Locale.getDefault();
	private ImageProvider iconProvider;
	protected PropertyProvider<Boolean> enabledProvider;
	private PropertyProvider<Boolean> visibleProvider;

	protected AbstractMenuItem(MenuItemStyleEnum style)
	{
		this(style, null);
	}

	protected AbstractMenuItem(MenuItemStyleEnum style, HotKeyGroup accelerator)
	{
		Assert.isNotNull(style);

		this.style = style;
		this.accelerator = accelerator;
	}

	public void setEnabledProvider(PropertyProvider<Boolean> enabledProvider)
	{
		this.enabledProvider = enabledProvider;
	}

	public void setVisibleProvider(PropertyProvider<Boolean> visibleProvider)
	{
		this.visibleProvider = visibleProvider;
	}

	@Override
    public void setImageProvider(ImageProvider iconProvider)
	{
		this.iconProvider = iconProvider;
	}

	public ImageProvider getIconProvider()
	{
		return iconProvider;
	}

	public void setListener(Listener listener)
	{
		this.listener = listener;
	}

	public final MenuItemStyleEnum getStyle()
	{
		return style;
	}

	protected abstract String _getKey();
	
	protected boolean isListenerEnabled()
	{
		return isNotNull(listener);
	}

	/*
	 * ICreateMenu
	 */

	@Override
	public MenuItem create(Menu parent)
	{
		Assert.isNotNull(parent);

		MenuItem item = new MenuItem(parent, style.getSwtStyle());

		item.setImage(getImage());
		item.setText(getTitle());
		item.setEnabled(isEnabled());

		if(isNotNull(listener))
			item.addListener(Selection, listener);

		return item;
	}

	/*
	 * IMenuItem
	 */

	@Override
	public String getText(Locale locale)
	{
		return _getKey();
	}

	@Override
	public boolean isVisible()
	{
		return isNotNull(visibleProvider) ? visibleProvider.getProperty() : true;
	}

	@Override
	public boolean isEnabled()
	{
		if(!isListenerEnabled())
			return false;

		if(isNull(enabledProvider))
			return true;

		return enabledProvider.getProperty();
	}

	@Override
	public Listener getListener()
	{
		return listener;
	}

	@Override
	public String getTitle()
	{
		if(isNull(accelerator) || accelerator.isEmpty())
			return getText(locale);

		return getText(locale) + " \t" + accelerator.getName();
	}

	@Override
	public HotKeyGroup getAccelerator()
	{
		return isNotNull(accelerator) ? (HotKeyGroup)accelerator.clone() : new HotKeyGroup();
	}

	@Override
	public Image getImage()
	{
		ImageProvider iconProvider = getIconProvider();
		String key = _getKey();

		Assert.isTrue(isNotEmpty(key));

		return isNotNull(iconProvider) && isNotEmpty(key) ? iconProvider.getImage(key) : null;
	}

	/*
	 * LocaleSupport
	 */

	@Override
	public void setLocale(Locale locale)
	{
		Assert.isNotNull(locale);

		this.locale = locale;
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		return "MenuItem: " + _getKey();
	}

}
