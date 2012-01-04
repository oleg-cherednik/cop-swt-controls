package cop.swt.widgets.viewers.table.columns;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;

import cop.swt.images.ImageProvider;

public interface ColumnSettingsContext
{
	AccessibleObject getObject();

	ImageProvider getImageProvider();

	Locale getLocale();
}
