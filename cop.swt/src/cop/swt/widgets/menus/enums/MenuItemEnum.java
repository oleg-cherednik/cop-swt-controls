package cop.swt.widgets.menus.enums;

import static cop.common.extensions.StringExtension.isNotEmpty;
import static cop.swt.widgets.keys.HotKey.createHotKey;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_A;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_ALT;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_C;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_CTRL;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_DELETE;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_E;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_ESC;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_P;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.eclipse.core.runtime.Assert;

import cop.common.extensions.StringExtension;
import cop.swt.extensions.LocalizationExtension;
import cop.swt.widgets.annotations.i18n;
import cop.swt.widgets.keys.HotKey;
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
	MI_COPY("Copy", createHotKey(KEY_CTRL, KEY_C), "Copy", "Kopieren", "����������"),
	MI_DELETE("Delete", KEY_DELETE, "Delete", "L\u00f6schen", "�������"),
	MI_SELECT_ALL("Select All", createHotKey(KEY_CTRL, KEY_A), "Select All", "Alle Ausw\u00e4hlen", "�������� ��"),
	MI_DESELECT_ALL("Deselect All", KEY_ESC, "Deselect All", "Abw\u00e4hlen", "����� ���������"),
	MI_PROPERTIES("Properties...", createHotKey(KEY_ALT, KEY_P), "Properties...", "Eigenschaften...", "��������..."),
	MI_SORT("Sorting", "Sorting", "Sortierung", "����������"),
	MI_OFF("off", "off", "ausschalten", "����."),
	MI_EXPORT("Export...", createHotKey(KEY_ALT, KEY_E), "Export...", "Exportieren...", "��������������..."),
	MI_HIDE,

	;

	public static final String MENU_ITEM_ENUM = "MenuItemEnum";
	// public static final String MENU_ITEM_KEY = "key";

	private Map<Locale, String> map = new HashMap<Locale, String>();

	private String text;
	private HotKey accelerator;

	private MenuItemEnum()
	{}

	private MenuItemEnum(String text, String en_UK, String de_DE, String ru_RU)
	{
		this(text, (HotKey)null, en_UK, de_DE, ru_RU);
	}

	private MenuItemEnum(String text, KeyEnum accelerator, String en_UK, String de_DE, String ru_RU)
	{
		this(text, createHotKey(accelerator), en_UK, de_DE, ru_RU);
	}

	private MenuItemEnum(String text, HotKey accelerator, String en_UK, String de_DE, String ru_RU)
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

	public HotKey getAccelerator()
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
