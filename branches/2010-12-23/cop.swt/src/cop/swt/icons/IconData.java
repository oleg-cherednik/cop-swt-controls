package cop.swt.icons;

import static cop.common.extensions.StringExtension.isNotEmpty;

import org.eclipse.core.runtime.Assert;

public final class IconData
{
	public final IconSizeEnum size;
	public final String path;

	public IconData(IconSizeEnum size, String path)
	{
		Assert.isNotNull(size);
		Assert.isTrue(isNotEmpty(path));

		this.size = size;
		this.path = path;
	}
}
