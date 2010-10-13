package cop.swt.widgets.keys.enums;

import static cop.algorithms.search.LinearSearch.linearSearch;
import static cop.common.extensions.CommonExtension.isNotNull;

import org.eclipse.core.runtime.Assert;

import cop.swt.widgets.keys.KeyGroup;

public enum KeyEnum
{
	KEY_UNKNOWN(0x0),
	KEY_BACKSPACE(0x8),
	KEY_TAB(0x9, "Tab"),
	KEY_ENTER(0xD, "Enter"),
	KEY_ESC(0x1B, "Esc"),
	KEY_SPACE(0x20),
	KEY_COMMA(0x2C, ","),
	KEY_MINUS(0x2D, "-"),
	KEY_DOT(0x2E, "."),
	KEY_A(0x61, "A", (char)0x1, (char)0x61),
	KEY_B(0x62, "B", (char)0x2, (char)0x62),
	KEY_C(0x63, "C", (char)0x3, (char)0x63),
	KEY_D(0x64, "D", (char)0x4, (char)0x64),
	KEY_E(0x65, "E", (char)0x5, (char)0x65),
	KEY_F(0x66, "F", (char)0x6, (char)0x66),
	KEY_J(0x67, "J", (char)0x7, (char)0x67),
	KEY_H(0x68, "H", (char)0x8, (char)0x68),
	KEY_M(0x6D, "M", (char)0xD, (char)0x6D),
	KEY_N(0x6E, "N", (char)0xE, (char)0x6E),
	KEY_O(0x6F, "O", (char)0xF, (char)0x6F),
	KEY_P(0x70, "P", (char)0x10, (char)0x70),
	KEY_S(0x73, "S", (char)0x13, (char)0x73),
	KEY_Y(0x79, "Y", (char)0x19, (char)0x79),
	KEY_DELETE(0x7F, "Delete"),
	KEY_SPACE_NB(0xA0), // non-break space, use in DecimalFormatSymbols.groupingSeparator
	KEY_ALT(0x10000, "Alt"),
	KEY_SHIFT(0x20000, "Shift"),
	KEY_CTRL(0x40000, "Ctrl"),
	KEY_UP(0x1000001, "Up"),
	KEY_DOWN(0x1000002, "Down"),
	KEY_LEFT(0x1000003, "Left"),
	KEY_RIGHT(0x1000004, "Right"),
	KEY_PAGE_UP(0x1000005, "PgUp"),
	KEY_PAGE_DOWN(0x1000006, "PgDn"),
	KEY_F1(0x100000A, "F1"),
	KEY_F2(0x100000B, "F2"),
	KEY_F3(0x100000C, "F3"),
	KEY_F4(0x100000D, "F4"),
	KEY_F5(0x100000E, "F5"),
	KEY_F6(0x100000F, "F6"),
	KEY_F7(0x1000010, "F7"),
	KEY_F8(0x1000011, "F8"),
	KEY_F9(0x1000012, "F9"),
	KEY_F10(0x1000013, "F10"),
	KEY_GREY_DOT(0x100002E),
	KEY_GREY_MINUS(0x100002D);

	private int keyCode;
	private char ctrlChar; // Ctrl+...
	private char altChar; // Alt+...
	private String keyName;

	private KeyEnum(int keyCode)
	{
		this(keyCode, null, (char)0x0, (char)0x0);
	}

	private KeyEnum(int keyCode, String keyName)
	{
		this(keyCode, keyName, (char)0x0, (char)0x0);
	}

	private KeyEnum(int keyCode, String keyName, char ctrlChar, char altChar)
	{
		this.keyCode = keyCode;
		this.ctrlChar = ctrlChar;
		this.altChar = altChar;
		this.keyName = keyName;
	}

	public int getKeyCode()
	{
		return keyCode;
	}

	public char getCtrlChar()
	{
		return ctrlChar;
	}

	public char getAltChar()
	{
		return altChar;
	}

	public String getKeyName()
	{
		return keyName;
	}

	public KeyGroup toKeyGroup()
	{
		return (this == KEY_UNKNOWN) ? new KeyGroup() : new KeyGroup(this);
	}

	public KeyGroup toKeyGroup(KeyEnum key)
	{
		return new KeyGroup(key, this);
	}

	public static KeyEnum parseKeyEnum(int keyCode)
	{
		for(KeyEnum key : KeyEnum.values())
		{
			if(key.keyCode == keyCode)
				return key;
		}

		return KEY_UNKNOWN;
	}

	public static KeyGroup parseKeyEnum(char character)
	{
		Assert.isLegal(character != 0x0);

		for(KeyEnum key : KeyEnum.values())
		{
			if(key.keyCode == character)
				return key.toKeyGroup();
			if(key.ctrlChar == character)
				return key.toKeyGroup(KEY_CTRL);
			if(key.altChar == character)
				return key.toKeyGroup(KEY_ALT);
		}

		return null;
	}

	public static boolean isSpace(int keyCode)
	{
		return isSpace(parseKeyEnum(keyCode));
	}

	public static boolean isSpace(KeyEnum key)
	{
		return isNotNull(key) ? (key == KEY_SPACE || key == KEY_SPACE_NB) : false;
	}

	public static boolean isArrow(int keyCode)
	{
		return isArrow(parseKeyEnum(keyCode));
	}

	public static boolean isArrow(KeyEnum key)
	{
		return isNotNull(key) ? linearSearch(getArrowKeys(), key) >= 0 : false;
	}

	public static boolean isPageUpDown(int keyCode)
	{
		KeyEnum key = parseKeyEnum(keyCode);

		if(key == null || key == KEY_UNKNOWN)
			return false;

		return key == KEY_PAGE_UP || key == KEY_PAGE_DOWN;
	}

	public static boolean isArrowLeftRight(int keyCode)
	{
		return isArrowLeftRight(parseKeyEnum(keyCode));
	}

	public static boolean isArrowLeftRight(KeyEnum key)
	{
		return isNotNull(key) ? (key == KEY_LEFT || key == KEY_RIGHT) : false;
	}

	public static boolean isArrowUpDown(int keyCode)
	{
		return isArrowUpDown(parseKeyEnum(keyCode));
	}

	public static boolean isArrowUpDown(KeyEnum key)
	{
		return isNotNull(key) ? (key == KEY_UP || key == KEY_DOWN) : false;
	}

	public static KeyEnum[] getArrowKeys()
	{
		return new KeyEnum[] { KEY_UP, KEY_DOWN, KEY_LEFT, KEY_RIGHT };
	}
}
