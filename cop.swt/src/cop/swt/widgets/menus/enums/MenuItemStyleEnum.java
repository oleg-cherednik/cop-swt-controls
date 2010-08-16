package cop.swt.widgets.menus.enums;

import static org.eclipse.swt.SWT.CASCADE;
import static org.eclipse.swt.SWT.CHECK;
import static org.eclipse.swt.SWT.PUSH;
import static org.eclipse.swt.SWT.RADIO;
import static org.eclipse.swt.SWT.SEPARATOR;

public enum MenuItemStyleEnum
{
	MIS_CHECK(CHECK),
	MIS_CASCADE(CASCADE),
	MIS_PUSH(PUSH),
	MIS_RADIO(RADIO),
	MIS_SEPARATOR(SEPARATOR);

	private int swtStyle;

	private MenuItemStyleEnum(int swtStyle)
	{
		this.swtStyle = swtStyle;
	}

	public int getSwtStyle()
	{
		return swtStyle;
	}
}
