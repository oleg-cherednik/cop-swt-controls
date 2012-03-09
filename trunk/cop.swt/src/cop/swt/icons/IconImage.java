package cop.swt.icons;

import static cop.extensions.ArrayExt.isEmpty;
import static cop.extensions.CollectionExt.isNotEmpty;
import static cop.extensions.CommonExt.isNotNull;

import java.util.HashMap;
import java.util.Map;

import cop.extensions.StringExt;

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
		if(isNotNull(size) && StringExt.isNotEmpty(path))
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
