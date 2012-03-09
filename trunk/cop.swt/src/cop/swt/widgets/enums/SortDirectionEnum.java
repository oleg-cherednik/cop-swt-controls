package cop.swt.widgets.enums;

import static org.eclipse.swt.SWT.DOWN;
import static org.eclipse.swt.SWT.NONE;
import static org.eclipse.swt.SWT.UP;

import java.util.Locale;

import cop.i18.LocaleStore;
import cop.swt.widgets.menu.interfaces.MenuItemKey;

public enum SortDirectionEnum implements MenuItemKey {
	SORT_OFF(NONE),
	SORT_ASC(UP),
	SORT_DESC(DOWN);

	private int swt;

	private SortDirectionEnum(int direction) {
		this.swt = direction;
	}

	public int getSwtDirection() {
		return swt;
	}

	public static SortDirectionEnum parseSwtDirection(int swt) {
		for (SortDirectionEnum value : values())
			if (value.swt == swt)
				return value;

		return SORT_OFF;
	}

	/*
	 * Localizable
	 */

	@Override
	public String i18n() {
		return LocaleStore._i18n(this, name());
	}

	@Override
	public String i18n(Locale locale) {
		return LocaleStore._i18n(this, name(), locale);
	}

	/*
	 * MenuItemKey
	 */

	@Override
	public String getKey() {
		return name();
	}
}
