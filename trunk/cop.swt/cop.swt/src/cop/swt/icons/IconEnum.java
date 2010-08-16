package cop.swt.icons;

import static cop.swt.icons.IconSizeEnum.ICO_SIZE_16;

import java.util.HashMap;
import java.util.Map;

public enum IconEnum
{
	ICO_COPY(new IconData(ICO_SIZE_16, "icons//copy//copy16.png"));

	private Map<IconSizeEnum, String> images = new HashMap<IconSizeEnum, String>();

	private IconEnum()
	{}

	private IconEnum(IconData... data)
	{}

}
