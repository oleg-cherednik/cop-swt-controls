package cop.swt.widgets.menu.enums;

import static cop.swt.widgets.keys.HotKey.EMPTY_HOT_KEY;
import static cop.swt.widgets.keys.HotKey.createHotKey;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_A;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_ALT;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_C;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_CTRL;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_DELETE;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_E;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_ESC;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_P;

import java.util.Locale;

import cop.swt.extensions.LocalizationExtension;
import cop.swt.widgets.annotations.i18n;
import cop.swt.widgets.keys.HotKey;
import cop.swt.widgets.keys.enums.KeyEnum;
import cop.swt.widgets.menu.interfaces.MenuItemKey;

public enum MenuItemEnum implements MenuItemKey
{
	// MI_EXPORT(new PushMenuItem("Export...", new HotKeyGroup(KEY_CTRL, KEY_E), null)),
	// MI_PRINT(new PushMenuItem("Print...", new HotKeyGroup(KEY_CTRL, KEY_P), "icons//print//print16.png")),
	// MI_SORT(new PushMenuItem("Sort...", "icons//sort//sort16.png")),

	MI_SEPARATOR,
	MI_STATE,
	MI_COLUMN_DESCRIPTION,
	MI_COPY(createHotKey(KEY_CTRL, KEY_C)),
	MI_DELETE(KEY_DELETE),
	MI_SELECT_ALL(createHotKey(KEY_CTRL, KEY_A)),
	MI_DESELECT_ALL(KEY_ESC),
	MI_PROPERTIES(createHotKey(KEY_ALT, KEY_P)),
	MI_SORT,
	MI_OFF,
	MI_EXPORT(createHotKey(KEY_ALT, KEY_E)),
	MI_HIDE,

	;

	public static final String MENU_ITEM_ENUM = "MenuItemEnum";
	// public static final String MENU_ITEM_KEY = "key";

	private HotKey accelerator = EMPTY_HOT_KEY;

	private MenuItemEnum()
	{}

	private MenuItemEnum(KeyEnum... accelerator)
	{
		this(createHotKey(accelerator));
	}

	private MenuItemEnum(HotKey accelerator)
	{
		this.accelerator = accelerator;
	}

	public HotKey getAccelerator()
	{
		return accelerator;
	}

	@i18n
	public static String[] getLocalizedName()
	{
		return LocalizationExtension.i18n(values());
	}

	@i18n
	public static String[] getLocalizedName(Locale locale)
	{
		return LocalizationExtension.i18n(values(), locale);
	}

	/*
	 * Localizable
	 */

	@Override
	public String i18n()
	{
		return LocalizationExtension.i18n(this, name());
	}

	@Override
	public String i18n(Locale locale)
	{
		return LocalizationExtension.i18n(this, name(), locale);
	}

	/*
	 * MenuKey
	 */

	public String getKey()
	{
		return name();
	}
}
