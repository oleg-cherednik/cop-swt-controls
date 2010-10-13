package cop.swt.icons;

import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.CollectionExtension.isNotEmpty;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.StringExtension.isNotEmpty;

import java.util.HashMap;
import java.util.Map;

public class IconImage
{
	private Map<IconSizeEnum, String> images = new HashMap<IconSizeEnum, String>();

	public IconImage()
	{}

	public IconImage(IconSizeEnum size, String path)
	{
		addImages(size, path);
	}

	public IconImage(Map<IconSizeEnum, String> images)
	{
		addImages(images);
	}

	public void addImages(IconSizeEnum size, String path)
	{
		if(isNotNull(size) && isNotEmpty(path))
			images.put(size, path);
	}

	public void addImages(Map<IconSizeEnum, String> images)
	{
		if(isNotEmpty(images))
			images.putAll(images);
	}

	public void removeImages(IconSizeEnum size)
	{
		if(isNotNull(size))
			images.remove(size);
	}

	public void removeImages(IconSizeEnum[] sizes)
	{
		if(isEmpty(sizes))
			return;

		for(IconSizeEnum size : sizes)
			images.remove(size);
	}
}
