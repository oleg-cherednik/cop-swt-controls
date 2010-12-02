package cop.swt.widgets.keys;

import static cop.swt.widgets.keys.enums.KeyEnum.KEY_CTRL;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_DOWN;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_UP;
import cop.swt.widgets.keys.enums.KeyEnum;

public final class FinalHotKeyGroup extends HotKeyGroup
{
	public static final HotKeyGroup keyCtrlUp = new FinalHotKeyGroup(KEY_CTRL, KEY_UP);
	public static final HotKeyGroup keyCtrlDown = new FinalHotKeyGroup(KEY_CTRL, KEY_DOWN);

	public FinalHotKeyGroup(KeyEnum... keys)
	{
		super(keys);
	}

	/*
	 * KeyGroup
	 */

	@Override
	@Deprecated
	public void addKey(KeyEnum key)
	{
		throw new IllegalAccessError("Can't add new key to FinalHotKeyGroup");
	}

	@Override
	@Deprecated
	public boolean removeKey(KeyEnum key)
	{
		throw new IllegalAccessError("Can't remove key from FinalHotKeyGroup");
	}
}
