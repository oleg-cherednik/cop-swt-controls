package cop.swt.widgets.menu.items.basics;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isNotEmpty;
import static cop.swt.widgets.keys.HotKey.EMPTY_HOT_KEY;
import static org.eclipse.swt.SWT.Selection;

import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import cop.swt.images.ImageProvider;
import cop.swt.widgets.keys.HotKey;
import cop.swt.widgets.menu.MenuManager;
import cop.swt.widgets.menu.enums.MenuItemEnum;
import cop.swt.widgets.menu.enums.MenuItemStyleEnum;
import cop.swt.widgets.menu.interfaces.IMenuItem;
import cop.swt.widgets.menu.interfaces.PropertyProvider;

public abstract class AbstractMenuItem implements IMenuItem
{
	protected final MenuItemEnum key;
	private final MenuItemStyleEnum style;
	private HotKey accelerator;
	protected Listener listener;
	private Locale locale = Locale.getDefault();
	private ImageProvider iconProvider;
	protected PropertyProvider<Boolean> enabledProvider;
	private PropertyProvider<Boolean> visibleProvider;

	protected AbstractMenuItem(MenuItemStyleEnum style, MenuItemEnum key)
	{
		this(style, key, EMPTY_HOT_KEY);
	}

	protected AbstractMenuItem(MenuItemStyleEnum style, MenuItemEnum key, HotKey accelerator)
	{
		Assert.isNotNull(style);

		this.key = key;
		this.style = style;
		this.accelerator = (accelerator != null) ? accelerator : EMPTY_HOT_KEY;
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
	public final MenuItemEnum getMenuItemKey()
	{
		return key;
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
	
	protected String _getKey()
	{
		return key.name();
	}

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
		MenuItem item = new MenuItem(parent, style.getSwtStyle());
		String path = (String)parent.getData(MenuManager.MENU_ITEM_PATH) + _getKey()
		                + MenuManager.MENU_ITEM_PATH_SEPARATOR;

		item.setData(MenuManager.MENU_ITEM_PATH, path);
		item.setData(MenuItemEnum.MENU_ITEM_ENUM, key);
		item.setImage(getImage());
		item.setText(getTitle());
		item.setEnabled(isEnabled());

		if(listener != null)
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
		if(accelerator.isEmpty())
			return getText(locale);

		return getText(locale) + " \t" + accelerator.getName();
	}

	@Override
	public HotKey getAccelerator()
	{
		return EMPTY_HOT_KEY;
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
