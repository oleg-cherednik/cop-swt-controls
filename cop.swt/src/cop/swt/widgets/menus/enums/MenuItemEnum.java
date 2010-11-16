package cop.swt.widgets.menus.enums;

import static cop.common.extensions.StringExtension.isNotEmpty;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_A;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_ALT;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_C;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_CTRL;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_ESC;
import static cop.swt.widgets.keys.enums.KeyEnum.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.eclipse.core.runtime.Assert;

import cop.swt.extensions.LocalizationExtension;
import cop.common.extensions.StringExtension;
import cop.swt.widgets.annotations.i18n;
import cop.swt.widgets.keys.HotKeyGroup;
import cop.swt.widgets.keys.enums.KeyEnum;
import cop.swt.widgets.localization.interfaces.Localizable;

public enum MenuItemEnum implements Localizable<String>
{
	// MI_EXPORT(new PushMenuItem("Export...", new HotKeyGroup(KEY_CTRL, KEY_E), null)),
	// MI_PRINT(new PushMenuItem("Print...", new HotKeyGroup(KEY_CTRL, KEY_P), "icons//print//print16.png")),
	// MI_SORT(new PushMenuItem("Sort...", "icons//sort//sort16.png")),

	MI_SEPARATOR,
	MI_STATE,
	MI_COLUMN_DESCRIPTION,
	MI_COPY("Copy", new HotKeyGroup(KEY_CTRL, KEY_C), "Copy", "Kopieren", "Копировать"),
	MI_DELETE("Delete", KeyEnum.KEY_DELETE, "Delete", "L\u00f6schen", "Удалить"),
	MI_SELECT_ALL("Select All", new HotKeyGroup(KEY_CTRL, KEY_A), "Select All", "Alle Ausw\u00e4hlen", "Выделить Всё"),
	MI_DESELECT_ALL("Deselect All", KEY_ESC, "Deselect All", "Abw\u00e4hlen", "Снять Выделение"),
	MI_PROPERTIES("Properties...", new HotKeyGroup(KEY_ALT, KEY_P), "Properties...", "Eigenschaften...", "Свойства..."),
	MI_SORT("Sorting", "Sorting", "Sortierung", "Сортировка"),
	MI_OFF("off", "off", "ausschalten", "выкл."),
	MI_EXPORT("Export...", new HotKeyGroup(KEY_ALT, KEY_E), "Export...", "Exportieren...", "Экспортировать..."),

	;

	public static final String MENU_ITEM_KEY = "menu_item";
	private Map<Locale, String> map = new HashMap<Locale, String>();

	private String text;
	private HotKeyGroup accelerator;

	private MenuItemEnum()
	{}

	private MenuItemEnum(String text, String en_UK, String de_DE, String ru_RU)
	{
		this(text, (HotKeyGroup)null, en_UK, de_DE, ru_RU);
	}

	private MenuItemEnum(String text, KeyEnum accelerator, String en_UK, String de_DE, String ru_RU)
	{
		this(text, new HotKeyGroup(accelerator), en_UK, de_DE, ru_RU);
	}

	private MenuItemEnum(String text, HotKeyGroup accelerator, String en_UK, String de_DE, String ru_RU)
	{
		Assert.isTrue(StringExtension.isNotEmpty(text));
		this.text = text;
		this.accelerator = accelerator;

		map.put(Locale.US, text);
		map.put(Locale.UK, en_UK);
		map.put(Locale.GERMANY, de_DE);
		map.put(new Locale("ru", "RU"), ru_RU);
	}

	public String getText()
	{
		return text;
	}

	public HotKeyGroup getAccelerator()
	{
		return accelerator;
	}

	@i18n
	public static String[] getLocalizedName()
	{
		return getLocalizedName(Locale.getDefault());
	}

	@i18n
	public static String[] getLocalizedName(Locale locale)
	{
		return LocalizationExtension.getLocalizedNames(values(), locale);
	}

	/*
	 * Localizable
	 */

	@Override
	public String i18n()
	{
		return i18n(Locale.getDefault());
	}

	@Override
	public String i18n(Locale locale)
	{
		Assert.isNotNull(locale);

		String str = map.get(locale);

		return isNotEmpty(str) ? str : map.get(Locale.getDefault());
	}
}
